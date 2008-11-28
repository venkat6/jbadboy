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

import org.w3c.dom.NodeList;
import org.w3c.dom.html.*;

public interface QueryInterface {
    
    HTMLElement htmlElement(Object obj);
    
    HTMLFormElement formElement(Object obj);
    
    HTMLInputElement inputElement(Object obj);

    NodeList nodeList(Object obj);

    HTMLSelectElement selectElement(Object obj);

    HTMLOptionElement optionElement(Object obj);

    HTMLTextAreaElement textAreaElement(Object obj);

}
