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

import com.badboy.jbadboy.item.ExecutionException;
import com.badboy.jbadboy.item.PlayEvent;
import com.badboy.jbadboy.item.ScriptItem;
import com.badboy.jbadboy.model.Playable;
import com.badboy.jbadboy.util.Algorithm.Filter;

public class ScriptEngine {
    
    private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(ScriptEngine.class);
    
    enum PlayDisposition {
        BEFORE,
        DURING,
        AFTER
    }
    
    enum PlayStatus {
        STOPPED,
        PLAYING,
        PAUSED,
        FINISHED
    }
    
    public static Filter<ScriptItem> isPlayable = new Filter<ScriptItem>() {
        @Override
        public boolean $(ScriptItem t) {
            if(t != null && t instanceof Playable) {
                Playable p = (Playable) t;
                return !p.getDisabled();
            }
            return false;
        }
    };
	
	ScriptIterator playPosition;
	
	ScriptContext scriptContext;
	
    PlayDisposition disposition = PlayDisposition.BEFORE;
	
	PlayStatus playStatus = PlayStatus.STOPPED;
	
	public void play() {
	    if(playPosition == null) {
	        playPosition = new ScriptIterator(scriptContext.getScript());
	        playPosition.forward();
	    }
	    
	    if(disposition != PlayDisposition.BEFORE)
		    next();
	    
	    this.begin();
	}
	
	private void begin() {
	    this.playStatus = PlayStatus.PLAYING;
	    
	    // In case play position is sitting on non-playable item
	    if(!(this.playPosition.get() instanceof Playable))
	        this.next();
	    
	    playLoop();
    }

    private Playable playLoop() {
        Playable pos = (Playable) this.playPosition.get();
        while(playPosition != null && this.playStatus == PlayStatus.PLAYING) {
	        disposition = PlayDisposition.DURING;
	        try {
                pos.play();
            } catch (ExecutionException e) {
                log.error("Item " + pos.toString() + " Failed",e);
            } 
	        disposition = PlayDisposition.AFTER;
	        
	        Playable newPlayPosition = this.next();
	        
	        for(ScriptItem p = pos.getParent(); p != null; p = p.getParent()) {
	            if(newPlayPosition == null  || !newPlayPosition.isParent(p))  {
	                try {
                        p.publish(PlayEvent.LEAVE);
                    } 
	                catch (ExecutionException e) {
                        log.error("Failed publishing event",e);
                    }
	            }
	        }
	        
	        ScriptItem s = playPosition.get();
	        if(s instanceof Playable) {
	            newPlayPosition = (Playable) s;
	        }
	        else
	            newPlayPosition = this.next();
	            
	        
	        if(newPlayPosition == null)
	            break;
	        
	        pos = newPlayPosition;
	    }
        return pos;
    }

    public Playable next() {
        return (Playable)playPosition.next(isPlayable);
	}
    
	public ScriptContext getScriptContext() {
        return scriptContext;
    }

    public void setScriptContext(ScriptContext scriptContext) {
        this.scriptContext = scriptContext;
        this.playPosition = new ScriptIterator(scriptContext.getScript());
        playPosition.forward();
        scriptContext.setScriptEngine(this);
    }

    public ScriptIterator getPlayPosition() {
        return playPosition;
    }

    
}
