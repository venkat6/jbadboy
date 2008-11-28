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
		
public class Assertion extends Playable {

    
    
	
    private Boolean captureDuplicates = Boolean.FALSE;

    @Exportable
    public Boolean getCaptureDuplicates() {
        return this.captureDuplicates;
    }
    public void setCaptureDuplicates(Boolean captureDuplicates) {
        this.captureDuplicates = captureDuplicates;
    }

    private Boolean captureScreenShot = Boolean.FALSE;

    @Exportable
    public Boolean getCaptureScreenShot() {
        return this.captureScreenShot;
    }
    public void setCaptureScreenShot(Boolean captureScreenShot) {
        this.captureScreenShot = captureScreenShot;
    }

    private Integer scalingPercent = 0;

    @Exportable
    public Integer getScalingPercent() {
        return this.scalingPercent;
    }
    public void setScalingPercent(Integer scalingPercent) {
        this.scalingPercent = scalingPercent;
    }
}
