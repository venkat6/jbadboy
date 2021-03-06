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

import org.w3c.dom.html.HTMLElement;

import com.badboy.jbadboy.browser.Browser;

public class NameOrIdReference extends NavigationReference {

    public NameOrIdReference(String filter, String value) {
        super(filter, value);
    }

    @Override
    public List<HTMLElement> findElement(Browser browser) {
        // TODO Auto-generated method stub
        return null;
    }

}
