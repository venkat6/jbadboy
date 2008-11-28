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
		
public class Timer extends Playable {

    
    
	
    private String lowerMs = "";

    @Exportable
    public String getLowerMs() {
        return this.lowerMs;
    }
    public void setLowerMs(String lowerMs) {
        this.lowerMs = lowerMs;
    }

    private Boolean cascade = Boolean.FALSE;

    @Exportable
    public Boolean getCascade() {
        return this.cascade;
    }
    public void setCascade(Boolean cascade) {
        this.cascade = cascade;
    }

    private Integer waitType = 0;

    @Exportable
    public Integer getWaitType() {
        return this.waitType;
    }
    public void setWaitType(Integer waitType) {
        this.waitType = waitType;
    }

    private String upperMs = "";

    @Exportable
    public String getUpperMs() {
        return this.upperMs;
    }
    public void setUpperMs(String upperMs) {
        this.upperMs = upperMs;
    }

    private String waitMs = "";

    @Exportable
    public String getWaitMs() {
        return this.waitMs;
    }
    public void setWaitMs(String waitMs) {
        this.waitMs = waitMs;
    }
}
