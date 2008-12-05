/**
 * Copyright 2008, Simon Sadedin, Badboy Software.
 * 
 * $Id: ScriptContextTest.groovy 1646 2008-11-07 00:48:28Z  $
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
import org.w3c.dom.html.HTMLElementimport com.badboy.jbadboy.browser.DOMElementFilterimport com.badboy.jbadboy.browser.Browserimport com.badboy.jbadboy.item.VisibleTextReferenceimport static org.junit.Assert.*import org.junit.Test
public class VisibleTextReferenceTest {

	def getBrowser(def elements) {        Browser b = [                      filterElements: { r, f ->                          elements.collect { e -> [ getAttribute: { a -> e[a] },                                                    getTagName: { e.tagName },                                                    getPrefix:  { e?.text} ] as HTMLElement}.findAll { de -> f.accept(de) }                      },                     getElementText: { e ->  e.getPrefix() },                     getBodyElement: { null }                   ] as Browser	}
		@Test
	final void testLink(){
	    VisibleTextReference r = new VisibleTextReference("Links", "Foo")	    def b = getBrowser([ [ tagName: "a", text: "Foo" ] ])	    	    b.filterElements([:] as HTMLElement, { true } as DOMElementFilter)	    	    		def results = r.findElement(b);		//assertTrue(results.size() == 1);	}	
	@Test	final void testLinkButtonDiscrimmination(){	    VisibleTextReference r = new VisibleTextReference("Links", "Foo")	    def b = getBrowser([ 	                         [ tagName: "a", text: "Foo" ], 	                         [tagName: "button",text:"Foo"] 	                       ])	                       		def results = r.findElement(b);		assertTrue(results.size() == 1);		assert results[0].tagName == "a"			    b = getBrowser([ 	                         [tagName: "button",text:"Foo"], 	                         [ tagName: "a", text: "Foo" ] 	                       ])				results = r.findElement(b);		assertTrue(results.size() == 1);		assert results[0].tagName == "a"			    r = new VisibleTextReference("Buttons", "Foo")		results = r.findElement(b);		assertTrue(results.size() == 1);		assert results[0].tagName == "button"	}	
	@Test
	final void testLinkNotFound() {	    VisibleTextReference r = new VisibleTextReference("Links", "Boo") 	    def b = getBrowser([ [ tagName: "a", text: "Foo" ] ])		def results = r.findElement(b);	    assertTrue(results.size() == 0);	    	    b = getBrowser([ [ tagName: "a", text: "Boo" ] ])		results = new VisibleTextReference("Links", "Foo").findElement(b);	    assertTrue(results.size() == 0);	    	    b = getBrowser([ [ tagName: "b", text: "Foo" ] ])		results = new VisibleTextReference("Links", "Foo").findElement(b);	    assertTrue(results.size() == 0);	}	
	@Test
	final void testButton() {
	    VisibleTextReference r = new VisibleTextReference("Buttons", "Foo")	    def b = getBrowser([ [ tagName: "button", text: "Foo" ] ])		def results = r.findElement(b);		assertTrue(results.size() == 1);	}		@Test	final void testSubmit() {        VisibleTextReference r = new VisibleTextReference("Buttons", "Foo")        def b = getBrowser([ [ tagName: "submit", value: "Foo" ] ])        def results = r.findElement(b);        assertTrue(results.size() == 1);        assert results[0].tagName == "submit"                 b = getBrowser([ [ tagName: "submit", value: "Boo" ] ])        results = r.findElement(b);        assert results.empty	}		@Test	final void testInput() {        VisibleTextReference r = new VisibleTextReference("Buttons", "Foo")        def b = getBrowser([ [ tagName: "input", type: "button", value: "Foo" ] ])        def results = r.findElement(b);        assertTrue(results.size() == 1);        assert results[0].tagName == "input"                 b = getBrowser([ [ tagName: "input", type: "button", value: "Boo" ] ])        results = r.findElement(b);        assert results.empty                b = getBrowser([ [ tagName: "input", type: "checkbox", value: "Foo" ] ])        results = r.findElement(b);        assert results.empty	}}