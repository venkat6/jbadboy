/**
 * Copyright 2008, Simon Sadedin, Badboy Software.
 * 
 * $Id: Runner.groovy 1661 2008-11-28 01:20:05Z  $
 *
 * This file is part of JBadboy.
 * 
 * JBadboy is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * JBadboy is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with JBadboy.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * Runner to launch badboy browser
 */
package com.badboy.jbadboy

import org.mozilla.browser.MozillaWindow
import javax.swing.JFrame
import org.mozilla.browser.MozillaAutomation
import com.badboy.jbadboy.item.ScriptItem
import com.badboy.jbadboy.browser.mozilla.MozillaBrowser
import com.badboy.jbadboy.item.Summary
import com.badboy.jbadboy.item.Script
import javax.xml.transform.stream.StreamSource
import javax.xml.transform.stream.StreamResult
import javax.xml.transform.TransformerFactory
import org.apache.log4j.Loggerimport org.apache.log4j.PropertyConfiguratorimport org.apache.log4j.BasicConfiguratorimport org.apache.log4j.Levelimport org.apache.log4j.varia.NullAppender
/**
 * Main class for running JBadboy.
 * <p>
 * Reads arguments from command line, loads script, creates MozSwing window,
 * and then creates a ScriptEngine and initiates playback.  When playback
 * is complete, writes results report and exits.
 * 
 * @author ssadedin@badboy.com.au
 */
public class Runner {
     
    private static Logger log = Logger.getInstance(Runner.class)

	public static void main(def args){
        
        configureLogging();
        
        printCopyright()
        
        if(!args.length) {
          println "\n\nUsage:  java -jar jbadboy.jar <script> [output report name]\n"
          System.exit(1);
        }
	    
	    def outputFileName = args.length>1 ? args[1] : "report.html"
	    ScriptContext context = loadContext(args[0])
        ScriptEngine engine = new ScriptEngine(scriptContext: context)
        
        println "\n\nFinished Loading - Begining Play\n\n"
        
        engine.play()
        
        println "\n\nFinished Playing\n\n"
        println "================================================"
        
        printSummary(context.script)
       
        println "================================================"
        println "\n\nFinished!\n\n"
        
        writeReport(context,outputFileName)
        
       // Force exit - without forcing it, browser window remains open
       System.exit(0)
	}
    
    /**
     * Load and initialize a ScriptContext from the specified
     * file name.
     */
    static ScriptContext loadContext(fileName) {
        ScriptContext context = new ScriptContext();
        def browser = new MozillaBrowser(context)        
        context.browser = browser
        
        def bbscript = new XmlParser().parse(new File(fileName))
        ScriptItem script = new Script(context:context);
        context.setScript(script)
        new Runner().loadItem(script,bbscript.Script[0])
        
        if(log.isDebugEnabled())
            script.dump(System.out,0)
        
        loadVariables(bbscript, context)
        loadDataSources(bbscript, context)
       
        log.info "Found ${context.variables.size()} variables"
        context.variables.each { n,var ->
          log.info "Variable:  " + var.toString()
        }
        return context
    }
    
    /**
     * Print a formatted summary of results to standard out
     */
    static void printSummary(def item) {
        def summary = item.summary
        def attributes = [ 
          "Played": "played", 
          "Succeeded": "succeeded", 
          "Failed" : "failed", 
          "Assertions" : "assertions", 
          "Average Time (ms)":"averageResponseTime", 
          "Max Time (ms)": "maxResponseTime" ]
        
        attributes.each { key, value ->
	       println "${key}: ".padLeft(20) + (summary[value]).toString().padRight(5).padLeft(20)
        }
    }
    
    /**
     * Write an HTML report for the given script context to the
     * specified file.
     */
    static void writeReport(def context, outputFileName) {
        // If home not defined then assume we are running in the jbadboy directory
        String jbadboyDir = System.properties.jbadboyHome ?: "."
        jbadboyDir = jbadboyDir.replaceAll('/*$',"")
        
        context.setVariable("badboy.saveItemRelFileName",outputFileName)
        context.setVariable("badboy.saveDateTime",(new Date()).toString())
                
        XmlWriter w = new XmlWriter()
        def outputXML = w.writeScript(context)
        
        // Write report
       def factory = TransformerFactory.newInstance()
       def transformer = factory.newTransformer(new StreamSource(new FileReader("$jbadboyDir/xsl/detail-report.xsl")))
       transformer.transform(new StreamSource(new StringReader(outputXML)), new StreamResult(new FileWriter(outputFileName))) 
       
       // File outputFile = new File(outputFileName)
       File outputImagesDir = new File(outputFileName + "-images")
        
       AntBuilder ant = new AntBuilder()
       
       // Copy all the image files across
       File imagesDir = new File("$jbadboyDir/xsl/images")
        
       ant.copy(todir: outputImagesDir) {
            fileset(dir: "$jbadboyDir/xsl/images/", includes: '**')
        }
    }
	
