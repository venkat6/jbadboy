/**
 * 
 */
import com.badboy.jbadboy.item.ScriptItem;
import com.badboy.jbadboy.ScriptIterator;

import static org.junit.Assert.*
import org.junit.Test

public class ScriptIteratorTest {

	@Test
	final void testSingleItem(){
	    def item = new ScriptItem();
	    def iter = new ScriptIterator(item);
		assertTrue(iter.forward() == item);
		assertTrue(iter.forward() == null);
	}
	
	@Test
	void testParentChild() {
	    def parent = new ScriptItem();
	    def child = new ScriptItem();
	    parent.addChild(child)
	    def iter = new ScriptIterator(parent);
	    
		assertTrue(iter.forward() == parent);
		assertTrue(iter.forward() == child);
		assertTrue(iter.forward() == null);
	}

	   @Test
	    void testAscendOnce() {
	        def parent = new ScriptItem()
	        def gc = new ScriptItem()
	        def children = [ new ScriptItem().addChild(gc), new ScriptItem(), new ScriptItem() ]
	        children.each {
	            parent.addChild(it)
	        }
	        
	        def iter = new ScriptIterator(parent);
	        
	        assertTrue(iter.forward() == parent)
	        assertTrue(iter.forward() == children[0])
	        assertTrue(iter.forward() == gc)
	        assertTrue(iter.forward() == children[1])
	        assertTrue(iter.forward() == children[2])
	    }
	   
	   @Test
	    void testDeepTree() {
	        def parent = new ScriptItem()
	        def gc = new ScriptItem()
	        def ggc = new ScriptItem()
	        def children = [ new ScriptItem().addChild(gc).addChild(ggc), new ScriptItem(), new ScriptItem() ]
	        children.each {
	            parent.addChild(it)
	        }
	        
	        def iter = new ScriptIterator(parent);
	        
	        assertTrue(iter.forward() == parent)
	        assertTrue(iter.forward() == children[0])
	        assertTrue(iter.forward() == gc)
	        assertTrue(iter.forward() == ggc)
	        assertTrue(iter.forward() == children[1])
	        assertTrue(iter.forward() == children[2])
	        assertTrue(iter.forward() == null)
	        assertTrue(iter.forward() == null)
	    }
	   
}