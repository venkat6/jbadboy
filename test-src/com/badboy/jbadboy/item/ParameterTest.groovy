/**
 * Copyright 2008, Simon Sadedin, Badboy Software.
 * 
 * $Id: ParameterTest.groovy 1646 2008-11-07 00:48:28Z  $
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


package com.badboy.jbadboy.item

import com.badboy.jbadboy.ScriptContext
import com.badboy.jbadboy.model.Parameter

import static org.junit.Assert.*
import org.junit.Test

public class ParameterTest {

	@Test
	final void testEncode(){
	    ScriptContext c = new ScriptContext()
	    def p = new ParameterImpl(context: c, name:"foo", value:"bar", alwaysSendEquals: false)
	    
	    // Simple case
	    assertEquals("foo=bar", p.encode("UTF-8"))
	    
	    // blank value
	    p.value=""
	    assertEquals("foo", p.encode("UTF-8"))
	    
	    // null value
	    p.value=null
	    assertEquals("foo", p.encode("UTF-8"))
	    
	    // always send equals
	    p.alwaysSendEquals = true
	    assertEquals("foo=", p.encode("UTF-8"))
	    
	    // using variable
	    c.setVariable("x","bar")
	    p.value = '${x}'
	    assertEquals("foo=bar", p.encode("UTF-8"))
	}

}