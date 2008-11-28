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

import org.mozilla.browser.MozillaWindow;

import com.badboy.jbadboy.browser.Browser;
import com.badboy.jbadboy.browser.LoadStateListener;
import com.badboy.jbadboy.browser.Browser.State;

/**
 * A utility class that listens to the internal loading
 * events of the {@link MozillaWindow} and provides a blocking
 * method that only returns when the window has completed 
 * loading.
 * 
 * @author ssadedin
 */
public class NavigationWaiter implements LoadStateListener {
    
    State state = State.NEW;
    
    Browser browser = null;
    
    public NavigationWaiter(Browser browser) {
        this.browser = browser;
        browser.getLoadStateSignal().subscribe(this); 
    }

    /**
     * Wait for window to load and only return when it is finished.
     */
    public void waitForNavigation() {
        try {
            // TODO: Implement a proper quiescence algorithm to wait 1000ms after the last complete
            while(state != State.COMPLETE) {
                try { Thread.sleep(1000); } catch (InterruptedException e) { }
            }
        }
        finally {
            browser.getLoadStateSignal().unsubscribe(this);
        }
    }

    @Override
    public void stateChange(State state) {
        this.state = state;
    }
}
