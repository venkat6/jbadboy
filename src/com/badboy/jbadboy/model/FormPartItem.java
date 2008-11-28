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
		
public class FormPartItem extends ScriptItem {

    
    
	
    private String headers = "";

    @Exportable
    public String getHeaders() {
        return this.headers;
    }
    public void setHeaders(String headers) {
        this.headers = headers;
    }

    private String sourceType = "";

    @Exportable
    public String getSourceType() {
        return this.sourceType;
    }
    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    private String sourceFile = "";

    @Exportable
    public String getSourceFile() {
        return this.sourceFile;
    }
    public void setSourceFile(String sourceFile) {
        this.sourceFile = sourceFile;
    }

    private Boolean substituteVariables = Boolean.FALSE;

    @Exportable
    public Boolean getSubstituteVariables() {
        return this.substituteVariables;
    }
    public void setSubstituteVariables(Boolean substituteVariables) {
        this.substituteVariables = substituteVariables;
    }
}
