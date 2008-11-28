import com.badboy.jbadboy.item.HTTPMethodimport com.badboy.jbadboy.item.ParameterImplimport com.badboy.jbadboy.item.RequestImpl
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
	    RequestImpl r = new RequestImpl();	    r.addChild(new ParameterImpl(name:'foo', value: 'bar' ))	    r.addChild(new ParameterImpl(name:'tree', value: 'house' ))		assertTrue(r.getQueryString() == "foo=bar&tree=house");
	}		@Test	void testPostData(){
	    RequestImpl r = new RequestImpl();	    def p2 = new ParameterImpl(name:'tree', value: 'house' );	    r.addChild(new ParameterImpl(name:'foo', value: 'bar', method: HTTPMethod.POST ))	    r.addChild(p2)		assertEquals("tree=house",r.getQueryString());
		assertTrue(r.getPostData() == "foo=bar");				p2.method = HTTPMethod.POST;		assertEquals(null, r.getQueryString());
		assertTrue(r.getPostData() == "foo=bar&tree=house");
				p2.method = HTTPMethod.DEFAULT		r.defaultMethod = HTTPMethod.POST		assertEquals(null, r.getQueryString());
		assertEquals("foo=bar&tree=house",r.getPostData());
		
	}
	

}