/**
 * Copyright 2008, Simon Sadedin, Badboy Software.
 * 
 * $Id: NextListValueIncrementStrategy.groovy 1646 2008-11-07 00:48:28Z  $
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
 * 
 */
package com.badboy.jbadboy.item

import org.junit.Assert
import org.junit.Test
import com.badboy.jbadboy.Variable
import org.junit.Before

public class NextListValueIncrementStrategyTest {
    
    static final junit.framework.Test suite(){
        return new junit.framework.JUnit4TestAdapter(NextListValueIncrementStrategyTest.class);
    }
    
    NextListValueIncrementStrategy strategy
    
    @Before
    void setup() {
        strategy = new NextListValueIncrementStrategy();
    }
    
    @Test
    void testIncrement() {
        
        Variable v = new Variable("foo","bar");
        v.values << "a" << "b" << "c"
        assert v.value.size() == 3
        
        strategy.increment(v);
        assert v.value == "a"
        
        strategy.increment(v);
        assert v.value == "b"
        
        strategy.increment(v);
        assert v.value == "c"
        
        try {
            strategy.increment(v)
            fail("Increment should overflow")
        }
        catch(IncrementOverflowException e) {
            
        }
    }
    
}
