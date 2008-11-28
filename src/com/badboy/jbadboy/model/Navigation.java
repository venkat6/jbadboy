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
		
public class Navigation extends Playable {

    
    
	
    private Boolean continueOnFailure = Boolean.FALSE;

    @Exportable
    public Boolean getContinueOnFailure() {
        return this.continueOnFailure;
    }
    public void setContinueOnFailure(Boolean continueOnFailure) {
        this.continueOnFailure = continueOnFailure;
    }

    private Integer selectedReference = 0;

    @Exportable
    public Integer getSelectedReference() {
        return this.selectedReference;
    }
    public void setSelectedReference(Integer selectedReference) {
        this.selectedReference = selectedReference;
    }

    private Integer passiveTimeoutMs = 0;

    @Exportable
    public Integer getPassiveTimeoutMs() {
        return this.passiveTimeoutMs;
    }
    public void setPassiveTimeoutMs(Integer passiveTimeoutMs) {
        this.passiveTimeoutMs = passiveTimeoutMs;
    }

    private Boolean isPassive = Boolean.FALSE;

    @Exportable
    public Boolean getIsPassive() {
        return this.isPassive;
    }
    public void setIsPassive(Boolean isPassive) {
        this.isPassive = isPassive;
    }

    private String targetFrame = "";

    @Exportable
    public String getTargetFrame() {
        return this.targetFrame;
    }
    public void setTargetFrame(String targetFrame) {
        this.targetFrame = targetFrame;
    }

    private Integer eventType = 0;

    @Exportable
    public Integer getEventType() {
        return this.eventType;
    }
    public void setEventType(Integer eventType) {
        this.eventType = eventType;
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
