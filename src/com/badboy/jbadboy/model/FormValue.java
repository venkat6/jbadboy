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
		
public class FormValue extends ScriptItem {

    
    
	
    private Integer index = 0;

    @Exportable
    public Integer getIndex() {
        return this.index;
    }
    public void setIndex(Integer index) {
        this.index = index;
    }

    private String name = "";

    @Exportable
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    private Boolean matchVisibleText = Boolean.FALSE;

    @Exportable
    public Boolean getMatchVisibleText() {
        return this.matchVisibleText;
    }
    public void setMatchVisibleText(Boolean matchVisibleText) {
        this.matchVisibleText = matchVisibleText;
    }

    private String value = "";

    @Exportable
    public String getValue() {
        return this.value;
    }
    public void setValue(String value) {
        this.value = value;
    }
}
