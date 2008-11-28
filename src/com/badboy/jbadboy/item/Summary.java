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


package com.badboy.jbadboy.item;

public class Summary {
    
    int assertions;
    
    int played;
    
    int failed;
    
    int succeeded;
    
    long maxResponseTime;
    
    long minTimeMs;
    
    double averageResponseTime = 0.0;
    
    public void assertion() {
        ++this.assertions;
    }
    
    public void played(boolean success, long responseTime) {
        Summary s = new Summary();
        s.played = 1;
        if(success) 
            s.succeeded = 1;
        else
            s.failed = 1;
        
        s.averageResponseTime = responseTime;
        s.maxResponseTime = s.minTimeMs = responseTime;
        this.add(s);
    }
    
    public void add(Summary other) {
        if(other.played > 0 || this.played > 0)
	        this.averageResponseTime = (this.played * averageResponseTime + other.averageResponseTime*other.played) / (this.played + other.played);
        
        assert averageResponseTime != Double.NaN;
        
        if(this.played == 0 || other.minTimeMs < this.minTimeMs)
            this.minTimeMs = other .minTimeMs;
        
        assertions += other.assertions;
        played += other.played;
        failed += other.failed;
        succeeded += other.succeeded;
        if(other.maxResponseTime > this.maxResponseTime)
            this.maxResponseTime = other.maxResponseTime;
    }
    
    public void clear() {
        assertions = 0;
        played = 0;
        failed = 0;
        succeeded = 0;
        maxResponseTime = 0;
        minTimeMs = 0;
        averageResponseTime = 0.0;
    }

    public int getAssertions() {
        return assertions;
    }

    public int getPlayed() {
        return played;
    }

    public int getFailed() {
        return failed;
    }

    public int getSucceeded() {
        return succeeded;
    }

    public long getMaxResponseTime() {
        return maxResponseTime;
    }

    public long getMinTimeMs() {
        return minTimeMs;
    }

    public double getAverageResponseTime() {
        return averageResponseTime;
    }

    public void add(Summarizable summarizable) {
        add(summarizable.getSummary());
    }
}
