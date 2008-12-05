import com.badboy.jbadboy.ScriptContextimport org.junit.Beforeimport com.badboy.jbadboy.item.HTTPMethodimport com.badboy.jbadboy.item.ParameterImplimport com.badboy.jbadboy.item.RequestImpl
import static org.junit.Assert.*import org.junit.Test
public class RequestTest {		ScriptContext ctx		@Before	void setup() {		ctx = new ScriptContext()	}

	@Test
	void testQueryString(){
	    RequestImpl r = new RequestImpl(context: ctx);	    r.addChild(new ParameterImpl(name:'foo', value: 'bar' ))	    r.addChild(new ParameterImpl(name:'tree', value: 'house' ))		assertTrue(r.getQueryString() == "foo=bar&tree=house");
	}		@Test	void testPostData(){
	    RequestImpl r = new RequestImpl(context: ctx);	    def p2 = new ParameterImpl(name:'tree', value: 'house' );	    r.addChild(new ParameterImpl(name:'foo', value: 'bar', method: HTTPMethod.POST ))	    r.addChild(p2)		assertEquals("tree=house",r.getQueryString());
		assertTrue(r.getPostData() == "foo=bar");				p2.method = HTTPMethod.POST;		assertEquals(null, r.getQueryString());
		assertTrue(r.getPostData() == "foo=bar&tree=house");
				p2.method = HTTPMethod.DEFAULT		r.defaultMethod = HTTPMethod.POST		assertEquals(null, r.getQueryString());
		assertEquals("foo=bar&tree=house",r.getPostData());
		
	}
	

}