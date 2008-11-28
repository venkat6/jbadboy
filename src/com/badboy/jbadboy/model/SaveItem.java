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
		
public class SaveItem extends Playable {

    
    
	
    private String variableExpression = "";

    @Exportable
    public String getVariableExpression() {
        return this.variableExpression;
    }
    public void setVariableExpression(String variableExpression) {
        this.variableExpression = variableExpression;
    }

    private Boolean sendAsAttachment = Boolean.FALSE;

    @Exportable
    public Boolean getSendAsAttachment() {
        return this.sendAsAttachment;
    }
    public void setSendAsAttachment(Boolean sendAsAttachment) {
        this.sendAsAttachment = sendAsAttachment;
    }

    private Boolean exportImages = Boolean.FALSE;

    @Exportable
    public Boolean getExportImages() {
        return this.exportImages;
    }
    public void setExportImages(Boolean exportImages) {
        this.exportImages = exportImages;
    }

    private String emailCC = "";

    @Exportable
    public String getEmailCC() {
        return this.emailCC;
    }
    public void setEmailCC(String emailCC) {
        this.emailCC = emailCC;
    }

    private Integer sendType = 0;

    @Exportable
    public Integer getSendType() {
        return this.sendType;
    }
    public void setSendType(Integer sendType) {
        this.sendType = sendType;
    }

    private String emailSubject = "";

    @Exportable
    public String getEmailSubject() {
        return this.emailSubject;
    }
    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
    }

    private String styleSheet = "";

    @Exportable
    public String getStyleSheet() {
        return this.styleSheet;
    }
    public void setStyleSheet(String styleSheet) {
        this.styleSheet = styleSheet;
    }

    private String frameName = "";

    @Exportable
    public String getFrameName() {
        return this.frameName;
    }
    public void setFrameName(String frameName) {
        this.frameName = frameName;
    }

    private String fileName = "";

    @Exportable
    public String getFileName() {
        return this.fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    private Boolean showSavedFile = Boolean.FALSE;

    @Exportable
    public Boolean getShowSavedFile() {
        return this.showSavedFile;
    }
    public void setShowSavedFile(Boolean showSavedFile) {
        this.showSavedFile = showSavedFile;
    }

    private String emailTo = "";

    @Exportable
    public String getEmailTo() {
        return this.emailTo;
    }
    public void setEmailTo(String emailTo) {
        this.emailTo = emailTo;
    }

    private Boolean appendMode = Boolean.FALSE;

    @Exportable
    public Boolean getAppendMode() {
        return this.appendMode;
    }
    public void setAppendMode(Boolean appendMode) {
        this.appendMode = appendMode;
    }

    private String writerClass = "";

    @Exportable
    public String getWriterClass() {
        return this.writerClass;
    }
    public void setWriterClass(String writerClass) {
        this.writerClass = writerClass;
    }

    private Boolean includeResponseContent = Boolean.FALSE;

    @Exportable
    public Boolean getIncludeResponseContent() {
        return this.includeResponseContent;
    }
    public void setIncludeResponseContent(Boolean includeResponseContent) {
        this.includeResponseContent = includeResponseContent;
    }
}
