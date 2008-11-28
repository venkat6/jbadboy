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
		
public class ThreadItem extends Playable {

    
    
	
    private Boolean startGradual = Boolean.FALSE;

    @Exportable
    public Boolean getStartGradual() {
        return this.startGradual;
    }
    public void setStartGradual(Boolean startGradual) {
        this.startGradual = startGradual;
    }

    private Boolean pauseWhileStarting = Boolean.FALSE;

    @Exportable
    public Boolean getPauseWhileStarting() {
        return this.pauseWhileStarting;
    }
    public void setPauseWhileStarting(Boolean pauseWhileStarting) {
        this.pauseWhileStarting = pauseWhileStarting;
    }

    private Boolean pauseWhileStopping = Boolean.FALSE;

    @Exportable
    public Boolean getPauseWhileStopping() {
        return this.pauseWhileStopping;
    }
    public void setPauseWhileStopping(Boolean pauseWhileStopping) {
        this.pauseWhileStopping = pauseWhileStopping;
    }

    private String engine = "";

    @Exportable
    public String getEngine() {
        return this.engine;
    }
    public void setEngine(String engine) {
        this.engine = engine;
    }

    private Boolean stopGradual = Boolean.FALSE;

    @Exportable
    public Boolean getStopGradual() {
        return this.stopGradual;
    }
    public void setStopGradual(Boolean stopGradual) {
        this.stopGradual = stopGradual;
    }

    private Integer maxThreads = 0;

    @Exportable
    public Integer getMaxThreads() {
        return this.maxThreads;
    }
    public void setMaxThreads(Integer maxThreads) {
        this.maxThreads = maxThreads;
    }

    private Integer maxIterations = 0;

    @Exportable
    public Integer getMaxIterations() {
        return this.maxIterations;
    }
    public void setMaxIterations(Integer maxIterations) {
        this.maxIterations = maxIterations;
    }

    private Integer durationTimeSeconds = 0;

    @Exportable
    public Integer getDurationTimeSeconds() {
        return this.durationTimeSeconds;
    }
    public void setDurationTimeSeconds(Integer durationTimeSeconds) {
        this.durationTimeSeconds = durationTimeSeconds;
    }

    private Integer startIncrement = 0;

    @Exportable
    public Integer getStartIncrement() {
        return this.startIncrement;
    }
    public void setStartIncrement(Integer startIncrement) {
        this.startIncrement = startIncrement;
    }

    private Integer stopIncrement = 0;

    @Exportable
    public Integer getStopIncrement() {
        return this.stopIncrement;
    }
    public void setStopIncrement(Integer stopIncrement) {
        this.stopIncrement = stopIncrement;
    }

    private Integer stopInteveralSeconds = 0;

    @Exportable
    public Integer getStopInteveralSeconds() {
        return this.stopInteveralSeconds;
    }
    public void setStopInteveralSeconds(Integer stopInteveralSeconds) {
        this.stopInteveralSeconds = stopInteveralSeconds;
    }

    private Integer startInteveralSeconds = 0;

    @Exportable
    public Integer getStartInteveralSeconds() {
        return this.startInteveralSeconds;
    }
    public void setStartInteveralSeconds(Integer startInteveralSeconds) {
        this.startInteveralSeconds = startInteveralSeconds;
    }

    private Integer durationType = 0;

    @Exportable
    public Integer getDurationType() {
        return this.durationType;
    }
    public void setDurationType(Integer durationType) {
        this.durationType = durationType;
    }
}
