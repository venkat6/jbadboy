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

import org.mozilla.interfaces.nsIDOMHTMLFormElement;
import org.w3c.dom.html.HTMLCollection;
import org.w3c.dom.html.HTMLFormElement;

public class HTMLFormElementWrapper extends HTMLElementWrapper implements
        HTMLFormElement {

    public HTMLFormElementWrapper(nsIDOMHTMLFormElement e) {
        super(e);
        this.obj = e;
    }


    nsIDOMHTMLFormElement obj;

    public String getAcceptCharset() {
       return obj.getAcceptCharset();
    }


    public void setAcceptCharset(String arg) {
       this.obj.setAcceptCharset(arg);
    }


    public String getAction() {
       return obj.getAction();
    }


    public void setAction(String arg) {
       this.obj.setAction(arg);
    }


    public HTMLCollection getElements() {
       throw new RuntimeException("Not implemented");
    }


    public void setElements(HTMLCollection arg) {
       throw new RuntimeException("Not implemented");
    }


    public String getEnctype() {
       return obj.getEnctype();
    }


    public void setEnctype(String arg) {
       this.obj.setEnctype(arg);
    }


    public int getLength() {
       return obj.getLength();
    }


    public void setLength(int arg) {
        throw new RuntimeException("Not implemented");
    }


    public String getMethod() {
       return obj.getMethod();
    }


    public void setMethod(String arg) {
       this.obj.setMethod(arg);
    }


    public String getName() {
       return obj.getName();
    }


    public void setName(String arg) {
       this.obj.setName(arg);
    }


    public String getTarget() {
       return obj.getTarget();
    }


    public void setTarget(String arg) {
       this.obj.setTarget(arg);
    }


    public void reset() {
       this.obj.reset();                 
    }


    public void submit() {
       this.obj.submit();                 
    }
}
