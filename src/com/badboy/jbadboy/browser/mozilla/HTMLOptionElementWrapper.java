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


package com.badboy.jbadboy.browser.mozilla;

import org.mozilla.interfaces.nsIDOMHTMLOptionElement;
import org.w3c.dom.html.HTMLFormElement;
import org.w3c.dom.html.HTMLOptionElement;

public class HTMLOptionElementWrapper extends HTMLElementWrapper implements HTMLOptionElement {
    
    nsIDOMHTMLOptionElement obj;

    public HTMLOptionElementWrapper(nsIDOMHTMLOptionElement e) {
        super(e);
        obj = e;
    }

    

    public boolean getDefaultSelected() {
       return obj.getDefaultSelected();
    }


    public void setDefaultSelected(boolean arg) {
       this.obj.setDefaultSelected(arg);
    }


    public boolean getDisabled() {
       return obj.getDisabled();
    }


    public void setDisabled(boolean arg) {
       this.obj.setDisabled(arg);
    }


    public HTMLFormElement getForm() {
       return new HTMLFormElementWrapper(obj.getForm());
    }


    public void setForm(HTMLFormElement arg) {
        throw new RuntimeException("Not implemented.");
    }


    public int getIndex() {
       return obj.getIndex();
    }


    public void setIndex(int arg) {
        throw new RuntimeException("Not implemented.");
    }


    public String getLabel() {
       return obj.getLabel();
    }


    public void setLabel(String arg) {
       this.obj.setLabel(arg);
    }


    public boolean getSelected() {
       return obj.getSelected();
    }


    public void setSelected(boolean arg) {
       this.obj.setSelected(arg);
    }


    public String getText() {
       return obj.getText();
    }


    public void setText(String arg) {
        throw new RuntimeException("Not implemented.");
    }


    public String getValue() {
       return obj.getValue();
    }


    public void setValue(String arg) {
       this.obj.setValue(arg);
    }

}
