package graph;

/* See restrictions in Graph.java. */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;

/** A partial implementation of Graph containing elements common to
 *  directed and undirected graphs.
 *
 *  @author Gaoyue Zhou
 */
abstract class GraphObj extends Graph {

    /** a count number for POSSIBLEEDGEID. */
    private int possibleEdgeID = 1;
    /** the list of all vertices. */
    private ArrayList<Vertex> vertices;
    /** the list of all vertices for iteration. */
    private ArrayList<Integer> verticesForIter;
    /** the list of all edges. */
    private ArrayList<Edge> edges;

    /** A new, empty Graph. */
    GraphObj() {
        vertices = new ArrayList<>();
        verticesForIter = new ArrayList<>();
        edges = new ArrayList<>();
    }

    /** RETURN the list of all vertices. */
    ArrayList<Vertex> verticesList() {
        return vertices;
    }

    /** RETURN the list of all EDGES. */
    ArrayList<Edge> edgesList() {
        return edges;
    }

    @Override
    public int vertexSize() {
        int count = 0;
        for (int i = 0; i < vertices.size(); i++) {
            if (vertices.get(i) != null) {
                count += 1;
            }
        }
        return count;
    }

    @Override
    public int maxVertex() {
        for (int i = vertices.size() - 1; i >= 0; i--) {
            if (vertices.get(i) != null) {
                return i + 1;
            }
        }
        return 0;
    }

    @Override
    public int edgeSize() {
        return edges.size();
    }

    @Override
    public abstract boolean isDirected();

    @Override
    public int outDegree(int v) {
        if (contains(v)) {
            return vertexObj(v).mySuccessors().size();
        } else {
            return 0;
        }
    }


    /** RETURN the vertex object of V. */
    Vertex vertexObj(int v)  {
        return vertices.get(v - 1);
    }

    @Override
    public abstract int inDegree(int v);

    @Override
    public boolean contains(int u) {
        return u <= vertices.size() && vertices.get(u - 1) != null;
    }

    @Override
    public boolean contains(int u, int v) {
        Vertex vertexU = vertexObj(u);
        if (vertexU.mySuccessors().contains(v)) {
            return true;
        }

        return false;
    }

    @Override
    public int add() {
        Vertex nvertex;
        for (int i = 0; i < vertices.size(); i++) {
            if (vertices.get(i) == null) {
                nvertex = new Vertex(i + 1);
                vertices.set(i, nvertex);
                verticesForIter.add(i + 1);
                return i + 1;
            }
        }

        nvertex = new Vertex(vertices.size() + 1);
        vertices.add(nvertex);
        verticesForIter.add(nvertex.getId());
        return nvertex.getId();

    }

    @Override
    public int add(int u, int v) {
        if (contains(u, v)) {
            return edgeId(u, v);
        }

        Edge current;
        if (isDirected()) {
            current = new Edge(true, u, v);
            vertexObj(u).addSuccessor(v);
            vertexObj(v).addPredecessor(u);
        } else {
            current = new Edge(false, u, v);
            vertexObj(u).addSuccessor(v);
            vertexObj(u).addPredecessor(v);
            vertexObj(v).addSuccessor(u);
            vertexObj(v).addPredecessor(u);
        }
        edges.add(current);
        return current.id;
    }

    @Override
    public void remove(int v) {
        if (v <= vertices.size()) {
            Vertex removed = vertices.get(v - 1);
            vertices.set(v - 1, null);
            verticesForIter.remove((Integer) v);

            for (int pred: removed.myPredecessors()) {
                if (pred != v) {
                    Vertex predobj = vertexObj(pred);
                    predobj.removeSuccessor(v);
                }
            }

            for (int succ: removed.mySuccessors()) {
                if (succ != v) {
                    Vertex succobj = vertexObj(succ);
                    succobj.removePredecessor(v);
                }
            }

            removed.clear();

            ArrayList<Edge> edgeCopy = new ArrayList<>(edges);
            for (int i = 0; i < edgeCopy.size(); i++) {
                Edge currentEdge = edgeCopy.get(i);
                if (currentEdge.edgeEnds[0] == v
                        || currentEdge.edgeEnds[1] == v) {
                    edges.remove(currentEdge);
                }
            }
        }
    }

