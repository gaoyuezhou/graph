package make;

import graph.DirectedGraph;
import graph.LabeledGraph;

/** A directed, labeled subtype of Graph that describes dependencies between
 *  targets in a Makefile. The nodes correspond to Rules and edges out
 *  of rules are numbered to indicate the ordering of dependencies.
 *  @author Gaoyue Zhou
 */
class Depends extends LabeledGraph<Rule, Integer> {
    /** An empty dependency graph. */
    private int count = 1;

    /** A default depends object. */
    Depends() {
        super(new DirectedGraph());
    }

    /** add edge with number COUNT of vertices U and V,
     *  RETURN the edge id. */
    public int addEdgeWithCount(int u, int v) {
        int a = add(u, v, count);
        count += 1;
        return a;
    }

    /** Return the string representation of me for debugging purpose. */
    public String toString() {
        String want = "";
        for (int[] e : edges()) {
            want += "[" + e[0] + ", " + e[1] + "]";
        }
        want += "\n";
        for (int i : vertices()) {
            want += i + " ";
        }
        return want;
    }

}
