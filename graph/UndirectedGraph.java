package graph;

/* See restrictions in Graph.java. */

import java.util.ArrayList;

/** Represents an undirected graph.  Out edges and in edges are not
 *  distinguished.  Likewise for successors and predecessors.
 *
 *  @author Gaoyue Zhou
 */
public class UndirectedGraph extends GraphObj {

    @Override
    public boolean isDirected() {
        return false;
    }

    @Override
    public int inDegree(int v) {
        if (contains(v)) {
            return vertexObj(v).myPredecessors().size();
        } else {
            return 0;
        }
    }

    @Override
    public Iteration<Integer> predecessors(int v) {
        if (contains(v)) {
            return vertexObj(v).iterPredecessors();
        } else {
            return new VertexIteration<>(new ArrayList<Integer>());
        }
    }
}
