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
import java.util.Arrays;
import java.util.List;

import org.w3c.dom.html.HTMLElement;

import com.badboy.jbadboy.browser.Browser;
import com.badboy.jbadboy.browser.DOMElementFilter;

/**
 * Locates a browser element by matching it's visible / displayed
 * text value on the screen.
 * 
 * @author ssadedin
 */
public class VisibleTextReference extends NavigationReference {

    public VisibleTextReference(String filter, String value) {
        super(filter, value);
    }

    /**
     * Finds all elements in the given Browser that match the
     * specification of value and filter for this VisibleTextReference. 
     */
    @Override
    public List<HTMLElement> findElement(Browser browser) {
        final List<String> tags = new ArrayList<String>(); 
        if("Links".equals(this.getFilter())) {
            tags.add("a"); 
        }
        else
        if("Buttons".equals(this.getFilter())) {
            tags.add("input");
            tags.add("button");
            tags.add("submit");
        }
        
        final List<String> inputButtonTypes = Arrays.asList("button","submit","image");
        final Browser b = browser;
        List<HTMLElement> elements = browser.filterElements(b.getBodyElement(), new DOMElementFilter() {
            public boolean accept(HTMLElement e) {
                String tagName = e.getTagName().toLowerCase();
                if(!tags.contains(tagName))
	                return false;
                if(tagName.equals("input") && !inputButtonTypes.contains(e.getAttribute("type")))
                    return false;
                
                if(value.equals(e.getAttribute("title")))
                    return true;
                if(value.equals(e.getAttribute("value")))
                    return true;
                String text = b.getElementText(e);
                if(value.equals(text)) 
                    return true;
 
                return false;
            }
        });
        
        for(HTMLElement e : elements) {
            System.out.println("Found node " + e.getTagName() + " with value " + e.getAttribute("value"));    
        }
       
        return elements;
    }
    
}
