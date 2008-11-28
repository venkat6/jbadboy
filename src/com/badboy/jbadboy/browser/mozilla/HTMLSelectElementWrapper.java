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

import org.mozilla.interfaces.nsIDOMHTMLSelectElement;
import org.w3c.dom.html.HTMLCollection;
import org.w3c.dom.html.HTMLElement;
import org.w3c.dom.html.HTMLFormElement;
import org.w3c.dom.html.HTMLSelectElement;

public class HTMLSelectElementWrapper extends HTMLElementWrapper implements HTMLSelectElement {
    
    nsIDOMHTMLSelectElement obj = null;

    public HTMLSelectElementWrapper(nsIDOMHTMLSelectElement e) {
        super(e);
        this.obj = e;
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


     public int getLength() {
        return (int) obj.getLength();
     }


     public void setLength(int arg) {
        this.obj.setLength(arg);
     }


     public boolean getMultiple() {
        return obj.getMultiple();
     }


     public void setMultiple(boolean arg) {
        this.obj.setMultiple(arg);
     }


     public String getName() {
        return obj.getName();
     }


     public void setName(String arg) {
        this.obj.setName(arg);
     }


     public HTMLCollection getOptions() {
         throw new RuntimeException("Not implemented.");
     }


     public void setOptions(HTMLCollection arg) {
         throw new RuntimeException("Not implemented.");
     }


     public int getSelectedIndex() {
        return obj.getSelectedIndex();
     }


     public void setSelectedIndex(int arg) {
        this.obj.setSelectedIndex(arg);
     }


     public int getSize() {
        return obj.getSize();
     }


     public void setSize(int arg) {
        this.obj.setSize(arg);
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


     public void add(HTMLElement arg0, HTMLElement arg1) {
        throw new RuntimeException("Not implemented.");
     }


     public void blur() {
        this.obj.blur();                 
     }


     public void focus() {
        this.obj.focus();                 
     }


     public void remove(int arg0) {
        this.obj.remove(arg0);                 
     }
}
