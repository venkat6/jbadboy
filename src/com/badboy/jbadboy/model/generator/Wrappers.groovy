/**
 * Copyright 2008, Simon Sadedin, Badboy Software.
 * 
 * $Id: Wrappers.groovy 1646 2008-11-07 00:48:28Z  $
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


/**
 * 
 */
package com.badboy.jbadboy.model.generator


/**
 * Generates wrappers for Mozilla specific apis with W3C DOM APIs.
 * <p>
 * This requires the MozSwing source.  It is a one-off hack to 
 * quickly generate wrappers, not for general use.
 * 
 * @author ssadedin
 */
public class Wrappers {
     
    public static void main(def argv){
        
      def text = new File("src/com/badboy/jbadboy/browser/mozilla/HTMLTextAreaElementWrapper.java").text
      
      // Find all public getters
      (text =~ /([A-Za-z]*) get([A-Z][A-Za-z]*)\((.*)\)/).each { full, type, name, args ->
      
         def argNoTypes = args.split(",").collect { it.replaceAll("^.* ","") }.join(",")
      
         println """
  public $type get$name($args) {
     return obj.get$name($argNoTypes);
  }
"""
         if(args.split(",").length == 1) {
             println """
  public void set$name($type arg) {
     this.obj.set$name(arg);
  }
"""
         }
      }

      (text =~ /([A-Za-z]*) ([a-z]*)\((.*)\)/).each { full, type, name, args ->
         def argNoTypes = args.split(",").collect { it.replaceAll("^.* ","") }.join(",")
         if(type == "void") {
             println """
  public void $name($args) {
     this.obj.$name($argNoTypes);                 
  }
"""
         }

      }
    }
}
