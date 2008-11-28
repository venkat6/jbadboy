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
		
public class FormPopulator extends Playable {

    
    
	
    private String formIndex = "";

    @Exportable
    public String getFormIndex() {
        return this.formIndex;
    }
    public void setFormIndex(String formIndex) {
        this.formIndex = formIndex;
    }

    private Boolean submit = Boolean.FALSE;

    @Exportable
    public Boolean getSubmit() {
        return this.submit;
    }
    public void setSubmit(Boolean submit) {
        this.submit = submit;
    }

    private String formName = "";

    @Exportable
    public String getFormName() {
        return this.formName;
    }
    public void setFormName(String formName) {
        this.formName = formName;
    }

    private Integer actionType = 0;

    @Exportable
    public Integer getActionType() {
        return this.actionType;
    }
    public void setActionType(Integer actionType) {
        this.actionType = actionType;
    }

    private Boolean useRegex = Boolean.FALSE;

    @Exportable
    public Boolean getUseRegex() {
        return this.useRegex;
    }
    public void setUseRegex(Boolean useRegex) {
        this.useRegex = useRegex;
    }

    private Boolean ignoreHiddenFields = Boolean.FALSE;

    @Exportable
    public Boolean getIgnoreHiddenFields() {
        return this.ignoreHiddenFields;
    }
    public void setIgnoreHiddenFields(Boolean ignoreHiddenFields) {
        this.ignoreHiddenFields = ignoreHiddenFields;
    }
}
