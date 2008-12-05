/**
 * Copyright 2008, Simon Sadedin, Badboy Software.
 * 
 * $Id$
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


package com.badboy.jbadboy.item;

import java.util.ArrayList;
import java.util.List;

import com.badboy.jbadboy.PlayListener;
import com.badboy.jbadboy.ScriptIterator;
import com.badboy.jbadboy.Variable;
import com.badboy.jbadboy.model.Step;
import com.badboy.jbadboy.util.Function;
import com.badboy.jbadboy.util.Str;

public class StepImpl extends Step {
    
    private static final int REPEAT_TYPE_FIXED = 1;
    private static final int REPEAT_TYPE_VARIABLE = 2;
    
    private int currentIndex = 0;

    private NextListValueIncrementStrategy increment = new NextListValueIncrementStrategy();
    
    @Override
    public void play() throws ExecutionException {
        
        final ScriptItem s = this;
        
        this.currentIndex = 0;
        
        final List<Variable> repeatVariables = resolveRepeatVariables();
        
        if(getRepeatType() == REPEAT_TYPE_VARIABLE) {
            for(Variable v : repeatVariables) {
                v.setCurrentIndex(0);
                v.setValue(v.getValue(0));
            }
        }
        
        this.subscribe(PlayEvent.LEAVE, new PlayListener() {
            public void onEvent(ScriptItem scriptItem, PlayEvent event) throws ExecutionException {
                if(getRepeatType() == REPEAT_TYPE_FIXED) {
                    ++currentIndex;
                    if(currentIndex<=getRepetitions())  {
                        seekFirstChild();
                    }
                    else {
                        unsubscribe(this);
                    }
                }
                else 
                if(getRepeatType() == REPEAT_TYPE_VARIABLE) {
                    for(Variable v : repeatVariables) {
                        try {
                            increment.increment(v); 
                            seekFirstChild();
                        }
                        catch (IncrementOverflowException e) {
                            unsubscribe(this);
                        }
                    }
                }
            }
        });
    }
    
    private void seekFirstChild() {
        getContext().getScriptEngine().getPlayPosition().seek(this.getSubItems().get(0), ScriptIterator.Location.ON); 
    }

    /**
     * Search for repeat variables with data sources configured for this step, if any.
     */
    private List<Variable> resolveRepeatVariables() throws ExecutionException {
        Variable v = context.getVariable(this.getRepeatVariable());
        if(v == null)
            throw new ExecutionException("Unknown variable " + this.getRepeatVariable() + " specified as repeat variable");
        
        String dataSourceId = v.getDataSourceId();
        List<Variable> results = new ArrayList<Variable>();
        for(Variable var : context.getVariables().values()) {
           if(Str.eq(var.getDataSourceId(),dataSourceId)) {
               results.add(var);
           }
        }
        return results;
    }
    
    
}
