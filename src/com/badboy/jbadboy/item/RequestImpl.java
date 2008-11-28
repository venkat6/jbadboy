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

import static java.util.Arrays.asList;

import groovy.util.Node;
import groovy.util.NodeList;

import java.util.List;

import com.badboy.jbadboy.model.Request;
import com.badboy.jbadboy.util.Str;

public class RequestImpl extends Request {
    
    private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
            .getLog(RequestImpl.class);
    
    private boolean active = false;

    public RequestImpl() {
        this.setDefaultMethod(HTTPMethod.GET);
    }
    
	@Override
	public void play() throws ExecutionException {
	    
	    if(!this.active)
	        return;
	    
		log.info("Playing request " + this.getId());
		
		String url = 
        	context.eval(this.getProtocol()) + "://" + context.eval(this.getHost()) + context.eval(this.getPath());
		
		// Generate parameters
		String params = this.getQueryString();
		if(!Str.blank(params)) 
		    url += "?" + params;
		
		String postData = this.getPostData();
		
        long beginTimeMs = System.currentTimeMillis();
        NavigationWaiter n = new NavigationWaiter(this.context.getBrowser());
		this.context.getBrowser().navigate(url, this.getTargetFrame(), postData);
        n.waitForNavigation();
 		ResponseImpl response = new ResponseImpl(true,System.currentTimeMillis() - beginTimeMs); 
		this.addChild(response);
	}

    public String getQueryString() throws ExecutionException {
        if(this.getDefaultMethod() == 1)
	        return getParameterString(asList(HTTPMethod.DEFAULT,HTTPMethod.GET));
        else
	        return getParameterString(asList(HTTPMethod.GET));
    }
    
    public String getPostData() throws ExecutionException {
        if(this.getDefaultMethod() == 2)
	        return getParameterString(asList(HTTPMethod.DEFAULT,HTTPMethod.POST));
        else
	        return getParameterString(asList(HTTPMethod.POST));
    }

    private String getParameterString(List<Integer> methods) throws ExecutionException {
        StringBuilder result = null;
        for(ScriptItem c : this.getSubItems()) {
            if(c instanceof ParameterImpl) {
                ParameterImpl p = (ParameterImpl) c;
                if(methods.contains(p.getMethod())) {
                    if(result == null)
                        result = new StringBuilder();
                    else
                        result.append("&");
                    
                    // TODO: technically should use charset from form - 
                    // however the default is something weird and windows specific
                    // that will make Java puke - need to map to something sensible.
	                result.append(p.encode("UTF-8"));
                }
            }
        }
        return result != null ? result.toString() : null;
    }



    @Override
    public void load(Node node) throws LoadException {
        super.load(node);
        
        NodeList n = (NodeList) node.get("isActive");
        if(n != null && !n.isEmpty()) {
            this.setActive("1".equals(((Node)n.get(0)).text().trim()));
        }
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
