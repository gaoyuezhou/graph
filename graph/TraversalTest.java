package graph;

import org.junit.Test;

public class TraversalTest {

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

}
