import com.badboy.jbadboy.ScriptModelimport com.badboy.jbadboy.model.generator.CPP2Javaimport org.testng.annotations.*
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
	}		@Test	void testFindType() {		CPP2Java g = new CPP2Java()		assertEquals("java.lang.Integer",g.findType("ClickItem","xPosition").class.name)	}
	
	@Test
	final void testParseStringProperty(){
		println "Testing parsing"
		CPP2Java generator = new CPP2Java()
		ScriptModel model = generator.parse("ClickItem")
		assertTrue(model.properties['windowName'].class.name == 'java.lang.String')
		assertTrue(model.properties['xPosition'].class.name == 'java.lang.Integer')
	}		@Test	void testParentClassName() {		CPP2Java generator = new CPP2Java()				// Test a playable item is detected as playable
		ScriptModel model = generator.parse("ClickItem")		assertEquals("Playable",model.parentClassName)				// Test that a non-playable item is not returned as playable		model = generator.parse("Parameter");		assertEquals("ScriptItem",model.parentClassName)	}		@Test	void testWriteClass() {		CPP2Java generator = new CPP2Java(outputPath:"temp-src",outputPackage:"")		new File(generator.outputPath).mkdirs()
		File f = new File("temp-src/TestClass.java")		if(f.exists())			f.delete()					ScriptModel model = new ScriptModel(className:'TestClass');		model.testString = "";		model.testInt = new Integer(5)		generator.write(model)		assertTrue( new File("temp-src/TestClass.java").exists())
	}
}