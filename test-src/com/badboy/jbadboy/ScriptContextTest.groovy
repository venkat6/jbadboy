/**
 * Copyright 2008, Simon Sadedin, Badboy Software.
 * 
 * $Id: ScriptContextTest.groovy 1646 2008-11-07 00:48:28Z  $
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

import org.testng.annotations.ExpectedExceptions
import org.junit.Test
import com.badboy.jbadboy.item.ExecutionException

import static org.junit.Assert.*
import org.junit.Test

public class ScriptContextTest {

	@Test
	final void testVariableSubstitution(){
	    def ctx = new ScriptContext();
	    
	    ctx.setVariable("x", "tree");
	    ctx.setVariable("y", '${x}');
	    
	    assertEquals("tree",ctx.eval('${x}'))
	    assertEquals("tree tree",ctx.eval('${x} ${x}'))
	    assertEquals("tree",ctx.eval('${y}'))
	    assertEquals("tree tree tree",ctx.eval('${x} ${y} ${y}'))
	    
	    // Check invalid sequences
	    assertEquals('${',ctx.eval('${'))
	    assertEquals('{',ctx.eval('{'))
	    assertEquals('}',ctx.eval('}'))
	    assertEquals('',ctx.eval('${{}'))
	}
	    
	@Test
	final void testRecursiveVariableSubstitution() {

	    try {
		    def ctx = new ScriptContext();
		    ctx.setVariable('r','${r}')
		    ctx.eval('${r}')
		    fail("Should not get here");
	    }
	    catch(ExecutionException ex) {
	          
	    }
	}

}