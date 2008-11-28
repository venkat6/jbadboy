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
		
public class SummaryCheckItem extends ScriptItem {

    
    
	
    private Integer locatorType = 0;

    @Exportable
    public Integer getLocatorType() {
        return this.locatorType;
    }
    public void setLocatorType(Integer locatorType) {
        this.locatorType = locatorType;
    }

    private Integer combineType = 0;

    @Exportable
    public Integer getCombineType() {
        return this.combineType;
    }
    public void setCombineType(Integer combineType) {
        this.combineType = combineType;
    }

    private String summarySelector = "";

    @Exportable
    public String getSummarySelector() {
        return this.summarySelector;
    }
    public void setSummarySelector(String summarySelector) {
        this.summarySelector = summarySelector;
    }
}
