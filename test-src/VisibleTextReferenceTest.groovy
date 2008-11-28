import org.w3c.dom.html.HTMLElementimport com.badboy.jbadboy.browser.DOMElementFilterimport com.badboy.jbadboy.browser.Browserimport com.badboy.jbadboy.item.VisibleTextReferenceimport org.testng.annotations.*
import org.testng.TestNG
import org.testng.TestListenerAdapter
import static org.testng.AssertJUnit.*;
public class VisibleTextReferenceTest {

	/**
	* Main entry point to run <code>VisibleTextReferenceTest</code> as
	* simple Groovy class
	*/
	public static void main(String[] args){
		def testng = new TestNG()
		testng.setTestClasses(VisibleTextReferenceTest)
		testng.addListener(new TestListenerAdapter())
		testng.run()
	}		def getBrowser(def elements) {        Browser b = [                      filterElements: { r, f ->                          elements.collect { e -> [ getAttribute: { a -> e[a] },                                                    getTagName: { e.tagName },                                                    getPrefix:  { e?.text} ] as HTMLElement}.findAll { de -> f.accept(de) }                      },                     getElementText: { e ->  e.getPrefix() },                     getBodyElement: { null }                   ] as Browser	}
		@Test
	final void testLink(){
	    VisibleTextReference r = new VisibleTextReference("Links", "Foo")	    def b = getBrowser([ [ tagName: "a", text: "Foo" ] ])	    	    b.filterElements([:] as HTMLElement, { true } as DOMElementFilter)	    	    		def results = r.findElement(b);		//assertTrue(results.size() == 1);	}	
	@Test	final void testLinkButtonDiscrimmination(){	    VisibleTextReference r = new VisibleTextReference("Links", "Foo")	    def b = getBrowser([ 	                         [ tagName: "a", text: "Foo" ], 	                         [tagName: "button",text:"Foo"] 	                       ])	                       		def results = r.findElement(b);		assertTrue(results.size() == 1);		assert results[0].tagName == "a"			    b = getBrowser([ 	                         [tagName: "button",text:"Foo"], 	                         [ tagName: "a", text: "Foo" ] 	                       ])				results = r.findElement(b);		assertTrue(results.size() == 1);		assert results[0].tagName == "a"			    r = new VisibleTextReference("Buttons", "Foo")		results = r.findElement(b);		assertTrue(results.size() == 1);		assert results[0].tagName == "button"	}	
	@Test
	final void testLinkNotFound() {	    VisibleTextReference r = new VisibleTextReference("Links", "Boo") 	    def b = getBrowser([ [ tagName: "a", text: "Foo" ] ])		def results = r.findElement(b);	    assertTrue(results.size() == 0);	    	    b = getBrowser([ [ tagName: "a", text: "Boo" ] ])		results = new VisibleTextReference("Links", "Foo").findElement(b);	    assertTrue(results.size() == 0);	    	    b = getBrowser([ [ tagName: "b", text: "Foo" ] ])		results = new VisibleTextReference("Links", "Foo").findElement(b);	    assertTrue(results.size() == 0);	}	
	@Test
	final void testButton() {
	    VisibleTextReference r = new VisibleTextReference("Buttons", "Foo")	    def b = getBrowser([ [ tagName: "button", text: "Foo" ] ])		def results = r.findElement(b);		assertTrue(results.size() == 1);	}		@Test	final void testSubmit() {        VisibleTextReference r = new VisibleTextReference("Buttons", "Foo")        def b = getBrowser([ [ tagName: "submit", value: "Foo" ] ])        def results = r.findElement(b);        assertTrue(results.size() == 1);        assert results[0].tagName == "submit"                 b = getBrowser([ [ tagName: "submit", value: "Boo" ] ])        results = r.findElement(b);        assert results.empty	}		@Test	final void testInput() {        VisibleTextReference r = new VisibleTextReference("Buttons", "Foo")        def b = getBrowser([ [ tagName: "input", type: "button", value: "Foo" ] ])        def results = r.findElement(b);        assertTrue(results.size() == 1);        assert results[0].tagName == "input"                 b = getBrowser([ [ tagName: "input", type: "button", value: "Boo" ] ])        results = r.findElement(b);        assert results.empty                b = getBrowser([ [ tagName: "input", type: "checkbox", value: "Foo" ] ])        results = r.findElement(b);        assert results.empty	}}