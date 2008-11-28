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

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

import com.badboy.jbadboy.PlayListener;
import com.badboy.jbadboy.ScriptContext;

public class ScriptItem {
	
	protected ScriptContext context;
	
	long id;
	
	ScriptItem parent;
	
    List<ScriptItem> subItems = new ArrayList<ScriptItem>();
    
    Map<PlayEvent, Set<PlayListener> > listeners = new HashMap<PlayEvent, Set<PlayListener> >();
    
    public synchronized void subscribe(PlayEvent e,PlayListener l) {
        Set<PlayListener> evtListeners = this.listeners.get(e);
        if(evtListeners == null) {
            evtListeners = new HashSet<PlayListener>();
            listeners.put(e, evtListeners);
        }
        evtListeners.add(l);
    }
    
    public synchronized void unsubscribe(PlayListener l) {
        for(Set<PlayListener> ls : this.listeners.values()) {
            ls.remove(l);
        }
    }
    
    public synchronized void publish(PlayEvent event) throws ExecutionException {
        Set<PlayListener> evtListeners = this.listeners.get(event);
        if(evtListeners == null)
            return;
        for(PlayListener l : evtListeners) {
            l.onEvent(this, event);
        }
    }
    
    public void load(Node node) throws LoadException {
        
    }
    
    public void notifyModified() {
        if(this instanceof Summarizable) {
            Summarizable s = (Summarizable) this;
            Summary summary = s.getSummary();
            summary.clear();
            for(ScriptItem c : subItems) {
                if(c instanceof Summarizable) {
                    Summarizable cs = (Summarizable) c;
                    summary.add(cs);
                }
            }
        }
        if(this.parent != null) {
            this.parent.notifyModified();
        }
    }
	
	public void dump(OutputStream out, int indent) throws IOException {
		for(int i=0; i<indent; ++i) {
			out.write("  ".getBytes());
		}
		out.write((this.getClass().getName().replaceFirst("^.*\\.", "") + "(" + this.id + ")\n").getBytes());
		for(ScriptItem i : subItems) {
			i.dump(out, indent+4);
		}
	}
	
	public ScriptItem addChild(ScriptItem item) {
	    item.setParent(this);
	    item.setContext(context);
	    this.subItems.add(item);
	    if (this instanceof Summarizable && item instanceof Summarizable) {
            Summarizable me = (Summarizable) this;
            me.getSummary().add((Summarizable)item);
        }
	    this.notifyModified();
	    return this;
	}
	
	public List<ScriptItem> getSubItems() {
		return subItems;
	}

	public void setSubItems(List<ScriptItem> subItems) {
		this.subItems = subItems;
	}

	public void setId(long id) {
		this.id = id;
	}

	public ScriptContext getContext() {
		return context;
	}

	public void setContext(ScriptContext context) {
		this.context = context;
	}

	public ScriptItem getParent() {
        return parent;
    }

    public void setParent(ScriptItem parent) {
        this.parent = parent;
    }
    
    @Exportable
    public long getId() {
        return id;
    }

    public static void main(String[] args) {
        ScriptItem item = new ScriptItem();
        for (Method m : item.getClass().getMethods()) {
            System.out.println(m.getName() + ":");
            for(Annotation a : m.getDeclaredAnnotations()) {
              System.out.println("   " + a.getClass().getName());
            }
        }
    }
    
    public boolean isParent(ScriptItem parent) {
        for(ScriptItem p = this.getParent(); p != null; p = p.getParent()) 
            if(p == parent)
                return true;
        
        return false;
    }
}