	/**
	 * Scan the node for Variables and add any found to the given
	 * script context.  Scan any 
	 */
	static void loadVariables(Node bbscript, ScriptContext context) {
	     
	   def v = null
	   bbscript.Variables[0].children().each {
	       if(it.name() == 'name') {	           
	           v = new Variable(it.name())
	           context.variables[it.text()] = v
	       }
	       
	       if(v == null)
	           return;
	       
	       if(v.properties.containsKey(it.name())) {
	           log.info "${it.name()} => ${it.text()}"
	           v[it.name()] = it.text()
	       }
	       else
	           log.info "no property ${it.name()}"
	   }
	 }
	 
	/**
	 * Scan the given script node for data soruces and load them in,
	 * associating them with the variables.
	 */
	static void loadDataSources(Node bbscript, ScriptContext context) {
	  
	   // Now add values to variables from data sources
	   bbscript.dataSources.source.each { src ->
	     def values = src.ValueListDataSource.VariableValueSet.value.collect { it.text() }
	     
	     log.info "Found data source with id ${src.id} and values ${values}"
	     
	     // Find any variables using this data source
	     context.variables.findAll { n, var ->  var.dataSourceId == src.id.text() }.each { n, var ->
	       var.values = values;
	     }
	   }
	}
	
	def typeCache = [:]
	
	void loadItem(ScriptItem item, Node node) {
	    if(node.id.text())
	        item.setId(Long.parseLong(node.id.text()))
        
        node.children().each {
			def nameUpper = it.name().replaceAll(/^([a-z])/,{ full, c -> c.toUpperCase()})
        	def method = item.class.methods.find{it.name.equals("get"+nameUpper)}
			if(method) {
	        	try {
	        	  switch(method.returnType) {
	        	    case Integer.class:
	                   item[it.name()] = Integer.parseInt(it.text())
	        	       break;
	        	    case String.class:
	                   item[it.name()] = it.text()
	        	       break;
	        	    case Boolean.class:
	                   item[it.name()] = Boolean.parseBoolean(it.text())
	        	       break;
	        	  }
		          // log.info "Set attribute ${it.name()}"
	        	}
	        	catch(Exception e) {
	        		log.info "Unable to set attribute {$it.name()}"
	        	}
        	}
        }
	    
	    item.load(node)
        
        if(!node.ChildItems)
        	return;
        
		node.ChildItems[0].children().each {
			
			// Try to create
			def type = typeCache[it.name()]
			if(!type) {
				def typeName = "com.badboy.jbadboy.item."+it.name()+"Impl"
				try {
					type = Class.forName(typeName)
					log.info "Found implemented type ${type.name}"
				}
				catch(Exception exNotFound) {
					log.info "No type ${typeName} found"
					type = Class.forName("com.badboy.jbadboy.model."+it.name())
				}
				typeCache[it.name()] = type
			}
			
			def child = type.newInstance()
			item.addChild(child)
			loadItem(child,it)
		}
	}
	
	
	/**
	 * Set up logging
	 */
	private static void configureLogging() {
        if(new File('log4j.properties').exists())
	        PropertyConfigurator.configure("log4j.properties")
	    else {
	        BasicConfigurator.configure();
	        log.getRootLogger().removeAllAppenders();
	        log.getRootLogger().addAppender(new NullAppender());
	    }
	}
	
	/**
	 * Display copyright messages
	 */
	private static void printCopyright() {
	    def versionFile = new File('version.txt')
	    def version = versionFile.exists() ? versionFile.text.trim() : "(dev)"
	    println """\r\nJBadboy Version ${version}  - Copyright 2008, Badboy Software

This program comes with ABSOLUTELY NO WARRANTY and is Free Software. 
You are welcome to redistribute it under certain conditions.
"""
	}
}
