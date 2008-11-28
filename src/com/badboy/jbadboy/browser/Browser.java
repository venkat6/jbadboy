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

import java.util.List;

import org.w3c.dom.html.HTMLElement;

public interface Browser {
    
    public static enum State {
        NEW,
        LOADING,
        COMPLETE
    }

    /**
     * Signal to which interested parties can subscribe to receive event
     * notifications
     */
    public LoadStateSignal getLoadStateSignal();
    
    /**
     * Navigate to the given URL, sending the given POST data
     * 
     * @param url
     * @param postData - optional.  for GET send null.
     */
    public void navigate(String url, String targetFrame, String postData);
    
    /**
     * Return an HTMLELement representing the Body of the HTML document 
     * loaded in the browser
     */
    public HTMLElement getBodyElement();

    /**
     * Simulate a mouse click on the given element
     * 
     * @param element
     */
    public void click(HTMLElement element);
    
    /**
     * Return text inside specified element
     * (equivalent of IE innerText property)
     */
    public String getElementText(HTMLElement element);
    
    /**
     * Return HTML for given element
     */
    public String getInnerHTML(HTMLElement element);
    
    /**
     * Return a list of dom elements from whole document hierarchy matching the given filter.
     * <p>
     * IFrame elements themselves will not be passed to the filter.  Instead,the
     * iframe's DOM document will be seamlessly recursed into.
     * 
     * @return list of HTMLElement's matching the specified filter, in the order they
     *         appear in the browser document and frame hierarchy
     */
    public List<HTMLElement> filterElements(HTMLElement root, DOMElementFilter e);
    
    /**
     * Return adaptor for casting the provided object to different interfaces
     */
    public QueryInterface cast();

    /**
     * Execute the given script
     */
    public void executeScript(String script);
    
}
