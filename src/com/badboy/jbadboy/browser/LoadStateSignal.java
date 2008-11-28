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


package com.badboy.jbadboy.browser;

import java.util.HashSet;
import java.util.Set;

import com.badboy.jbadboy.item.NavigationWaiter;

public class LoadStateSignal {
    
    Set<LoadStateListener> listeners = new HashSet<LoadStateListener>();
    
    public synchronized void subscribe(LoadStateListener l) {
        listeners.add(l);
    }
    
    public synchronized void notifyStateChange(Browser.State state) {
        for(LoadStateListener l : listeners) {
            l.stateChange(state);
        }
    }

    public synchronized void unsubscribe(NavigationWaiter navigationWaiter) {
        this.listeners.remove(navigationWaiter);
    }
}
