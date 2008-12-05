/**
 * Copyright 2008, Simon Sadedin, Badboy Software.
 * 
 * $Id: StepTest.groovy 1646 2008-11-07 00:48:28Z  $
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

import static org.junit.Assert.*
import org.junit.Test
import com.badboy.jbadboy.ScriptContext
import com.badboy.jbadboy.Variable
import com.badboy.jbadboy.ScriptEngine
/**
 * @author ssadedin
 *
 */
public class StepTest{
    
	 /*
    static final junit.framework.Test suite(){
        return new junit.framework.JUnit4TestAdapter(StepTest.class);
    }
    */
    
    @Test
    void testPlay() {
    	ScriptEngine engine = new ScriptEngine()
        ScriptContext ctx = new ScriptContext(scriptEngine:engine)
    	
        Variable v = new Variable("hello","world")
        v.dataSourceId = "123"
        v.values << "foo" << "bar" << "tree"
        ctx.variables["hello"] = v
        ctx.variables["z"] = new Variable(name:"z", value:"world", dataSourceId:"123", values:["a", "b", "c"]);
        
        StepImpl s = new StepImpl(context: ctx, repeatType: StepImpl.REPEAT_TYPE_VARIABLE, repeatVariable: "hello");
        s.addChild(new ScriptItem());
    	ctx.script = s
    	engine.scriptContext = ctx
        
        // Play the step, variable should get set to the first value
        s.play()
        assert v.value == "foo"
        assert ctx.variables["z"].value == "a"
        
        s.publish(PlayEvent.LEAVE);
        assert v.value == "bar"
        assert ctx.variables["z"].value == "b"
        
        s.publish(PlayEvent.LEAVE);
        assert v.value == "tree"
        assert ctx.variables["z"].value == "c"
        
        s.publish(PlayEvent.LEAVE);
        assert v.value == "tree"
        assert ctx.variables["z"].value == "c"
    }
    
}
