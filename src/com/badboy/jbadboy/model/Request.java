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
		
public class Request extends Playable {

    
    
	
    private String headers = "";

    @Exportable
    public String getHeaders() {
        return this.headers;
    }
    public void setHeaders(String headers) {
        this.headers = headers;
    }

    private String protocol = "";

    @Exportable
    public String getProtocol() {
        return this.protocol;
    }
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    private String host = "";

    @Exportable
    public String getHost() {
        return this.host;
    }
    public void setHost(String host) {
        this.host = host;
    }

    private Boolean bUseLenientSubrequestCount = Boolean.FALSE;

    @Exportable
    public Boolean getBUseLenientSubrequestCount() {
        return this.bUseLenientSubrequestCount;
    }
    public void setBUseLenientSubrequestCount(Boolean bUseLenientSubrequestCount) {
        this.bUseLenientSubrequestCount = bUseLenientSubrequestCount;
    }

    private String targetFrame = "";

    @Exportable
    public String getTargetFrame() {
        return this.targetFrame;
    }
    public void setTargetFrame(String targetFrame) {
        this.targetFrame = targetFrame;
    }

    private String formCharset = "";

    @Exportable
    public String getFormCharset() {
        return this.formCharset;
    }
    public void setFormCharset(String formCharset) {
        this.formCharset = formCharset;
    }

    private Integer defaultMethod = 0;

    @Exportable
    public Integer getDefaultMethod() {
        return this.defaultMethod;
    }
    public void setDefaultMethod(Integer defaultMethod) {
        this.defaultMethod = defaultMethod;
    }

    private String multipartBoundary = "";

    @Exportable
    public String getMultipartBoundary() {
        return this.multipartBoundary;
    }
    public void setMultipartBoundary(String multipartBoundary) {
        this.multipartBoundary = multipartBoundary;
    }

    private String resource = "";

    @Exportable
    public String getResource() {
        return this.resource;
    }
    public void setResource(String resource) {
        this.resource = resource;
    }

    private String label = "";

    @Exportable
    public String getLabel() {
        return this.label;
    }
    public void setLabel(String label) {
        this.label = label;
    }

    private Integer newWindowHeight = 0;

    @Exportable
    public Integer getNewWindowHeight() {
        return this.newWindowHeight;
    }
    public void setNewWindowHeight(Integer newWindowHeight) {
        this.newWindowHeight = newWindowHeight;
    }

    private Integer newWindowWidth = 0;

    @Exportable
    public Integer getNewWindowWidth() {
        return this.newWindowWidth;
    }
    public void setNewWindowWidth(Integer newWindowWidth) {
        this.newWindowWidth = newWindowWidth;
    }

    private String path = "";

    @Exportable
    public String getPath() {
        return this.path;
    }
    public void setPath(String path) {
        this.path = path;
    }

    private Integer executionMode = 0;

    @Exportable
    public Integer getExecutionMode() {
        return this.executionMode;
    }
    public void setExecutionMode(Integer executionMode) {
        this.executionMode = executionMode;
    }

    private Boolean waitForSubRequests = Boolean.FALSE;

    @Exportable
    public Boolean getWaitForSubRequests() {
        return this.waitForSubRequests;
    }
    public void setWaitForSubRequests(Boolean waitForSubRequests) {
        this.waitForSubRequests = waitForSubRequests;
    }
}
