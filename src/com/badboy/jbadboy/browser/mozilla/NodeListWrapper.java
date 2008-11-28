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


package com.badboy.jbadboy.browser.mozilla;

import org.mozilla.dom.NodeFactory;
import org.mozilla.interfaces.nsIDOMNode;
import org.mozilla.interfaces.nsIDOMNodeList;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class NodeListWrapper implements NodeList {
    
    nsIDOMNodeList obj;

    public NodeListWrapper(nsIDOMNodeList obj) {
        super();
        this.obj = obj;
    }

    @Override
    public int getLength() {
        return (int) obj.getLength();
    }

    @Override
    public Node item(int index) {
        nsIDOMNode n = this.obj.item(index);
        return NodeFactory.getNodeInstance(n);
    }
}
