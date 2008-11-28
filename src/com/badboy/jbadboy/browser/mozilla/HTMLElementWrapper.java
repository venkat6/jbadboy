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

import org.mozilla.interfaces.nsIDOMHTMLElement;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.TypeInfo;
import org.w3c.dom.UserDataHandler;
import org.w3c.dom.html.HTMLElement;

public class HTMLElementWrapper implements HTMLElement {
    
    nsIDOMHTMLElement obj;

    public HTMLElementWrapper(nsIDOMHTMLElement e) {
        super();
        this.obj = e;
    }
    
    nsIDOMHTMLElement getWrapped() {
        return obj;
    }
    
    public void setWrapped(nsIDOMHTMLElement wrapped) {
        this.obj = wrapped;
    }
    
    public String getClassName() {
       return obj.getClassName();
    }

    public String getDir() {
       return obj.getDir();
    }

    public String getId() {
       return obj.getId();
    }

    public String getLang() {
       return obj.getLang();
    }

    public String getTitle() {
       return obj.getTitle();
    }

    public String getAttribute(String arg0) {
       return obj.getAttribute(arg0);
    }


    public String getAttributeNS(String arg0, String arg1) {
       return obj.getAttributeNS(arg0,arg1);
    }


    public Attr getAttributeNode(String arg0) {
       return null;
    }


    public Attr getAttributeNodeNS(String arg0, String arg1) {
       return null;
    }


    public NodeList getElementsByTagName(String arg0) {
        return new NodeListWrapper(this.obj.getElementsByTagName(arg0));
    }

    public NodeList getElementsByTagNameNS(String arg0, String arg1) {
       return null;
    }

    public TypeInfo getSchemaTypeInfo() {
       return null;
    }

    public String getTagName() {
       return obj.getTagName();
    }


    public NamedNodeMap getAttributes() {
       return null;
    }


    public String getBaseURI() {
       return null;
    }


    public NodeList getChildNodes() {
       return null;
    }


    public Object getFeature(String arg0, String arg1) {
       return null;
    }


    public Node getFirstChild() {
       return null;
    }


    public Node getLastChild() {
       return null;
    }


    public String getLocalName() {
       return obj.getLocalName();
    }


    public String getNamespaceURI() {
       return obj.getNamespaceURI();
    }


    public Node getNextSibling() {
       return null;
    }

    public String getNodeName() {
       return obj.getNodeName();
    }


    public short getNodeType() {
       return 0;
    }


    public String getNodeValue() {
       return obj.getNodeValue();
    }


    public Document getOwnerDocument() {
       return null;
    }


    public Node getParentNode() {
       return null;
    }


    public String getPrefix() {
       return obj.getPrefix();
    }


    public Node getPreviousSibling() {
       return null;
    }


    public String getTextContent() {
       return null;
    }


    public Object getUserData(String arg0) {
       return null;
    }
    
    @Override
    public boolean hasAttribute(String arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean hasAttributeNS(String arg0, String arg1) throws DOMException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void removeAttribute(String arg0) throws DOMException {
        // TODO Auto-generated method stub

    }

    @Override
    public void removeAttributeNS(String arg0, String arg1) throws DOMException {
        // TODO Auto-generated method stub

    }

    @Override
    public Attr removeAttributeNode(Attr arg0) throws DOMException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setAttribute(String arg0, String arg1) throws DOMException {
        obj.setAttribute(arg0, arg1);
    }

    @Override
    public void setAttributeNS(String arg0, String arg1, String arg2)
            throws DOMException {
        // TODO Auto-generated method stub

    }

    @Override
    public Attr setAttributeNode(Attr arg0) throws DOMException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Attr setAttributeNodeNS(Attr arg0) throws DOMException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setIdAttribute(String arg0, boolean arg1) throws DOMException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setIdAttributeNS(String arg0, String arg1, boolean arg2)
            throws DOMException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setIdAttributeNode(Attr arg0, boolean arg1) throws DOMException {
        // TODO Auto-generated method stub

    }

    @Override
    public Node appendChild(Node arg0) throws DOMException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Node cloneNode(boolean arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public short compareDocumentPosition(Node arg0) throws DOMException {
        // TODO Auto-generated method stub
        return 0;
    }


    @Override
    public boolean hasAttributes() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean hasChildNodes() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Node insertBefore(Node arg0, Node arg1) throws DOMException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isDefaultNamespace(String arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isEqualNode(Node arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isSameNode(Node arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isSupported(String arg0, String arg1) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String lookupNamespaceURI(String arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String lookupPrefix(String arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void normalize() {
        // TODO Auto-generated method stub

    }

    @Override
    public Node removeChild(Node arg0) throws DOMException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Node replaceChild(Node arg0, Node arg1) throws DOMException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setNodeValue(String arg0) throws DOMException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setPrefix(String arg0) throws DOMException {

    }

    @Override
    public void setTextContent(String arg0) throws DOMException {
        
    }

    @Override
    public Object setUserData(String arg0, Object arg1, UserDataHandler arg2) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setClassName(String arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setDir(String arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setId(String arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setLang(String arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setTitle(String arg0) {
        // TODO Auto-generated method stub
        
    }
}
