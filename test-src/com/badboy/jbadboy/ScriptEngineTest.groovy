/**
 * Copyright 2008, Simon Sadedin, Badboy Software.
 * 
 * $Id: ScriptEngineTest.groovy 1646 2008-11-07 00:48:28Z  $
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
package com.badboy.jbadboy

import com.badboy.jbadboy.browser.Browser
import com.badboy.jbadboy.model.Playable
import com.badboy.jbadboy.item.ScriptItem
import org.junit.Test
import org.junit.Before
import com.badboy.jbadboy.item.PlayEvent
public class MockItem extends Playable {
  boolean played = false
  void play() {
    played = true    
  }
}

public class ChildItem extends MockItem {
}
public class GrandChildItem extends MockItem {
}

public class ScriptEngineTest  {
    
    static final junit.framework.Test suite(){
        return new junit.framework.JUnit4TestAdapter(ScriptEngineTest.class);
    }
    
    ScriptItem suite = new MockItem()
    ScriptItem testItem = new ChildItem()
    ScriptItem testItem2 = new ChildItem()
    ScriptItem childItem = new GrandChildItem()
    ScriptContext context = null;
    ScriptEngine engine = null;
    
    @Before 
    void setup() {
        suite = new MockItem()
        testItem = new ChildItem()
        testItem2 = new ChildItem()
        childItem = new GrandChildItem()
        suite.addChild(testItem).addChild(testItem2)
        testItem.addChild(childItem)
        context = new ScriptContext(script:suite);
        engine = new ScriptEngine(scriptContext:context);
    }
    
    @Test
    void testPlaySingleItem() {
        Browser browser = [:] as Browser
        ScriptItem mockItem = new MockItem()
        ScriptContext context = new ScriptContext(browser:browser, script:mockItem);
        ScriptEngine engine = new ScriptEngine(scriptContext:context);
        engine.play()
        assert mockItem.played
    }
    
    @Test
    void testPlayHierarchy() {
       
        engine.play()
        
        assert suite.played
        assert testItem.played
        assert testItem2.played
        assert childItem.played
    }
    
    
    @Test
    void testPlayEvents() {
        def left = false;
        testItem.subscribe(PlayEvent.LEAVE, { s,evt ->  left = true; } as PlayListener )
        engine.play()
        assert left 
    }
}
