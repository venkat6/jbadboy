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
		
public class ScheduleItem extends Playable {

    
    
	
    private Integer scriptType = 0;

    @Exportable
    public Integer getScriptType() {
        return this.scriptType;
    }
    public void setScriptType(Integer scriptType) {
        this.scriptType = scriptType;
    }

    private String delayMinutes = "";

    @Exportable
    public String getDelayMinutes() {
        return this.delayMinutes;
    }
    public void setDelayMinutes(String delayMinutes) {
        this.delayMinutes = delayMinutes;
    }

    private String scriptFile = "";

    @Exportable
    public String getScriptFile() {
        return this.scriptFile;
    }
    public void setScriptFile(String scriptFile) {
        this.scriptFile = scriptFile;
    }

    private Integer scheduleDelayType = 0;

    @Exportable
    public Integer getScheduleDelayType() {
        return this.scheduleDelayType;
    }
    public void setScheduleDelayType(Integer scheduleDelayType) {
        this.scheduleDelayType = scheduleDelayType;
    }

    private String delayHours = "";

    @Exportable
    public String getDelayHours() {
        return this.delayHours;
    }
    public void setDelayHours(String delayHours) {
        this.delayHours = delayHours;
    }

    private Boolean inheritVariables = Boolean.FALSE;

    @Exportable
    public Boolean getInheritVariables() {
        return this.inheritVariables;
    }
    public void setInheritVariables(Boolean inheritVariables) {
        this.inheritVariables = inheritVariables;
    }
}
