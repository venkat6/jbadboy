/**
 * Copyright 2008, Simon Sadedin, Badboy Software.
 * 
 * $Id: Generator.groovy 1646 2008-11-07 00:48:28Z  $
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

import com.badboy.jbadboy.ScriptModel

/**
 * Scans a Badboy source directory and converts all
 * C++ classes contained within to Java equivalents.
 * <p>
 * For this to work, you must have the Badboy C++ code
 * available (not shipped by default with JBadboy).
 * 
 * @author ssadedin
 */
public class Generator {

	def converter = new CPP2Java()

	/**
	 * @param args
	 */
	public static void main(def args){
		new Generator().generate()
		new Generator().generate("c:/badboy/dev/trunk/bbcore")
	}
	
	void generate(srcPath="c:/badboy/dev/trunk") {
		converter.srcPath = srcPath;
		
		def dir = new File(srcPath)
		dir.eachFileMatch(~/.*\.cpp$/) {
		    if(it.name =~ /.*Summary.cpp/)
		        return;
		    
			if(it.text =~ /DECLPROP/) {
				println "Processing file ${it}"
				println " ==>  ${it} has properties"
				ScriptModel model = converter.parse(it.name.replaceAll(/\.cpp/,""))
				converter.write(model)
			}
		}
		
	}
}
