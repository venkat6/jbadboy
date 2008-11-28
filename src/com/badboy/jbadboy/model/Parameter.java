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



/**
 * This is a GENERATED FILE - DO NOT EDIT
 */
package com.badboy.jbadboy.model;

import com.badboy.jbadboy.item.*;
		
public class Parameter extends ScriptItem {

    
    
	
    private String name = "";

    @Exportable
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    private String value = "";

    @Exportable
    public String getValue() {
        return this.value;
    }
    public void setValue(String value) {
        this.value = value;
    }

    private Integer method = 0;

    @Exportable
    public Integer getMethod() {
        return this.method;
    }
    public void setMethod(Integer method) {
        this.method = method;
    }
}
