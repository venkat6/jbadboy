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

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.html.HTMLElement;

import com.badboy.jbadboy.model.Navigation;
import com.badboy.jbadboy.util.Str;

public class NavigationImpl extends Navigation {
    
    List<NavigationReference> references = new ArrayList<NavigationReference>();
    
    public NavigationImpl() {
        this.setSelectedReference(0);
        this.setTimeoutEnabled(Boolean.FALSE);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public void load(Node node) throws LoadException {
        NodeList n = (NodeList) node.get("references");
        if(!n.isEmpty()) {
            parseReferences((Node) n.get(0));
        }
    }

    private void parseReferences(Node refs) throws LoadException {
        for(Node c : (List<Node>)refs.children()) {
            if("count".equals(c.name()))
                continue;
            
            String filter = ((Node)((NodeList)c.get("filter")).get(0)).text();
            String value = ((Node)((NodeList)c.get("value")).get(0)).text();
            
            NavigationReference ref = null;
            if("NameOrIdReference".equals(c.name())) {
                this.references.add(ref = new NameOrIdReference(filter,value));
            }
            else
            if("VisibleTextReference".equals(c.name())) {
                this.references.add(ref = new VisibleTextReference(filter,value));
            }
            else
            if("JavascriptReference".equals(c.name())) {
                this.references.add(ref = new JavascriptReference(filter,value));
            }
            else 
                throw new LoadException("Unknown reference type " + c.name() + " encountered");
            
            // Set the index
            NodeList indexNode = ((NodeList)c.get("index"));
            String index = null;
            if(!indexNode.isEmpty())
                index = ((Node)((NodeList)c.get("index")).get(0)).text();
            
            if(Str.blank(index)) {
                ref.setIndex(0);
            }
            else {
                ref.setIndex(Integer.parseInt(index));
            }
        }
    }

    @Override
    public void play() {
        NavigationReference reference = this.references.get(this.getSelectedReference());
        // NavigationReference reference = this.references.get(1);
        List<HTMLElement> element = reference.findElement(this.context.getBrowser());
        if(element != null && element.size() > reference.getIndex()) {
            
            NavigationWaiter n = new NavigationWaiter(this.context.getBrowser());
            
            this.context.getBrowser().click(element.get(reference.getIndex()));
            
            n.waitForNavigation();
        }
        else
            System.out.println("Unable to locate element for Navigation " + this.getId());
    }
}
