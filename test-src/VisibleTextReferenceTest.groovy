import org.w3c.dom.html.HTMLElement
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
	}
	
	final void testLink(){
	    VisibleTextReference r = new VisibleTextReference("Links", "Foo")
	@Test
	@Test
	final void testLinkNotFound() {
	@Test
	final void testButton() {
	    VisibleTextReference r = new VisibleTextReference("Buttons", "Foo")