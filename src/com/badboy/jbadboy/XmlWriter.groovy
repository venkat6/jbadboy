/**
 * Copyright 2008, Simon Sadedin, Badboy Software.
 * 
 * $Id: XmlWriter.groovy 1646 2008-11-07 00:48:28Z  $
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


package com.badboy.jbadboy

import com.badboy.jbadboy.item.ScriptItem
import com.badboy.jbadboy.item.Exportable
import static org.apache.commons.lang.StringEscapeUtils.*
import com.badboy.jbadboy.item.Summarizable
public class XmlWriter {
    
    void write(ScriptItem s, StringBuilder out) {
        this.write(s,out,0);
    }
    
    void write(ScriptItem s, StringBuilder out, int level) {
        // println "Using indent = " + level
        def indent = ""
        for(i in 0..level)
            indent += "  "
            
        def className = s.class.name.replaceAll(/^.*\./,"").replaceAll(/Impl$/,"")
            
        out << "$indent<${className}>\n";
        
        if(s instanceof Summarizable) {
            def summary = s.summary
            out << "$indent$indent<Summary>\n"
            for(i in ["played", "succeeded", "failed", "assertions","maxResponseTime", "averageResponseTime"]) {
                def value = summary[i]
                if(value != 0) {
		            out << "$indent$indent$indent<$i>${value}</$i>"
                }
            }
            out << "$indent$indent</Summary>\n"
        }
        
        if(s.subItems) {
            out << "$indent$indent<childCount>${s.subItems.size()}</childCount>\n"
            out << "$indent$indent<ChildItems>\n"
            s.subItems.each {
                this.write(it,out,level+1)
            }
            out << "$indent$indent</ChildItems>\n"
        }
        s.class.methods.findAll { it.isAnnotationPresent(Exportable.class) }.each { method ->
            
            def propName = method.name.replaceFirst("get","")
            propName = propName[0].toLowerCase() + propName.substring(1)
            
	        def propValue = String.valueOf(s[propName])
	        
            s.class.methods.findAll { exportMethod -> exportMethod.name == "${method.name}Export" }.each {
                propValue = String.valueOf(it.invoke(s))
            }
	        
            out << "$indent$indent<$propName>" << escapeXml(propValue) << "</$propName>" << "\n"
        }
        out << "$indent</${className}>\n";
    }
    
    /**
     * Writes an entire script including XML declaration, variables
     * and other context
     */
    String writeScript(ScriptContext ctx) {
        StringBuilder xml = new StringBuilder()
        
        xml << """<?xml version='1.0' encoding='UTF-8'?>
<WebScript v='30024'>
   <Variables>
   <count>${ctx.variables.size()}</count>
"""
        ctx.variables.each { name, var ->
            xml << "    <name>" << escapeXml(name) << "</name><value>"<< escapeXml(var.value) << "</value>\n"
        }
        xml << "  </Variables>\n"
        StringBuilder scriptXML = new StringBuilder()
        this.write(ctx.script,scriptXML)
        xml << scriptXML
        xml << "</WebScript>"
	    return xml.toString()
    }
}
