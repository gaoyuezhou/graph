package graph;

/* See restrictions in Graph.java. */

import java.util.ArrayList;

/** Represents a general unlabeled directed graph whose vertices are denoted by
 *  positive integers. Graphs may have self edges.
 *
 *  @author Gaoyue Zhou
 */
public class DirectedGraph extends GraphObj {

    @Override
    public boolean isDirected() {
        return true;
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
