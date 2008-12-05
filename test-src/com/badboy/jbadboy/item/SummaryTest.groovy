/**
 * Copyright 2008, Simon Sadedin, Badboy Software.
 * 
 * $Id: SummaryTest.groovy 1646 2008-11-07 00:48:28Z  $
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

import static org.junit.Assert.*
import org.junit.Test

public class SummaryTest {

	@Test
	final void testAdd(){
	    def s1 = new Summary();
	    def s2 = new Summary(played:7,succeeded:4,failed:3,assertions:4,averageResponseTime:200.00,maxResponseTime:700, minTimeMs:100);
	    
	    // Add to blank
	    s1.add(s2);
	    assertEquals(7,s1.played)
	    assertEquals(4,s1.succeeded)
	    assertEquals(3,s1.failed)
	    assertEquals(4,s1.assertions)
	    assertTrue(200.00-s1.averageResponseTime<0.001)
	    assertEquals(700,s1.maxResponseTime)
	    
	    // Special case - because it's adding the first played item, it should accept 100 even though
	    // the initial value (0) is smaller than 100
	    assertEquals(100,s1.minTimeMs)
	    
	    
	    // Now add to non-blank
	    s1.minTimeMs = 100
	    def s3 = new Summary(played:2,succeeded:1,failed:1,assertions:1,averageResponseTime:600.00,maxResponseTime:1700, minTimeMs:50)
	    s1.add(s3)
	    assertEquals(9,s1.played)
	    assertEquals(5,s1.succeeded)
	    assertEquals(4,s1.failed)
	    assertEquals(5,s1.assertions)
	    assertTrue((7*200 + 2*600) / 9 - s1.averageResponseTime<0.001)
	    assertEquals(1700,s1.maxResponseTime)
	    assertEquals(50,s1.minTimeMs)
	}
	
	@Test
	void testPlayed() {
	    def s = new Summary()
	    s.played(true, 100)
	    assertEquals(1,s.getPlayed())
	    assertEquals(1,s.getSucceeded())
	    assertEquals(0,s.getFailed())
	    assertTrue(s.averageResponseTime-100.0 < 0.001)
	    assertEquals(100,s.maxResponseTime)
	    assertEquals(100,s.getMinTimeMs())
	    
	    s.played(false, 300)
	    assertEquals(2, s.getPlayed())
	    assertEquals(1, s.getSucceeded())
	    assertEquals(1, s.getFailed())
	    assertTrue(s.averageResponseTime-200.0 < 0.001)
	    assertEquals(300, s.maxResponseTime)
	    assertEquals(100, s.getMinTimeMs())
	}

}