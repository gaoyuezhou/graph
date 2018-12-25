package trip;

/* You MAY add public @Test methods to this class.  You may also add
 * additional public classes containing "Testing" in their name. These
 * may not be part of your trip package per se (that is, it must be
 * possible to remove them and still have your package work). */

import org.junit.Test;
import ucb.junit.textui;
import static org.junit.Assert.*;

/** Unit tests for the trip package. */
public class UnitTest {

    /** Run all JUnit tests in the graph package. */
    public static void main(String[] ignored) {
        System.exit(textui.runClasses(trip.UnitTest.class));
    }

    @Test
    public void myTest() {
        Location a = new Location("school", 0, 0);
        Location b = new Location("home", 10, 10);
        Location c = new Location("c1", 20, 30);
        Location d = new Location("c2", 23, 5);
        Location f = new Location("f1", 44, 21);
        Trip trip = new Trip();
        assertEquals(0, (int) a.distance());
        assertEquals(14, (int) a.dist(a, b));
        a.setDistance(5.5);
        assertEquals(5, (int) a.distance());
        assertEquals(14, (int) a.dist(a, b));
        assertEquals("school", a.toString());
        assertEquals("f1", f.toString());
    }

    @Test
    public void roads() {
        Road a = new Road("AA", Direction.EW, 25);
        Road b = new Road("BB", Direction.WE, 454);
        Road c = new Road("CC", Direction.NS, 24);
        Road d = new Road("DD", Direction.WE, 10);
        assertEquals("west", a.direction().fullName());
        assertEquals(25, (int) a.length());
        assertNotEquals(a, b);
        assertTrue(a.length() < b.length());
    }

}
