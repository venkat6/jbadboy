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
		
public class ResponseCheckItem extends ScriptItem {

    
    
	
    private Integer minSizeKB = 0;

    @Exportable
    public Integer getMinSizeKB() {
        return this.minSizeKB;
    }
    public void setMinSizeKB(Integer minSizeKB) {
        this.minSizeKB = minSizeKB;
    }

    private Boolean checkSize = Boolean.FALSE;

    @Exportable
    public Boolean getCheckSize() {
        return this.checkSize;
    }
    public void setCheckSize(Boolean checkSize) {
        this.checkSize = checkSize;
    }

    private Integer maxSeconds = 0;

    @Exportable
    public Integer getMaxSeconds() {
        return this.maxSeconds;
    }
    public void setMaxSeconds(Integer maxSeconds) {
        this.maxSeconds = maxSeconds;
    }

    private Integer maxSizeKB = 0;

    @Exportable
    public Integer getMaxSizeKB() {
        return this.maxSizeKB;
    }
    public void setMaxSizeKB(Integer maxSizeKB) {
        this.maxSizeKB = maxSizeKB;
    }

    private Integer minSeconds = 0;

    @Exportable
    public Integer getMinSeconds() {
        return this.minSeconds;
    }
    public void setMinSeconds(Integer minSeconds) {
        this.minSeconds = minSeconds;
    }
}
