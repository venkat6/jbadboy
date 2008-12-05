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


package com.badboy.jbadboy;

import com.badboy.jbadboy.item.ScriptItem;
import com.badboy.jbadboy.util.Algorithm.Filter;

public class ScriptIterator {
    
    public enum Location {
        BEFORE,
        ON,
        AFTER
    }
    
    ScriptItem position;
    
    Location location = Location.BEFORE;

    public ScriptIterator(ScriptItem position) {
        super();
        this.position = position;
    }
    
    public ScriptItem forward() {
    	
        if(location == Location.AFTER)
            return null;
        
        if(location == Location.BEFORE && this.position != null) {
            location = Location.ON;
            return this.position;
        }
        
        // Depth first recursion:  play this item's first child if it has one
        if(!this.position.getSubItems().isEmpty()) {
            this.position = this.position.getSubItems().get(0);
            return this.position;
        }
        
        // No playable child found
        while(true) {
            // Move to next item in parent
            ScriptItem parent = position.getParent();
            if(parent == null) {
                location = Location.AFTER;
                return null;  // No parent!  we must be finished
            }
            
            int index = parent.getSubItems().indexOf(position);
            assert (index>=0) : "Child item should be in parent's subItem list!";
            
            if(index < parent.getSubItems().size()-1) {
                this.position = parent.getSubItems().get(index+1);
                return this.position;
            }
            else {
                position = parent;
            }
        }
    }
    
    public ScriptItem next(Filter<ScriptItem> p) {
        while(true) {
            ScriptItem i = this.forward();
            if(i == null)
                return null;
            
            if(p.$(i))
                return i;
        }
    }
    
    public ScriptItem get() {
        switch(location) {
        case BEFORE:
            return null;
        case ON:
            return this.position;
        case AFTER:
            return null;
        default: 
            return null;
        }
    }

    public void seek(ScriptItem item, Location l) {
        this.position = item;
        this.location = l;
    }

}
