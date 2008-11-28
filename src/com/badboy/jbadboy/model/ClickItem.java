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
		
public class ClickItem extends Playable {

    
    
	
    private Integer upXPosition = 0;

    @Exportable
    public Integer getUpXPosition() {
        return this.upXPosition;
    }
    public void setUpXPosition(Integer upXPosition) {
        this.upXPosition = upXPosition;
    }

    private Integer clickType = 0;

    @Exportable
    public Integer getClickType() {
        return this.clickType;
    }
    public void setClickType(Integer clickType) {
        this.clickType = clickType;
    }

    private Integer yPosition = 0;

    @Exportable
    public Integer getYPosition() {
        return this.yPosition;
    }
    public void setYPosition(Integer yPosition) {
        this.yPosition = yPosition;
    }

    private Boolean waitForPageLoad = Boolean.FALSE;

    @Exportable
    public Boolean getWaitForPageLoad() {
        return this.waitForPageLoad;
    }
    public void setWaitForPageLoad(Boolean waitForPageLoad) {
        this.waitForPageLoad = waitForPageLoad;
    }

    private Integer upYPosition = 0;

    @Exportable
    public Integer getUpYPosition() {
        return this.upYPosition;
    }
    public void setUpYPosition(Integer upYPosition) {
        this.upYPosition = upYPosition;
    }

    private String windowName = "";

    @Exportable
    public String getWindowName() {
        return this.windowName;
    }
    public void setWindowName(String windowName) {
        this.windowName = windowName;
    }

    private Integer windowSizeX = 0;

    @Exportable
    public Integer getWindowSizeX() {
        return this.windowSizeX;
    }
    public void setWindowSizeX(Integer windowSizeX) {
        this.windowSizeX = windowSizeX;
    }

    private Boolean cascade = Boolean.FALSE;

    @Exportable
    public Boolean getCascade() {
        return this.cascade;
    }
    public void setCascade(Boolean cascade) {
        this.cascade = cascade;
    }

    private Boolean raiseToTop = Boolean.FALSE;

    @Exportable
    public Boolean getRaiseToTop() {
        return this.raiseToTop;
    }
    public void setRaiseToTop(Boolean raiseToTop) {
        this.raiseToTop = raiseToTop;
    }

    private Boolean waitForWindow = Boolean.FALSE;

    @Exportable
    public Boolean getWaitForWindow() {
        return this.waitForWindow;
    }
    public void setWaitForWindow(Boolean waitForWindow) {
        this.waitForWindow = waitForWindow;
    }

    private Integer xPosition = 0;

    @Exportable
    public Integer getXPosition() {
        return this.xPosition;
    }
    public void setXPosition(Integer xPosition) {
        this.xPosition = xPosition;
    }

    private Boolean dragMouse = Boolean.FALSE;

    @Exportable
    public Boolean getDragMouse() {
        return this.dragMouse;
    }
    public void setDragMouse(Boolean dragMouse) {
        this.dragMouse = dragMouse;
    }

    private Integer windowSizeY = 0;

    @Exportable
    public Integer getWindowSizeY() {
        return this.windowSizeY;
    }
    public void setWindowSizeY(Integer windowSizeY) {
        this.windowSizeY = windowSizeY;
    }

    private Boolean enableRestoreSize = Boolean.FALSE;

    @Exportable
    public Boolean getEnableRestoreSize() {
        return this.enableRestoreSize;
    }
    public void setEnableRestoreSize(Boolean enableRestoreSize) {
        this.enableRestoreSize = enableRestoreSize;
    }
}
