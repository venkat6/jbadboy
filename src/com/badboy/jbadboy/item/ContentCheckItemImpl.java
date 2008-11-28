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

import groovy.util.Node;
import groovy.util.NodeList;

import java.util.regex.Pattern;

import com.badboy.jbadboy.browser.Browser;
import com.badboy.jbadboy.model.ContentCheckItem;
import com.badboy.jbadboy.model.Playable;

public class ContentCheckItemImpl extends ContentCheckItem implements Check {
    
    ContentCheckType checkType = ContentCheckType.CONTAINS;
    
    ContentCheckMode mode = ContentCheckMode.SEARCH_ALL;

    @Override
    public void load(Node node) throws LoadException {
        super.load(node);
        NodeList list= (NodeList) node.get("type");
        for(Object obj : list) {
            if (obj instanceof Node) {
                Node n = (Node) obj;
                if(n != null) {
                    this.checkType = "0".equals(n.text().trim()) ? ContentCheckType.CONTAINS : ContentCheckType.NOT_CONTAINS;
                }
            }
        }
        list = (NodeList) node.get("mode");
        for(Object obj : list) {
            if (obj instanceof Node) {
                Node n = (Node) obj;
                if(n != null) {
                    this.mode = "0".equals(n.text().trim()) ? ContentCheckMode.SEARCH_ALL : ContentCheckMode.SEARCH_TOP;
                }
            }
        }
    }
    
    public String getModeExport() {
        switch(mode) {
        case SEARCH_ALL:
            return "0";
        case SEARCH_TOP:
            return "1";
        default:
            throw new RuntimeException("Invalid value for mode property");
        }
    }
    
    public String getTypeExport() {
        switch(checkType) {
	        case CONTAINS: 
	            return "0";
	        case NOT_CONTAINS:
	            return "1";
	        default:
	            throw new RuntimeException("Invalid value for type property");
        }
    }

    @Override
    public CheckResult check(Playable playable) throws ExecutionException {
        Browser b = this.context.getTargetBrowser(playable);
        String html = b.getInnerHTML(b.getBodyElement());
        
        // Look for the text
        String pattern = this.context.eval(this.getPattern());
        boolean found = false;
        
        if(getBUseRegex()) {
	        found = Pattern.compile(pattern).matcher(html).find();
        }
        else
	        found = html.indexOf(pattern) >= 0;
        
        if(checkType == ContentCheckType.CONTAINS) {
            if(found) 
		        return new CheckResult(CheckExecutionStatus.SUCCEEDED, "Expected text " + pattern + " found");
            else
		        return new CheckResult(CheckExecutionStatus.FAILED, "Text " + pattern + " not found on page");
        }
        else {
            if(found) 
		        return new CheckResult(CheckExecutionStatus.FAILED, "Text " + pattern + " found on page");
            else
		        return new CheckResult(CheckExecutionStatus.SUCCEEDED, "Text " + pattern + " not found");
        }
    }

    @Exportable
    public ContentCheckType getCheckType() {
        return checkType;
    }

    public void setCheckType(ContentCheckType type) {
        this.checkType = type;
    }

    @Exportable
    public ContentCheckMode getMode() {
        return mode;
    }

    public void setMode(ContentCheckMode mode) {
        this.mode = mode;
    }
}
