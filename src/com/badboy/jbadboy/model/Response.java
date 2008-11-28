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
		
public class Response extends ScriptItem {

    
    
	
    private String logContent = "";

    @Exportable
    public String getLogContent() {
        return this.logContent;
    }
    public void setLogContent(String logContent) {
        this.logContent = logContent;
    }

    private Boolean encodedContent = Boolean.FALSE;

    @Exportable
    public Boolean getEncodedContent() {
        return this.encodedContent;
    }
    public void setEncodedContent(Boolean encodedContent) {
        this.encodedContent = encodedContent;
    }
}
