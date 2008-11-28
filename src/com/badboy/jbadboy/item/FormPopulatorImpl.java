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

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.html.HTMLElement;
import org.w3c.dom.html.HTMLFormElement;

import com.badboy.jbadboy.browser.Browser;
import com.badboy.jbadboy.browser.DOMElementFilter;
import com.badboy.jbadboy.browser.QueryInterface;
import com.badboy.jbadboy.model.FormPopulator;

/**
 * Implements behavior of a FormPopulator item from a Badboy script.
 * <p>
 * Note: <i>at this time clearing and submitting the form are not implemented.</i>
 * 
 * @author ssadedin
 */
public class FormPopulatorImpl extends FormPopulator {
    
    private static Log log = LogFactory.getLog(FormPopulatorImpl.class);

    @Override
    public void play() throws ExecutionException {
        // Locate the form
        HTMLFormElement f = findForm();
        if(f == null) 
            throw new ExecutionException("Failed to locate form with name " + this.getFormName() + " at index " + this.getFormIndex());
        
        log.info("found matching form " + f + " name  " + f.getName());
        this.populate(f);
    }

    protected void populate(HTMLFormElement f) throws ExecutionException {
        for(ScriptItem c : this.getSubItems()) {
            if(c instanceof FormValueImpl) {
                FormValueImpl v = (FormValueImpl) c;
                v.populate(this.context.getTargetBrowser(this), f);
            }
        }
    }

    protected HTMLFormElement findForm() {
        Browser b = this.context.getTargetBrowser(this);
        List<HTMLElement> forms = b.filterElements(b.getBodyElement(), new DOMElementFilter() {
            @Override
            public boolean accept(HTMLElement e) {
                return "form".compareToIgnoreCase(e.getTagName()) == 0;
            }
        });
        
        QueryInterface qi = b.cast();
        for(int i = 0; i<forms.size(); ++i) {
            HTMLFormElement form = qi.formElement(forms.get(i));
            assert form != null : "received non-form element from query that should only return forms";
            log.info("found form " + form + " name  " + form.getName());
            if(this.getFormName().equals(form.getName())) {
		        return form;
            }
        }
        return null;
    }
}
