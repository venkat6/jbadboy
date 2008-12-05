/**
 * Copyright 2008, Simon Sadedin, Badboy Software.
 * 
 * $Id: FormValueImplTest.groovy 1646 2008-11-07 00:48:28Z  $
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


package com.badboy.jbadboy.item

import com.badboy.jbadboy.browser.Browser
import com.badboy.jbadboy.browser.QueryInterface

import static org.junit.Assert.*
import org.junit.Test
 
import org.w3c.dom.html.HTMLInputElement
import org.w3c.dom.html.HTMLElement
import org.w3c.dom.html.HTMLFormElement
import org.w3c.dom.html.HTMLOptionElement
import org.w3c.dom.html.HTMLSelectElement
import org.w3c.dom.NodeList
import org.w3c.dom.html.HTMLTextAreaElement
import com.badboy.jbadboy.ScriptContext
import org.codehaus.groovy.runtime.StackTraceUtils

interface TestElement extends HTMLElement {
    def getData() 
}

public class FormValueImplTest {
    
   ScriptContext ctx = new ScriptContext();

   def getElement(def e) {
       def data = [ getAttribute: { a -> e[a] }, 
         getTagName: { e.tagName }, 
         getName: { e?.name }, 
         getPrefix:  { e?.text},
         getType: {e.getType()}
       ] 
       data['getData'] = { data }
       
       return data as TestElement       
   }
	
   def getBrowser(def elements) {
        Browser b = [ 
                     filterElements: { r,f -> 
                         elements.findAll { de -> f.accept(de) } 
                     },
                     getElementText: { e ->  e.getPrefix() },
                     getBodyElement: { null },
                     cast: { [ 
                               htmlElement: { e -> e instanceof HTMLElement ? e : null},
                               inputElement: { e -> e instanceof HTMLInputElement ? e : null},
                               selectElement: { e -> e instanceof HTMLSelectElement ? e : null}, 
                               optionElement: { e -> e instanceof HTMLOptionElement ? e : null},
                               textAreaElement: { e -> e instanceof HTMLTextAreaElement ? e : null} 
                             ] as QueryInterface }
                   ] as Browser
    }

	@Test
	final void testPopulateInput() {
	    def value = false;
	    def e = [ getType: {"text"}, setValue: { value=it } ] as HTMLInputElement;
	    def fv = [ searchElement: { b,f -> e } ] as FormValueImpl
        fv.context = ctx
        
	    def b = getBrowser([ e ]) as Browser;
	    def f = [:] as HTMLFormElement
	    fv.value = "success"
	    fv.populate(b, f)
	    assert value == "success"
	}
	
    @Test
    final void testPopulateCheckbox() {
        def value = false;
        def e = [ getName: { "testCheckBox" }, 
                  getValue: { "testValue"}, 
                  getType: { "checkbox" },
                  setChecked: { value=it }, 
                  setValue: { assert false : " should not set value"} 
                ] as HTMLInputElement;
        
        def fv = [ searchElement: { b,f -> e } ] as FormValueImpl
        fv.context = ctx
	    
        def b = getBrowser([ e ]) as Browser;
        def f = [:] as HTMLFormElement
        fv.name = "testCheckBox"
        fv.value = "testValue"
        fv.populate(b, f)
        assert value == true;
    }

    @Test
    final void testPopulateRadio() {
        
        def values = [false,true]
        
        def e1 = [ getName: { "testCheckBox" }, 
                  getValue: { "testValue"}, 
                  getType: { "radio" },
                  setChecked: { values[0]=it }, 
                  setValue: { assert false : " should not set value"} 
                ] as HTMLInputElement;
        def e2 = [ getName: { "testCheckBox" }, 
                  getValue: { "testValue"}, 
                  getType: { "radio" },
                  setChecked: { values[1]=it }, 
                  setValue: { assert false : " should not set value"} 
                ] as HTMLInputElement;
         
        def fv = [ searchElement: { b,f -> e1 } ] as FormValueImpl
        fv.context = ctx
        
        def b = getBrowser([ e1, e2 ]) as Browser;
        def f = [:] as HTMLFormElement
        fv.name = "testCheckBox"
        fv.value = "testValue"
        fv.populate(b, f)
        
        // Checked radios should have switched
        assert values == [true,false]
        
        fv = [ searchElement: { b2,f2 -> e2 } ] as FormValueImpl
        fv.context = ctx
        fv.name = "testCheckBox"
        fv.value = "testValue"
        fv.populate(b, f)
        assert values == [false,true]
    }
    
    
    @Test
    final void testPopulateSelect() {
        def selectedIndex = -1
        
        def options = [
                       [ 
                          getValue: {"v1"}, 
                          getPrefix: {"Option 1"},
                          setChecked: {assert false : " should not set checked"}, 
                          setValue: { assert false : " should not set value"} 
                       ] as HTMLOptionElement,
                       [ 
                          getValue: {"v2"}, 
                          getPrefix: {"Option 2"},
                          setChecked: {assert false : " should not set checked"}, 
                          setValue: { assert false : " should not set value"} 
                       ] as HTMLOptionElement
                     ];
        
        def sel = [ getName: { "testSelect" }, 
                  setSelectedIndex: {selectedIndex = it}, 
                  setValue: { assert false : " should not set value"},
                  getElementsByTagName: { [ getLength:{2}, item: { options[it]} ] as NodeList }
                ] as HTMLSelectElement;
        
        def fv = [ searchElement: { b,f -> sel } ] as FormValueImpl
        fv.context = ctx
        
        def b = getBrowser([ sel, options[0], options[1] ]) as Browser;
        def f = [:] as HTMLFormElement
        fv.name = "testSelect"
        fv.value = "v1"
        fv.populate(b, f)
        
        assert selectedIndex == 0
        
        fv.value = "v2"
        fv.populate(b, f)
        assert selectedIndex == 1
        
        fv.matchVisibleText = true
        fv.value = "Option 1"
        fv.populate(b, f)
        assert selectedIndex == 0
        
        fv.value = "Option 2"
        fv.populate(b, f)
        assert selectedIndex == 1
    }    
    
    @Test
    final void testPopulateTextArea() {
        def value = false;
        def e = [ setValue: { value=it } ] as HTMLTextAreaElement;
        def fv = [ searchElement: { b,f -> e } ] as FormValueImpl
        fv.context = ctx
        
        def b = getBrowser([ e ]) as Browser;
        def f = [:] as HTMLFormElement
        fv.value = "success"
        fv.populate(b, f)
        assert value == "success"
    }
    
    @Test
    final void testSearchTextArea() {
        def e = [ getName: { 'foo'} ] as HTMLTextAreaElement
        def e2 = [ getName: { 'foo'} ] as HTMLTextAreaElement
        testSearch(e,e2)
    }
    
     @Test
    final void testSearchSelect() {
        def e = [ getName: { 'foo'} ] as HTMLSelectElement
        def e2 = [ getName: { 'foo'} ] as HTMLSelectElement
        testSearch(e,e2)
    }
    
    void testSearch(def e, def e2) {
        def b = getBrowser([ e, e2 ]) as Browser;
        def f = [:] as HTMLFormElement
        def fv = new FormValueImpl(name: 'foo', value:'bar', context: ctx)
        assert fv.searchElement(b, f) == e
        
        fv.name = 'fubar'
        assert fv.searchElement(b, f) == null
        
        // Test indexed
        assert e != e2
        b = getBrowser([ e, e2 ]) as Browser;
        fv.name = 'foo'
        fv.index = 1
        assert fv.searchElement(b, f) == e2
        
        // Index 2 should not be found
        fv.index = 2
        assert fv.searchElement(b, f) == null
    }
    
    
}