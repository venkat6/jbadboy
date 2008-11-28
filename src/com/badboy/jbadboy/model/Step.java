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
		
public class Step extends Playable {

    
    
	
    private Boolean incrementAutomatically = Boolean.FALSE;

    @Exportable
    public Boolean getIncrementAutomatically() {
        return this.incrementAutomatically;
    }
    public void setIncrementAutomatically(Boolean incrementAutomatically) {
        this.incrementAutomatically = incrementAutomatically;
    }

    private String repeatVariable = "";

    @Exportable
    public String getRepeatVariable() {
        return this.repeatVariable;
    }
    public void setRepeatVariable(String repeatVariable) {
        this.repeatVariable = repeatVariable;
    }

    private Boolean incrementAll = Boolean.FALSE;

    @Exportable
    public Boolean getIncrementAll() {
        return this.incrementAll;
    }
    public void setIncrementAll(Boolean incrementAll) {
        this.incrementAll = incrementAll;
    }

    private String name = "";

    @Exportable
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    private Integer repeatType = 0;

    @Exportable
    public Integer getRepeatType() {
        return this.repeatType;
    }
    public void setRepeatType(Integer repeatType) {
        this.repeatType = repeatType;
    }

    private Integer expanded = 0;

    @Exportable
    public Integer getExpanded() {
        return this.expanded;
    }
    public void setExpanded(Integer expanded) {
        this.expanded = expanded;
    }

    private Integer repetitions = 0;

    @Exportable
    public Integer getRepetitions() {
        return this.repetitions;
    }
    public void setRepetitions(Integer repetitions) {
        this.repetitions = repetitions;
    }
}
