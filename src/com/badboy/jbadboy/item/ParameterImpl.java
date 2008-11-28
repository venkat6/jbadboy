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

import static com.badboy.jbadboy.util.Str.blank;
import groovy.util.Node;
import groovy.util.NodeList;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import com.badboy.jbadboy.model.Parameter;

/**
 * Adds some utiltiy behavior to the Parameter class loaded from
 * the script.
 * 
 * @author ssadedin
 */
public class ParameterImpl extends Parameter {
    
    boolean alwaysSendEquals = false;
    
    public ParameterImpl() {
        this.setMethod(HTTPMethod.DEFAULT);
    }
    
    @Override
    public void load(Node node) throws LoadException {
        super.load(node);
        NodeList list= (NodeList) node.get("sendEquals");
        for(Object obj : list) {
            if (obj instanceof Node) {
                Node n = (Node) obj;
                if(n != null) {
                    this.alwaysSendEquals = "true".equals(n.text());
                }
            }
        }
        
        // Values are stored URL encoded, so we need to "unencode" them
        try {
            this.setValue(URLDecoder.decode(this.getValue(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new LoadException("Unable to decode value",e);
        }
    }

    /**
     * Encodes the name and value of this parameter in a suitable
     * way to form part of a query string or url encoded form submission.
     * <p>
     * When {@link #alwaysSendEquals} is true will include the equals sign
     * even if value is blank, otherwise will omit it for blank values.
     * 
     * @param encoding      encoding to use when encoding string values.
     * @return encoded value in the form <name>=<value>
     * @throws ExecutionException
     */
    public String encode(String encoding) throws ExecutionException {
        try {
            
            String encName = URLEncoder.encode(context.eval(this.getName()),encoding);
            if(blank(this.getValue()))
                return alwaysSendEquals ? encName + "=" : encName;
            else
	            return encName + "=" + URLEncoder.encode(context.eval(this.getValue()),encoding);
        }
        catch (UnsupportedEncodingException e) {
            throw new ExecutionException("Unable to encode parameter " + this.toString() + ": ", e);
        }
    }

    public boolean isAlwaysSendEquals() {
        return alwaysSendEquals;
    }

    public void setAlwaysSendEquals(boolean alwaysSendEquals) {
        this.alwaysSendEquals = alwaysSendEquals;
    }

}
