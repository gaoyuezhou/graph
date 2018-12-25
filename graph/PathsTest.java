package graph;

import org.junit.Test;
import java.util.List;

public class PathsTest {

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
        for (int i = 0; i < path.size(); i++) {
            System.out.println(path.get(i));
        }
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
        List<Integer> path = simple.pathTo(7);

    }
}
