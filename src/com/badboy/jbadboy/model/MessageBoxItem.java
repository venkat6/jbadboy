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
		
public class MessageBoxItem extends Playable {

    
    
	
    private String message = "";

    @Exportable
    public String getMessage() {
        return this.message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    private Integer cascade = 0;

    @Exportable
    public Integer getCascade() {
        return this.cascade;
    }
    public void setCascade(Integer cascade) {
        this.cascade = cascade;
    }

    private String waitTimeSeconds = "";

    @Exportable
    public String getWaitTimeSeconds() {
        return this.waitTimeSeconds;
    }
    public void setWaitTimeSeconds(String waitTimeSeconds) {
        this.waitTimeSeconds = waitTimeSeconds;
    }

    private Boolean useRegex = Boolean.FALSE;

    @Exportable
    public Boolean getUseRegex() {
        return this.useRegex;
    }
    public void setUseRegex(Boolean useRegex) {
        this.useRegex = useRegex;
    }
}
