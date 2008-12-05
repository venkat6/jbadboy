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

import org.apache.commons.lang.NotImplementedException;

import com.badboy.jbadboy.item.*;
		
public class Playable extends ScriptItem implements Summarizable {

    
	/**
	 * Default play method - this method should be overridden by child 
	 * classes to implement actual playback behavior.
	 * 
	 * @throws ExecutionException
	 */
    public void play() throws ExecutionException {
    	throw new NotImplementedException("Item of type " + this.getClass().getName() + " is not implemented in JBadboy");
    }

	Summary summary;
	
	public Summary getSummary() {
	  if(summary == null)
        summary = new Summary();
	  return summary;
	}

	
    private String itemName = "";

    @Exportable
    public String getItemName() {
        return this.itemName;
    }
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    private Boolean timeoutEnabled = Boolean.FALSE;

    @Exportable
    public Boolean getTimeoutEnabled() {
        return this.timeoutEnabled;
    }
    public void setTimeoutEnabled(Boolean timeoutEnabled) {
        this.timeoutEnabled = timeoutEnabled;
    }

    private Integer timeoutSeconds = 0;

    @Exportable
    public Integer getTimeoutSeconds() {
        return this.timeoutSeconds;
    }
    public void setTimeoutSeconds(Integer timeoutSeconds) {
        this.timeoutSeconds = timeoutSeconds;
    }

    private Boolean recordResponses = Boolean.FALSE;

    @Exportable
    public Boolean getRecordResponses() {
        return this.recordResponses;
    }
    public void setRecordResponses(Boolean recordResponses) {
        this.recordResponses = recordResponses;
    }

    private String sourceLocation = "";

    @Exportable
    public String getSourceLocation() {
        return this.sourceLocation;
    }
    public void setSourceLocation(String sourceLocation) {
        this.sourceLocation = sourceLocation;
    }

    private Boolean waitForPageToLoad = Boolean.FALSE;

    @Exportable
    public Boolean getWaitForPageToLoad() {
        return this.waitForPageToLoad;
    }
    public void setWaitForPageToLoad(Boolean waitForPageToLoad) {
        this.waitForPageToLoad = waitForPageToLoad;
    }

    private String documentation = "";

    @Exportable
    public String getDocumentation() {
        return this.documentation;
    }
    public void setDocumentation(String documentation) {
        this.documentation = documentation;
    }

    private String targetWindow = "";

    @Exportable
    public String getTargetWindow() {
        return this.targetWindow;
    }
    public void setTargetWindow(String targetWindow) {
        this.targetWindow = targetWindow;
    }

    private String errorFilter = "";

    @Exportable
    public String getErrorFilter() {
        return this.errorFilter;
    }
    public void setErrorFilter(String errorFilter) {
        this.errorFilter = errorFilter;
    }

    private Boolean isInherited = Boolean.FALSE;

    @Exportable
    public Boolean getIsInherited() {
        return this.isInherited;
    }
    public void setIsInherited(Boolean isInherited) {
        this.isInherited = isInherited;
    }

    private Boolean disabled = Boolean.FALSE;

    @Exportable
    public Boolean getDisabled() {
        return this.disabled;
    }
    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    private Boolean ignoreErrors = Boolean.FALSE;

    @Exportable
    public Boolean getIgnoreErrors() {
        return this.ignoreErrors;
    }
    public void setIgnoreErrors(Boolean ignoreErrors) {
        this.ignoreErrors = ignoreErrors;
    }
    
}
