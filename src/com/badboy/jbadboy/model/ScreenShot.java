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
		
public class ScreenShot extends Playable {

    
    
	
    private String sourceItemId = "";

    @Exportable
    public String getSourceItemId() {
        return this.sourceItemId;
    }
    public void setSourceItemId(String sourceItemId) {
        this.sourceItemId = sourceItemId;
    }

    private Boolean addLabel = Boolean.FALSE;

    @Exportable
    public Boolean getAddLabel() {
        return this.addLabel;
    }
    public void setAddLabel(Boolean addLabel) {
        this.addLabel = addLabel;
    }

    private String source = "";

    @Exportable
    public String getSource() {
        return this.source;
    }
    public void setSource(String source) {
        this.source = source;
    }

    private String fileName = "";

    @Exportable
    public String getFileName() {
        return this.fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    private Boolean saveToFile = Boolean.FALSE;

    @Exportable
    public Boolean getSaveToFile() {
        return this.saveToFile;
    }
    public void setSaveToFile(Boolean saveToFile) {
        this.saveToFile = saveToFile;
    }

    private String label = "";

    @Exportable
    public String getLabel() {
        return this.label;
    }
    public void setLabel(String label) {
        this.label = label;
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
