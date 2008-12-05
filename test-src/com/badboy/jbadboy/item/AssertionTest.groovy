/**
 * Copyright 2008, Simon Sadedin, Badboy Software.
 * 
 * $Id: AssertionTest.groovy 1646 2008-11-07 00:48:28Z  $
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

import com.badboy.jbadboy.model.AssertionFailure
import com.badboy.jbadboy.model.ContentCheckItem

import static org.junit.Assert.*
import org.junit.Test

public class AssertionTest {
 
	@Test
	final void testNoChecks(){
	    AssertionImpl ai = new AssertionImpl();
	    assertEquals(0,ai.summary.assertions)
	    ai.play()
	    assertEquals(0,ai.summary.assertions)
	}
	
	@Test
	final void testOneCheck(){
	    AssertionImpl ai = new AssertionImpl();
	    
	    // Test failed
	    ContentCheckItem check1 = [ check: {new CheckResult(CheckExecutionStatus.FAILED,"unit test")} ] as ContentCheckItemImpl
	    ai.addChild(check1)
	    assertEquals(0,ai.summary.assertions)
	    ai.play()
	    assertEquals(1,ai.summary.assertions)
	    
	    // Test succeeded
	    ai.subItems.clear()
	    ai.summary.clear()
	    ContentCheckItem check2 = [ check: {new CheckResult(CheckExecutionStatus.SUCCEEDED,"unit test")} ] as ContentCheckItemImpl
	    ai.addChild(check1)
	    ai.addChild(check2)
	    ai.play()
	    
	    // 1 succeeded, 1 failed
	    assertEquals(1,ai.summary.assertions)
	    
	    // Should now have 3 assertion failures as children
	    ai.subItems.collect { it instanceof AssertionFailure }.size == 3
	}
}