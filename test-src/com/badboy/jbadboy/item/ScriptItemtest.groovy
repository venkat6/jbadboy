/**
 * Copyright 2008, Simon Sadedin, Badboy Software.
 * 
 * $Id: ScriptItemtest.groovy 1646 2008-11-07 00:48:28Z  $
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
import com.badboy.jbadboy.PlayListener

/**
 * @author ssadedin
 */
public class ScriptItemTest {
    
    static final junit.framework.Test suite(){
        return new junit.framework.JUnit4TestAdapter(ScriptItemTest.class);
    }
    
    @Test
    void testSubscribeUnsubscribe() {
        ScriptItem s = new ScriptItem();
        def called = false;
        PlayListener l = null;
        l = { item,evt -> called = true; assert item == s; s.unsubscribe(l); } as PlayListener
        s.subscribe(PlayEvent.ENTER, l)
        
        assert called == false
        s.publish(PlayEvent.ENTER);
        assert called == true
        
        called = false
        s.publish(PlayEvent.ENTER);
        assert called == false
    }
    
    @Test
    void testMultiSubscribe() {
        ScriptItem s = new ScriptItem();
        def called = false;
        def called2 = false;
        PlayListener l = null;
        l = { item,evt -> called = true; assert item == s;  } as PlayListener
        s.subscribe(PlayEvent.ENTER, l)
        s.subscribe(PlayEvent.LEAVE, l)
        s.subscribe(PlayEvent.ENTER, { item,evt -> called2 = true } as PlayListener)
        
        s.publish(PlayEvent.ENTERCHILD)
        assert called == false
        
        s.publish(PlayEvent.ENTER)
        assert called
        
        called = false
        s.publish(PlayEvent.LEAVE)
        assert called
        
        // Now unsubscribe - no more events should be received
        s.unsubscribe(l);
        called = false
        s.publish(PlayEvent.ENTER)
        assert !called
        s.publish(PlayEvent.LEAVE)
        assert !called
        
        // Should still get events to second listener
        called2 = false
        s.publish(PlayEvent.ENTER)
        assert called2
    }
}
