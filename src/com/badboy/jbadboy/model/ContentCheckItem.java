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
		
public class ContentCheckItem extends ScriptItem {

    
    
	
    private Boolean checkMessageBoxes = Boolean.FALSE;

    @Exportable
    public Boolean getCheckMessageBoxes() {
        return this.checkMessageBoxes;
    }
    public void setCheckMessageBoxes(Boolean checkMessageBoxes) {
        this.checkMessageBoxes = checkMessageBoxes;
    }

    private String pattern = "";

    @Exportable
    public String getPattern() {
        return this.pattern;
    }
    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    private Boolean checkRuntimeBodyContent = Boolean.FALSE;

    @Exportable
    public Boolean getCheckRuntimeBodyContent() {
        return this.checkRuntimeBodyContent;
    }
    public void setCheckRuntimeBodyContent(Boolean checkRuntimeBodyContent) {
        this.checkRuntimeBodyContent = checkRuntimeBodyContent;
    }

    private Boolean bUseRegex = Boolean.FALSE;

    @Exportable
    public Boolean getBUseRegex() {
        return this.bUseRegex;
    }
    public void setBUseRegex(Boolean bUseRegex) {
        this.bUseRegex = bUseRegex;
    }

    private Integer type = 0;

    @Exportable
    public Integer getType() {
        return this.type;
    }
    public void setType(Integer type) {
        this.type = type;
    }

    private Integer searchMode = 0;

    @Exportable
    public Integer getSearchMode() {
        return this.searchMode;
    }
    public void setSearchMode(Integer searchMode) {
        this.searchMode = searchMode;
    }
}
