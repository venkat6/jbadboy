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

import static com.badboy.jbadboy.util.Str.nvl;

import java.util.List;

import org.w3c.dom.NodeList;
import org.w3c.dom.html.*;

import com.badboy.jbadboy.browser.Browser;
import com.badboy.jbadboy.browser.DOMElementFilter;
import com.badboy.jbadboy.browser.QueryInterface;
import com.badboy.jbadboy.model.FormValue;

/**
 * Implementation of Form Populator
 * 
 * @author ssadedin
 */
public class FormValueImpl extends FormValue {
    
    public FormValueImpl() {
        this.setMatchVisibleText(false);
    }

    /**
     * Populate the element corresponding to this FormValue
     * in the given form with the value of this FormValue.
     * 
     * @param b
     * @param f
     * @throws ExecutionException
     */
    public void populate(Browser b, HTMLFormElement f) throws ExecutionException {
        HTMLElement e = this.searchElement(b, f);
        if(e == null) 
            throw new ExecutionException("Failed to locate form value element " + this.getName() + "["+ this.getIndex() + "]");
        
        QueryInterface cast = b.cast();
        HTMLInputElement inp = cast.inputElement(e);
        String val = this.context.eval(this.getValue());
        if(inp != null) {
	        
	        String type = inp.getType();
	        if(type == null)
	            type = "text";
	        type = type.toLowerCase();
	        if("checkbox".equals(type)) {
	            // For checkboxes, setChecked true for the value that matches
	            inp.setChecked(val.equals(inp.getValue()));
	        }
	        else
	        if("radio".equals(type)) {
	            // Set all other radios with the same name to unchecked
	            this.clearRadio(b, f,inp.getName());
	            inp.setChecked(val.equals(inp.getValue()));
	        }
	        else
			  inp.setValue(val);
	        
	        return;
	    }
        HTMLSelectElement sel = cast.selectElement(e);
        if(sel != null) {
            // Find the option that matches our value
            String value = nvl(val, "");
            NodeList options = sel.getElementsByTagName("option");
            for(int i=0; i< options.getLength(); ++i) {
                HTMLOptionElement opt = cast.optionElement(options.item(i));
                if(this.getMatchVisibleText()) {
                    if(value.equals(b.getElementText(opt))) {
	                    sel.setSelectedIndex(i);
	                    return;
                    }
                }
                else
                if(value.equals(opt.getValue())) {
                    sel.setSelectedIndex(i);
                    return;
                }
            }
        }
        HTMLTextAreaElement textArea = cast.textAreaElement(e);
        if(textArea != null) {
            textArea.setValue(val);
            return;
        }
    }
	
    /**
     * Clear all radio buttons with the given name in the given form
     */
    private void clearRadio(Browser b, HTMLFormElement f, final String name) {
        final QueryInterface qi = b.cast();
        b.filterElements(f, new DOMElementFilter() {
            @Override
            public boolean accept(HTMLElement e) {
                HTMLInputElement input = qi.inputElement(e);
                if(input != null) {
	                if("radio".compareToIgnoreCase(nvl(input.getType(),"text"))==0) {
	                    if(name.equals(input.getName()) || name.equals(input.getId()))
	                        input.setChecked(false);
	                }
                }
                return false;
            }
        });
    }

    /**
     * Searches the given form element for elements matching
     * this FormValue.  
     * 
     * @param b     top level browser containing elements
     * @param f     form to search in
     * @return element found if matching element exists, or null otherwise
     * @throws ExecutionException
     */
    public HTMLElement searchElement(Browser b, HTMLFormElement f) throws ExecutionException {
        
        final QueryInterface qi = b.cast();
        final String name = this.context.eval(getName());
        List<HTMLElement> results = b.filterElements(f, new DOMElementFilter() {
            @Override
            public boolean accept(HTMLElement e) {
                HTMLInputElement input = qi.inputElement(e);
                if(input != null) {
                    
                    String type = input.getType();
                    if(type == null)
                        type = "text";
                    type = type.toLowerCase();
                    
                    boolean nameMatch = name.equals(input.getName()) ||  name.equals(input.getId()); 
                    if(!nameMatch)
                        return false;
                    
                    // Radios and checkboxes only match if both their names and types match
                    if("radio".equals(type) || "checkbox".equals(type)) 
                        return ("checked".equals(getValue())) || getValue().equals(input.getValue());
                    
                    return true;
                }
                
                HTMLSelectElement select = qi.selectElement(e);
                if(select != null && name.equals(select.getName())) 
                    return true;
                
                HTMLTextAreaElement ta = qi.textAreaElement(e);
                if(ta != null && name.equals(ta.getName())) 
                    return true;
                
                return false;
            }
        });
        
        return results.size() > this.getIndex() ? qi.htmlElement(results.get(this.getIndex())) : null;
    }

}
