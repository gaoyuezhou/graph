package graph;

import org.junit.Test;



import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

/** Unit tests for the Graph class.
 *  @author Gaoyue Zhou
 */
public class GraphTest {

    @Test
    public void emptyGraph() {
        DirectedGraph g = new DirectedGraph();
        assertEquals("Initial graph has vertices", 0, g.vertexSize());
        assertEquals("Initial graph has edges", 0, g.edgeSize());
    }

    @Test
    public void generalUdir() {
        UndirectedGraph g = new UndirectedGraph();
        g.add(); g.add();
        assertEquals(3, g.add());
        g.remove(2);
        g.add();  g.add();
        int[] expectedVertices = {1, 2, 3, 4};
        for (int i = 0; i < g.vertexSize(); i++) {
            assertEquals(expectedVertices[i],
                    g.getIDVertex(g.verticesList().get(i)));
        }

        g.add(2, 4);
        assertEquals(2, g.getInfoEdge(g.edgesList().get(0))[0][0]);
        assertEquals(4, g.getInfoEdge(g.edgesList().get(0))[0][1]);
        assertTrue(g.contains(4, 2));
        assertTrue(g.contains(2, 4));
        assertEquals(1, g.edgeId(4, 2));

        g.add(2, 3);
        assertEquals(2, g.edgeId(2, 3));
        g.remove(2, 3);
        g.add(2, 3);
        assertEquals(3, g.edgeId(2, 3));
        assertEquals(2, g.inDegree(2));
        assertEquals(2, g.degree(2));

        Iteration<int[]> t = g.edges();
        int[] a = t.next();
        assertEquals(2, a[0]);
        assertEquals(4, a[1]);
        assertTrue(t.hasNext());
        t.next();
        assertFalse(t.hasNext());

        Iteration<Integer> b = g.vertices();
        assertEquals(1, (int) b.next());
        assertEquals(3, (int) b.next());
        g.remove(2);
        assertEquals(4, (int) b.next());
        assertFalse(b.hasNext());
        g.add();
        assertEquals(2, (int) b.next());
    }

    @Test
    public void vertexTestUndir() {
        UndirectedGraph g = new UndirectedGraph();
        for (int i = 0; i < 10; i++) {
            g.add();
        }
        Iteration<Integer> it = g.vertices();
        for (int i = 0; i < 5; i++) {
            assertEquals((int) i + 1, (int) it.next());
        }
        g.remove(6);
        assertEquals(7, (int) it.next());


        g.add();
        g.add(1, 1); g.add(1, 6);
        g.add(1, 2); g.add(1, 6);
        g.add(4, 1); g.add(10, 2);

        Iteration<int[]> egs = g.edges();

        Iteration<Integer> successors = g.successors(1);
        Iteration<Integer> predecessors = g.predecessors(1);
        while (successors.hasNext()) {
            assertEquals(predecessors.next(), successors.next());
        }
        Iteration<Integer> successors2 = g.successors(1);
        g.remove(6); g.remove(2);
        assertEquals(1, (int) successors2.next());
        assertEquals(4, (int) successors2.next());
        assertFalse(successors2.hasNext());

        g.add();  g.add();
        g.add(1, 1); g.add(1, 6);
        g.add(1, 2); g.add(4, 1);
        Iteration<Integer> successors3 = g.successors(1);
        g.remove(1, 6);
        int[] expected = {1, 4, 2};
        int i = 0;
        while (successors3.hasNext()) {
            assertEquals(expected[i], (int) successors3.next());
            i++;
        }

    }

    @Test
    public void specificVertexTestDir() {
        DirectedGraph g = new DirectedGraph();
        for (int i = 0; i < 10; i++) {
            g.add();
        }
        Iteration<Integer> it = g.vertices();
        for (int i = 0; i < 5; i++) {
            assertEquals((int) i + 1, (int) it.next());
        }
        g.remove(6);
        assertEquals(7, (int) it.next());

        g.add();
        g.add(1, 1); g.add(1, 6);
        g.add(1, 2); g.add(1, 6); g.add(4, 1); g.add(10, 2);
        Iteration<Integer> successors = g.successors(1);

        int[] correctSuccessors = {1, 6, 2};
        int j = 0;
        while (successors.hasNext()) {
            assertEquals(correctSuccessors[j], (int) successors.next());
            j++;
        }

        Iteration<Integer> successorsfor6 = g.successors(6);
        assertFalse(successorsfor6.hasNext());

        Iteration<Integer> predecessorsfor1 = g.predecessors(1);
        assertEquals(1, (int) predecessorsfor1.next());
        assertEquals(4, (int) predecessorsfor1.next());
        assertFalse(predecessorsfor1.hasNext());

        Iteration<Integer> successors2 = g.successors(1);
        g.remove(6);
        assertEquals(1, (int) successors2.next());
        assertEquals(2, (int) successors2.next());
        assertFalse(successors2.hasNext());

        g.add();
        g.add(1, 1); g.add(1, 6);
        g.add(1, 2); g.add(4, 1);
        Iteration<Integer> successors3 = g.successors(1);
        g.remove(1, 6);
        int[] expected = {1, 2};
        int i = 0;
        while (successors3.hasNext()) {
            assertEquals(expected[i], (int) successors3.next());
            i++;
        }

    }


