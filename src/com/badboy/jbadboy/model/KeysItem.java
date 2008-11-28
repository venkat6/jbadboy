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
		
public class KeysItem extends Playable {

    
    
	
    private String keys = "";

    @Exportable
    public String getKeys() {
        return this.keys;
    }
    public void setKeys(String keys) {
        this.keys = keys;
    }

    private Boolean runInBackground = Boolean.FALSE;

    @Exportable
    public Boolean getRunInBackground() {
        return this.runInBackground;
    }
    public void setRunInBackground(Boolean runInBackground) {
        this.runInBackground = runInBackground;
    }

    private String windowCaption = "";

    @Exportable
    public String getWindowCaption() {
        return this.windowCaption;
    }
    public void setWindowCaption(String windowCaption) {
        this.windowCaption = windowCaption;
    }

    private Integer delaySeconds = 0;

    @Exportable
    public Integer getDelaySeconds() {
        return this.delaySeconds;
    }
    public void setDelaySeconds(Integer delaySeconds) {
        this.delaySeconds = delaySeconds;
    }
}