    @Override
    public void remove(int u, int v) {
        int[] currentEdge = {u, v};
        boolean reallyRemoved = false;
        for (int i = 0; i < edges.size(); i++) {
            Edge current = edges.get(i);
            for (int j = 0; j < current.info.length; j++) {
                if (Arrays.equals(current.info[j], currentEdge)) {
                    edges.remove(current);
                    reallyRemoved = true;
                }
            }
        }

        if (reallyRemoved) {
            Vertex pred = vertexObj(u);
            Vertex succ = vertexObj(v);
            pred.removeSuccessor(v);
            succ.removePredecessor(u);
        }
    }

    @Override
    public Iteration<Integer> vertices() {
        return new VertexIteration<>(verticesForIter);
    }

    @Override
    public Iteration<Integer> successors(int v) {
        if (contains(v)) {
            return vertexObj(v).iterSuccessors();
        } else {
            return new VertexIteration<>(new ArrayList<Integer>());
        }

    }

    @Override
    public abstract Iteration<Integer> predecessors(int v);

    @Override
    public Iteration<int[]> edges() {
        EdgeIteration it = new EdgeIteration(edges);
        return it;
    }

    @Override
    protected void checkMyVertex(int v) {
        if (!contains(v)) {
            throw new NullPointerException("no such vertex");
        }
    }

    /** RETURN the id of vertex V. */
    int getIDVertex(Vertex v) {
        return v.getId();
    }

    @Override
    protected int edgeId(int u, int v) {
        int[] currentEdge = {u, v};
        for (int i = 0; i < edges.size(); i++) {
            Edge current = edges.get(i);
            for (int j = 0; j < current.info.length; j++) {
                if (Arrays.equals(current.info[j], currentEdge)) {
                    return current.id;
                }
            }
        }
        return 0;
    }

    /** a subclass of iteration for vertices. */
    class VertexIteration<Integer> extends Iteration<Integer> {

        /** the list of all vertices to be iterated. */
        private ArrayList<Integer> myVertices;
        /** the current index. */
        private int index;

        /** initialize a new VERTEXITERATION with VERTS. */
        VertexIteration(ArrayList<Integer> verts) {
            this.myVertices = verts;
            index = 0;
        }

        @Override
        public boolean hasNext() {
            if (myVertices == null) {
                return false;
            }
            if (isLegal(index)) {
                return true;
            } else {
                doubleCheck();
                return isLegal(index);
            }
        }

        /** RETURN if index IND is legal. */
        boolean isLegal(int ind)  {
            return ind < myVertices.size() && myVertices.get(ind) != null;
        }

        @Override
        public Integer next() {
            if (hasNext()) {
                Integer wanted = myVertices.get(index);
                index += 1;
                doubleCheck();
                return wanted;
            } else {
                throw new NoSuchElementException("no next vertex!");
            }
        }

        /** a helper method to double check if the next index is legal. */
        private void doubleCheck() {
            while (index < myVertices.size() && myVertices.get(index) == null) {
                index += 1;
            }
        }

    }

    /** a subclass of iteration for edges. */
    class EdgeIteration extends Iteration<int[]> {

        /** the list of all edges to be iterated. */
        private ArrayList<Edge> myedges;
        /** the current index. */
        private int index;

        /** initialize a new EDGEITERATION with EGS. */
        EdgeIteration(ArrayList<Edge> egs) {
            this.myedges = egs;
            index = 0;
        }

        @Override
        public int[] next() {
            if (hasNext()) {
                int[] wanted = myedges.get(index).edgeEnds;
                index += 1;
                return wanted;
            } else {
                throw new NoSuchElementException("no next edge!");
            }
        }

