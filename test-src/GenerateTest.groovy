import com.badboy.jbadboy.ScriptModel
import org.testng.TestNG
import org.testng.TestListenerAdapter
import static org.testng.AssertJUnit.*;

public class GenerateTest {

	/**
	* Main entry point to run <code>GenerateTest</code> as
	* simple Groovy class
	*/
	public static void main(String[] args){
		def testng = new TestNG()
		testng.setTestClasses(GenerateTest)
		testng.addListener(new TestListenerAdapter())
		testng.run()
	}
	
	@Test
	final void testParseStringProperty(){
		println "Testing parsing"
		CPP2Java generator = new CPP2Java()
		ScriptModel model = generator.parse("ClickItem")
		assertTrue(model.properties['windowName'].class.name == 'java.lang.String')
		assertTrue(model.properties['xPosition'].class.name == 'java.lang.Integer')
	}
		ScriptModel model = generator.parse("ClickItem")
		File f = new File("temp-src/TestClass.java")
	}
}