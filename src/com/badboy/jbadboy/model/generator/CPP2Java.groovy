/**
 * Copyright 2008, Simon Sadedin, Badboy Software.
 * 
 * $Id: CPP2Java.groovy 1646 2008-11-07 00:48:28Z  $
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
 * $Id: CPP2Java.groovy 1646 2008-11-07 00:48:28Z  $
 */
package com.badboy.jbadboy.model.generator

import com.badboy.jbadboy.ScriptModel
import groovy.text.SimpleTemplateEngine
/**
 * Reads java source code from the Badboy source tree and 
 * writes out equivalent java classes containing the
 * same properties as in the Java source.
 * 
 * @author ssadedin
 */
public class CPP2Java {
	
	def srcPath = "c:/badboy/dev/trunk"
	
	def outputPath = "src"
	
	def outputPackage = "com.badboy.jbadboy.model"
	
	ScriptModel parse(String className) {
		
		def srcFile = "${srcPath}/${className}.cpp"
		
		ScriptModel result = new ScriptModel(className:className)
		
		// Extract section between DECLARE_PROPERTIES 
		File f = new File(srcFile)
		def matches = (f.text =~ /DECLPROP\((.*)\)/)
		def props = []
		matches.each { full, prop -> 
		  props << prop
		}
		
		println "Found properties: "
		props.each { println it }
		println "\n\n"
		
		// Instantiate each property
		props.each {
			result[it] = findType(className,it)
		}
		
		// Set parent class
		if(f.text =~ /IMPLEMENT_SERIAL\([A-Za-z0-9 \t]*, *Playable *,.*VERSIONABLE_SCHEMA.*\)/)
		    result.parentClassName = "Playable"
	    else
	        result.parentClassName = "ScriptItem"
	        
	        
	    println "File " + srcFile + " ==> parent class is " + result.parentClassName;
	        
		return result	
	}
	
	Object findType(String className,String propertyName) {
		
		def headerFile = "${srcPath}/${className}.h"
		File f = new File(headerFile)
		
		def type = null;
		(f.text =~ /(.*)m_${propertyName}/).each { full, decl ->
			type = decl.trim();
			println "Declaration of $propertyName is $type"
		}
		switch(type) {
			case "UINT":
			case "short":
			case "int":
				return new Integer(0)
			case "CString":
				return new String()
			case "bool":
				return new Boolean(false)
		}
	}
	
	void write(ScriptModel model) {
		
		def props = []
		model.properties.each { name,value ->
			if(value == null)
				return
				
		    if(name == "parentClassName")
		        return
				
			def nameUpper = name.replaceAll(/^([a-z])/,{ full, c -> c.toUpperCase()})
			def type = value.class.name.replaceFirst("java.lang.","");
			def init = "";
			switch(type) {
			    case "Boolean":
    			    init = " = Boolean.FALSE"
    			    break;
			    case "Integer":
    			    init = " = 0"
    			    break;
			    case "String":
			        init = ' = ""'
			        break;
			}
			    
			props << """
    private ${type} ${name}${init};

    @Exportable
    public ${type} get${nameUpper}() {
        return this.${name};
    }
    public void set${nameUpper}(${type} ${name}) {
        this.${name} = ${name};
    }""";
		}
		
		def playMethod = "";
		def parentClass = model.parentClassName
		def summary = "";
		if(model.className == "Playable") {
		    playMethod = """
    public void play() throws ExecutionException {
    }
""";
	        parentClass = "ScriptItem implements Summarizable";

	        summary = """
	Summary summary;
	
	public Summary getSummary() {
	  if(summary == null)
        summary = new Summary();
	  return summary;
	}
""";
		}
		
		def text = """
/**
 * This is a GENERATED FILE - DO NOT EDIT
 */
package ${outputPackage};

import com.badboy.jbadboy.item.*;
		
public class ${model.className} extends ${parentClass} {

    ${playMethod}
    ${summary}
	${props.join('\n')}
}
""";
		
		def outputDir = this.outputPath + "/" + this.outputPackage.replaceAll(/\./,"/")
		def outputFile = new File("${outputDir}/${model.className}.java") 
		if(outputFile.exists())
			outputFile.delete()
		outputFile << text
	}
}