    @Test
    public void forme() {

        class It<Integer> implements Iterator<Integer> {
            ArrayList<Integer> array;
            int index = 0;
            It(ArrayList<Integer> a) {
                array = a;
            }

            @Override
            public Integer next() {
                Integer wanted = array.get(index);
                index += 1;
                return wanted;
            }

            @Override
            public boolean hasNext() {
                return true;
            }
        }

        ArrayList<Integer> test = new ArrayList<>();
        test.add(1); test.add(2); test.add(3); test.add(6);
        It b = new It<Integer>(test);
        test.remove(1);

    }

    @Test
    public void sspaths() {
        class Testpath1 extends SimpleShortestPaths {
            Testpath1(Graph G, int source, int dest) {
                super(G, source, dest);
            }

            @Override
            public double getWeight(int u, int v) {
                return 1;
            }
        }
        UndirectedGraph g = new UndirectedGraph();
        g.add(); g.add(); g.add(); g.add(); g.add();
        g.add(1, 2); g.add(2, 3); g.add(3, 4);
        g.add(4, 5);

        g.add(); g.add();
        g.add(4, 6); g.add(2, 7); g.add(7, 3);
        g.add(2, 4); g.add(7, 5); g.add(1, 7);
        Testpath1 simple = new Testpath1(g, 1, 5);
        simple.setPaths();
        List<Integer> path = simple.pathTo();
    }


    @Test
    public void unDirected() {
        UndirectedGraph g = new UndirectedGraph();
        DepthFirstTraversal dep = new DepthFirstTraversal(g);
        g.add(); g.add(); g.add(); g.add(); g.add();
        g.add(1, 2); g.add(2, 3); g.add(3, 4);
        g.add(4, 5);

        g.add(); g.add();
        g.add(4, 6); g.add(2, 7);
        dep.traverse(1);

    }

    @Test
    public void otherTests() {
        UndirectedGraph g = new UndirectedGraph();
    }

    @Test
    public void postOrderTest2() {
        DirectedGraph c = new DirectedGraph();
        for (int i = 0; i < 6; i++) {
            c.add();
        }
        c.add(1, 4);
        c.add(1, 2);
        c.add(2, 5);
        c.add(3, 5);
        c.add(3, 6);
        c.add(4, 2);
        c.add(5, 4);
        c.add(5, 6);
        DepthFirstTraversal dfsC = new DepthFirstTraversal(c);
        dfsC.traverse(1);
    }

    @Test
    public void forGraph() {
        UndirectedGraph g = new UndirectedGraph();
        g.add(); g.add(); g.add(); g.add(); g.add(); g.add(); g.add();
        g.add(); g.add(); g.add(); g.add(); g.add();
        assertEquals(12, g.maxVertex());
        g.remove(12);
        assertEquals(11, g.maxVertex());

        g.add(1, 2); g.add(1, 3); g.add(1, 4); g.add(1, 5);
        Iteration<Integer> v1succ = g.successors(1);
        assertTrue(v1succ.hasNext());
        g.remove(1);
        assertFalse(v1succ.hasNext());
        Iteration<Integer> v1removedsucc = g.successors(1);
        assertFalse(v1removedsucc.hasNext());
    }

    @Test
    public void shortestpasttest() {
        UndirectedGraph c = new UndirectedGraph();
        for (int i = 0; i < 6; i++) {
            c.add();
        }
        c.add(1, 4);
        c.add(1, 2);
        c.add(2, 5);
        c.add(3, 5);
        c.add(3, 6);
        c.add(4, 2);
        c.add(5, 4);
        c.add(5, 6);
        ShortestPaths path = new ShortestPaths(c, 2, 6) {
            double[] distances = new double[c.maxVertex() + 1];

            int[] parent = new int[c.maxVertex() + 1];

            @Override
            public double getWeight(int v) {
                return 0;
            }

            @Override
            protected void setWeight(int v, double w) {

            }

            @Override
            public int getPredecessor(int v) {
                return parent[v];
            }

            @Override
            protected void setPredecessor(int v, int u) {
                parent[v] = u;
            }

            @Override
            protected double getWeight(int u, int v) {
                return 0;
            }
        };
        path.setPaths();
        List<Integer> e = path.pathTo(6);
    }

    @Test
    public void pathdest0() {

        class Testpath1 extends SimpleShortestPaths {
            Testpath1(Graph G, int source, int dest) {
                super(G, source, dest);
            }

            @Override
            public double getWeight(int u, int v) {
                return 1;
            }
        }
        UndirectedGraph g = new UndirectedGraph();
        g.add(); g.add(); g.add(); g.add(); g.add();
        g.add(1, 2); g.add(2, 3); g.add(3, 4);
        g.add(4, 5);

        g.add(); g.add();
        g.add(4, 6); g.add(2, 7); g.add(7, 3);
        g.add(2, 4); g.add(7, 5); g.add(1, 7);
        Testpath1 simple = new Testpath1(g, 1, 0);
        simple.setPaths();

        int[] for7 = {1, 7};
        int[] for5 = {1, 7, 5};
        int[] for1 = {1};
        int[] for2 = {1, 2};
        List<Integer> path7 = simple.pathTo(7);
        for (int i = 0; i < path7.size(); i++) {
            assertEquals(for7[i], (int) path7.get(i));
        }
        List<Integer> path5 = simple.pathTo(5);
        for (int i = 0; i < path7.size(); i++) {
            assertEquals(for5[i], (int) path5.get(i));
        }
        List<Integer> path1 = simple.pathTo(1);
        for (int i = 0; i < path1.size(); i++) {
            assertEquals(for1[i], (int) path1.get(i));
        }
        List<Integer> path2 = simple.pathTo(2);
        for (int i = 0; i < path7.size(); i++) {
            assertEquals(for2[i], (int) path2.get(i));
        }

    }

}
