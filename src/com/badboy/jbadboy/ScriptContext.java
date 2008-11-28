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

package com.badboy.jbadboy;

import java.util.HashMap;

import com.badboy.jbadboy.browser.Browser;
import com.badboy.jbadboy.item.ExecutionException;
import com.badboy.jbadboy.item.ScriptItem;
import com.badboy.jbadboy.model.Playable;

/**
 * The top level script context representing the state
 * of the running script environment, including variables,
 * script items, responses etc. 
 * 
 * @author ssadedin
 */
public class ScriptContext implements IBadboy {
	
    /**
     * The actual script
     */
	ScriptItem script;

	/**
	 * The top level browser being used as a playback target
	 */
	Browser browser;
	
	/**
	 * Absolute path from which script was loaded, if any
	 */
	String scriptFileName;
	
	/**
	 * Script engine hosting this ScriptContext
	 */
	ScriptEngine scriptEngine;
	
	/**
	 * All the variables known by the script
	 */
    HashMap<String, Variable> variables  = new HashMap<String, Variable>();
	
	public HashMap<String, Variable> getVariables() {
        return variables;
    }

    /**
	 * Determine and return the browser to which 
	 * the specified playable item should be played.
	 */
	public Browser getTargetBrowser(Playable p) {
	    // For now this is hard coded as the only 
	    // known browser window.  Down the track
	    // it will have to figure out the right browser
	    // window based on the targetWindow property
	    // of p
	    return this.browser;
	}

	public ScriptItem getScript() {
		return script;
	}

	public void setScript(ScriptItem script) {
		this.script = script;
	}
	
	public Variable getVariable(String name) {
	    return this.variables.get(name);
	}
	
	public void setVariable(String name, String value) {
	    Variable v = this.getVariable(name);
	    if(v == null) {
	        this.variables.put(name, new Variable(name,value));
	    }
	    else
	        v.setValue(value);
	}
	
	/**
	 * Substitutes variables from this context with their values
	 * and returns the result.
	 * <p>
	 * The result is recursively evaluated so that variable values
	 * containing other variable values are fully evaluated until
	 * the resulting expression contains no more variable references.
	 * @throws ExecutionException 
	 */
	public String eval(String value) throws ExecutionException {
	    StringBuilder result = new StringBuilder(value);
	    boolean finished = false;
	    int maxReplacements = 100;
	    int count = 0;
	    while(!finished) {
	        if(count++>maxReplacements)
	            throw new ExecutionException("Variable expression evalution overflow: recursive variable definition in expression " + value);
	        
	        finished = true;
	        boolean dollar = false;
	        int lastOpen = -1;
	        for(int i =0; i<result.length(); ++i) {
	            if(dollar && result.charAt(i)=='{') {
	                lastOpen = i;
	            }

	            // Set flag if we are in a dollar expression
	            dollar = (result.charAt(i) == '$');
	            if(result.charAt(i) == '}' && lastOpen!=-1) {
	                String var = result.substring(lastOpen+1,i);
	                Variable v = this.variables.get(var);
	                String varValue = v !=null ? v.getValue() : "";
	                result.replace(lastOpen-1, i+1, varValue);
	                finished = false;
	            }
	        }
	    }
	    return result.toString();
	}
	
    public Browser getBrowser() {
        return browser;
    }

    public void setBrowser(Browser browser) {
        this.browser = browser;
    }
    
	public String getScriptFileName() {
        return scriptFileName;
    }

    public void setScriptFileName(String scriptFileName) {
        this.scriptFileName = scriptFileName;
    }

    @Override
    public void info(String msg) {
        System.err.println("INFO: " + msg);
    }

    public ScriptEngine getScriptEngine() {
        return scriptEngine;
    }

    public void setScriptEngine(ScriptEngine scriptEngine) {
        this.scriptEngine = scriptEngine;
    }
}
