import com.badboy.jbadboy.item.HTTPMethod
import org.testng.annotations.*
import org.testng.TestNG
import org.testng.TestListenerAdapter
import static org.testng.AssertJUnit.*;

public class RequestTest {

	/**
	* Main entry point to run <code>RequestTest</code> as
	* simple Groovy class
	*/
	public static void main(String[] args){
		def testng = new TestNG()
		testng.setTestClasses(RequestTest)
		testng.addListener(new TestListenerAdapter())
		testng.run()
	}
	
	
	/**
  	 *
	 */
	@Test
	void testQueryString(){
	    RequestImpl r = new RequestImpl();
	}
	    RequestImpl r = new RequestImpl();
		assertTrue(r.getPostData() == "foo=bar");
		assertTrue(r.getPostData() == "foo=bar&tree=house");
		
		assertEquals("foo=bar&tree=house",r.getPostData());
		
	}
	

}