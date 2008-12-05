/**
 * Copyright 2008, Simon Sadedin, Badboy Software.
 * 
 * $Id: XmlWriterTest.groovy 1646 2008-11-07 00:48:28Z  $
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
import com.badboy.jbadboy.model.Assertion
import com.badboy.jbadboy.item.Script

import static org.junit.Assert.*
import org.junit.Test

public class XmlWriterTest {

	@Test
	final void testWrite(){
	    XmlWriter w = new XmlWriter()
	    ScriptItem s = new ScriptItem();
	    StringBuilder output = new StringBuilder()
	    w.write(s, output)
	    println output
	    
	    Assertion a = new Assertion()
	    output = new StringBuilder()
	    w.write(a, output)
	    // println  output
	    
	    println "=================="
	    s.addChild(a)
	    output = new StringBuilder()
	    w.write(s, output)
	    // println  output
	}

	@Test
	final void testWriteScript() {
	    println "=================="
	    XmlWriter w = new XmlWriter()
	    Script s = new Script();
	    ScriptContext ctx = new ScriptContext(script:s)
	    def xml = w.writeScript(ctx)
	    println xml
	    
	    def x = new XmlSlurper().parseText(xml)
	    assertEquals("0", x.Script.id.text())
	    println "=================="
	    
	}
	
	@Test
	void testEscaping() {
	   	XmlWriter w = new XmlWriter()
	    Script s = new Script();
	    Assertion a = new Assertion()
	    a.itemName = "<hello>"
	    s.addChild(a)
	    ScriptContext ctx = new ScriptContext(script:s)
	    def xml = w.writeScript(ctx)
	    def x = new XmlSlurper().parseText(xml)
	    assertEquals("<hello>", x.Script.ChildItems.Assertion.itemName.text())
	    println "=================="
	    
	    ctx.setVariable("<test", "<world>")
	    x = new XmlSlurper().parseText(w.writeScript(ctx))
	    assertEquals("<world>", x.Variables.value.text())
	}
	
	@Test
	void testSummary() {
	       XmlWriter w = new XmlWriter()
	        Assertion a = new Assertion()
	        a.summary.played(true,500)
	        ScriptContext ctx = new ScriptContext(script:a)
	        def xml = w.writeScript(ctx)
	        def x = new XmlSlurper().parseText(xml)
	        assertEquals("1", x.Assertion.Summary.played.text())
	        assertEquals("500", x.Assertion.Summary.maxResponseTime.text())
	        assertTrue(500 - Float.parseFloat(x.Assertion.Summary.averageResponseTime.text()) < 0.001) 
	        println "=================="
	}
}