        @Override
        public boolean hasNext() {
            return index < myedges.size();
        }
    }

    /** represents the EDGE object. */
    class Vertex {

        /** MY ID. */
        private int _id;
        /** my PREDECESSORS. */
        private ArrayList<Integer> _predecessors;
        /** my PREDECESSORS for iteration purpose. */
        private ArrayList<Integer> _predecessorsForIter;
        /** my SUCCESSORS. */
        private ArrayList<Integer> _successors;
        /** my SUCCESSORS for iteration purpose. */
        private ArrayList<Integer> _successorsForIter;

        /** construct a new VERTEX with ID. */
        Vertex(int id) {
            this._id = id;
            _predecessors = new ArrayList<>();
            _predecessorsForIter = new ArrayList<>();
            _successors = new ArrayList<>();
            _successorsForIter = new ArrayList<>();
        }

        /**  RETURN my id. */
        int getId() {
            return _id;
        }

        /**  add U to my predecessors. */
        void addPredecessor(int u) {
            if (!_predecessors.contains(u)) {
                _predecessors.add(u);
                _predecessorsForIter.add(u);
            }
        }

        /**  add U to my successors. */
        void addSuccessor(int u) {
            if (!_successors.contains(u)) {
                _successors.add(u);
                _successorsForIter.add(u);
            }
        }

        /**  remove U from my predecessors. */
        void removePredecessor(int u) {
            _predecessors.remove((Integer) u);
            _predecessorsForIter.set(_predecessorsForIter.indexOf(u), null);
        }

        /**  remove U from my successors. */
        void removeSuccessor(int u) {
            _successors.remove((Integer) u);
            _successorsForIter.set(_successorsForIter.indexOf(u), null);
        }

        /**  CLEAR all my info to empty. */
        void clear() {
            _successors.clear();
            _successorsForIter.clear();
            _predecessors.clear();
            _predecessorsForIter.clear();
        }

        /**  RETURN an iterator over my predecessors. */
        Iteration<Integer> iterPredecessors() {
            return new VertexIteration<>(_predecessorsForIter);
        }

        /**  RETURN an iterator over my successors. */
        Iteration<Integer> iterSuccessors() {
            return new VertexIteration<>(_successorsForIter);
        }

        /**  RETURN a list of MYPREDECESSORS. */
        ArrayList<Integer> myPredecessors() {
            return _predecessors;
        }

        /** RETURN a list of MYSUCCESSORS. */
        ArrayList<Integer> mySuccessors() {
            return _successors;
        }
    }

    /**  RETURN the info of edge E. */
    int[][] getInfoEdge(Edge e) {
        return e.info;
    }

    /** represents the EDGE object. */
    class Edge {

        /** if I'm DIRECTED. */
        private boolean directed;
        /** my INFO of vertices. */
        private int[][] info;
        /** the vertices of EDGEENDS.*/
        private int[] edgeEnds;
        /** MY ID, a unique integer. */
        private int id;

        /** construct a new edge of IMDIRECTED with index U and V . */
        Edge(boolean imdirected, int u, int v) {
            this.directed = imdirected;
            int[] inOrder = {u, v};
            edgeEnds = inOrder;
            this.id = possibleEdgeID;
            possibleEdgeID += 1;

            if (imdirected) {
                this.info = new int[1][2];
                this.info[0] = inOrder;
            } else {
                this.info = new int[2][2];
                int[] reverseOrder = {v, u};
                this.info[0] = inOrder;
                this.info[1] = reverseOrder;
            }
        }

        /** Return the vertex number of the other
         * vertex of me other than FIRST. */
        Integer theOtherVertex(int first) {
            assert (edgeEnds[0] == first || edgeEnds[1] == first);
            if (first == edgeEnds[0]) {
                return edgeEnds[1];
            } else {
                return edgeEnds[0];
            }
        }

        @Override
        public String toString() {
            String wanted = "[" + edgeEnds[0]
                    + ", " + edgeEnds[1] + "]";
            return wanted;
        }
    }

}
