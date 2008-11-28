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
		
public class SpiderItem extends Playable {

    
    
	
    private Boolean randomWalk = Boolean.FALSE;

    @Exportable
    public Boolean getRandomWalk() {
        return this.randomWalk;
    }
    public void setRandomWalk(Boolean randomWalk) {
        this.randomWalk = randomWalk;
    }

    private Boolean browseLinks = Boolean.FALSE;

    @Exportable
    public Boolean getBrowseLinks() {
        return this.browseLinks;
    }
    public void setBrowseLinks(Boolean browseLinks) {
        this.browseLinks = browseLinks;
    }

    private String targetFrame = "";

    @Exportable
    public String getTargetFrame() {
        return this.targetFrame;
    }
    public void setTargetFrame(String targetFrame) {
        this.targetFrame = targetFrame;
    }

    private Boolean clickButtons = Boolean.FALSE;

    @Exportable
    public Boolean getClickButtons() {
        return this.clickButtons;
    }
    public void setClickButtons(Boolean clickButtons) {
        this.clickButtons = clickButtons;
    }

    private Boolean loopAutomatically = Boolean.FALSE;

    @Exportable
    public Boolean getLoopAutomatically() {
        return this.loopAutomatically;
    }
    public void setLoopAutomatically(Boolean loopAutomatically) {
        this.loopAutomatically = loopAutomatically;
    }

    private Integer navigationMode = 0;

    @Exportable
    public Integer getNavigationMode() {
        return this.navigationMode;
    }
    public void setNavigationMode(Integer navigationMode) {
        this.navigationMode = navigationMode;
    }

    private Boolean captureResponses = Boolean.FALSE;

    @Exportable
    public Boolean getCaptureResponses() {
        return this.captureResponses;
    }
    public void setCaptureResponses(Boolean captureResponses) {
        this.captureResponses = captureResponses;
    }

    private Boolean randomPopulate = Boolean.FALSE;

    @Exportable
    public Boolean getRandomPopulate() {
        return this.randomPopulate;
    }
    public void setRandomPopulate(Boolean randomPopulate) {
        this.randomPopulate = randomPopulate;
    }

    private Integer targetType = 0;

    @Exportable
    public Integer getTargetType() {
        return this.targetType;
    }
    public void setTargetType(Integer targetType) {
        this.targetType = targetType;
    }
}
