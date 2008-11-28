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
		
public class VariableSetter extends Playable {

    
    
	
    private Boolean parseVariableReferences = Boolean.FALSE;

    @Exportable
    public Boolean getParseVariableReferences() {
        return this.parseVariableReferences;
    }
    public void setParseVariableReferences(Boolean parseVariableReferences) {
        this.parseVariableReferences = parseVariableReferences;
    }

    private Boolean isCascading = Boolean.FALSE;

    @Exportable
    public Boolean getIsCascading() {
        return this.isCascading;
    }
    public void setIsCascading(Boolean isCascading) {
        this.isCascading = isCascading;
    }

    private String variableName = "";

    @Exportable
    public String getVariableName() {
        return this.variableName;
    }
    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    private Boolean noOverWrite = Boolean.FALSE;

    @Exportable
    public Boolean getNoOverWrite() {
        return this.noOverWrite;
    }
    public void setNoOverWrite(Boolean noOverWrite) {
        this.noOverWrite = noOverWrite;
    }

    private String targetFrame = "";

    @Exportable
    public String getTargetFrame() {
        return this.targetFrame;
    }
    public void setTargetFrame(String targetFrame) {
        this.targetFrame = targetFrame;
    }

    private String regex = "";

    @Exportable
    public String getRegex() {
        return this.regex;
    }
    public void setRegex(String regex) {
        this.regex = regex;
    }

    private String fileName = "";

    @Exportable
    public String getFileName() {
        return this.fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    private String valueList = "";

    @Exportable
    public String getValueList() {
        return this.valueList;
    }
    public void setValueList(String valueList) {
        this.valueList = valueList;
    }
}
