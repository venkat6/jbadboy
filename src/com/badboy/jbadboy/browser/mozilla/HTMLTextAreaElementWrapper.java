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

import org.mozilla.interfaces.nsIDOMHTMLTextAreaElement;
import org.w3c.dom.html.HTMLFormElement;
import org.w3c.dom.html.HTMLTextAreaElement;

public class HTMLTextAreaElementWrapper extends HTMLElementWrapper implements HTMLTextAreaElement {
    
    nsIDOMHTMLTextAreaElement obj;

    public HTMLTextAreaElementWrapper(nsIDOMHTMLTextAreaElement e) {
        super(e);
        this.obj = e;
    }

    public String getAccessKey() {
        return obj.getAccessKey();
     }


     public void setAccessKey(String arg) {
        this.obj.setAccessKey(arg);
     }


     public int getCols() {
        return obj.getCols();
     }


     public void setCols(int arg) {
        this.obj.setCols(arg);
     }


     public String getDefaultValue() {
        return obj.getDefaultValue();
     }


     public void setDefaultValue(String arg) {
        this.obj.setDefaultValue(arg);
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


     public String getName() {
        return obj.getName();
     }


     public void setName(String arg) {
        this.obj.setName(arg);
     }


     public boolean getReadOnly() {
        return obj.getReadOnly();
     }


     public void setReadOnly(boolean arg) {
        this.obj.setReadOnly(arg);
     }


     public int getRows() {
        return obj.getRows();
     }


     public void setRows(int arg) {
        this.obj.setRows(arg);
     }


     public int getTabIndex() {
        return obj.getTabIndex();
     }


     public void setTabIndex(int arg) {
        this.obj.setTabIndex(arg);
     }


     public String getType() {
        return obj.getType();
     }


     public void setType(String arg) {
        throw new RuntimeException("Not implemented.");
     }


     public String getValue() {
        return obj.getValue();
     }


     public void setValue(String arg) {
        this.obj.setValue(arg);
     }


     public void blur() {
        this.obj.blur();                 
     }


     public void focus() {
        this.obj.focus();                 
     }


     public void select() {
        this.obj.select();                 
     }
}
