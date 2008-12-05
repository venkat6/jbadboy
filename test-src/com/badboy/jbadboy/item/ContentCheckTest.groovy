/**
 * Copyright 2008, Simon Sadedin, Badboy Software.
 * 
 * $Id: ContentCheckTest.groovy 1646 2008-11-07 00:48:28Z  $
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

import org.w3c.dom.html.HTMLElement
import com.badboy.jbadboy.browser.Browser
import com.badboy.jbadboy.model.Playable
import com.badboy.jbadboy.ScriptContext

import static org.junit.Assert.*
import org.junit.Test

public class ContentCheckTest {

	@Test
	final void testCheck(){
	    def c = new ContentCheckItemImpl();
	    
	    def body = [ : ] as HTMLElement
	    def browser = [ getBodyElement: { body }, 
	                    getElementText: { "foo bar bam 123 *" },
	                    getInnerHTML: { "foo bar bam 123 *" }  
	                  ] as Browser
	    def p = [ getTargetBrowser: { browser } ] as Playable
	    def ctx = [ getTargetBrowser: { browser } ] as ScriptContext
	    c.context = ctx
	    
	    c.pattern = "foo"
	    def result = c.check(p)
	    assertEquals(CheckExecutionStatus.SUCCEEDED,result.status)
	    
	    c.pattern = "frobulous"
	    result = c.check(p)
	    assertEquals(CheckExecutionStatus.FAILED,result.status)
	    
	    c.bUseRegex = true
	    c.pattern = "[ghf]oo"
	    result = c.check(p)
	    assertEquals(CheckExecutionStatus.SUCCEEDED,result.status)
	}

}