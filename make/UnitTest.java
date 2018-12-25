package make;

/* You MAY add public @Test methods to this class.  You may also add
 * additional public classes containing "UnitTest" in their name. These
 * may not be part of your make package per se (that is, it must be
 * possible to remove them and still have your package work). */

import org.junit.Test;
import ucb.junit.textui;
import static org.junit.Assert.*;

/** Unit tests for the make package. */
public class UnitTest {

    /** Run all JUnit tests in the make package. */
    public static void main(String[] ignored) {
        System.exit(textui.runClasses(make.UnitTest.class));
    }

    @Test
    public void myTest() {
        Maker m = new Maker();
        Rule r1 = new Rule(m, "dog");
        Rule r2 = new Rule(m, "cat");
        Rule r3 = new Rule(m, "mouse");
        r1.addDependency(r2);
        r1.addDependency(r3);
        for (int i: m.getGraph().successors(r1.getVertex())) {
            assertTrue(i == 2 || i == 3);
        }
        assertTrue(r2.isUnfinished());
        assertEquals("cat", r2.getTarget());
        assertTrue(r3.getVertex() == 3);
    }

}
