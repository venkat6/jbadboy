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

import java.util.Hashtable;

import com.badboy.jbadboy.Variable;
import com.badboy.jbadboy.model.Increment;

public class IncrementImpl extends Increment {
    
    final static int STRATEGY_DEFAULT = 0;
    final static int STRATEGY_RANDOM_INTEGER = 1;
    final static int STRATEGY_NEXT_LIST_VALUE = 2;
    final static int STRATEGY_NEXT_INTEGER = 3;
    
    static Hashtable<Integer, IncrementStrategy> strategies = new Hashtable<Integer, IncrementStrategy>();
    {
        strategies.put(STRATEGY_NEXT_LIST_VALUE, new NextListValueIncrementStrategy());
    }
    
    @Override
    public void play() throws ExecutionException {
        Variable v = this.context.getVariable(this.getVariableName()); 
        
        IncrementStrategy is = strategies.get(this.getStrategy());
        if(is != null)
            is.increment(v);
        else
            throw new ExecutionException("Increment strategy not implemented.");
    }    
}
