package graph;

/* See restrictions in Graph.java. */

/** A partial implementation of ShortestPaths that contains the weights of
 *  the vertices and the predecessor edges.   The client needs to
 *  supply only the two-argument getWeight method.
 *  @author Gaoyue Zhou
 */
public abstract class SimpleShortestPaths extends ShortestPaths {

    /** an array to store distances of vertices. */
    private double[] distances;
    /** an array to store parents of vertices. */
    private int[] parent;

    /** The shortest paths in G from SOURCE. */
    public SimpleShortestPaths(Graph G, int source)  {
        this(G, source, 0);
    }

    /** A shortest path in G from SOURCE to DEST. */
    public SimpleShortestPaths(Graph G, int source, int dest) {
        super(G, source, dest);
        distances = new double[G.maxVertex() + 1];
        parent = new int[G.maxVertex() + 1];
        for (int i : G.vertices()) {
            setWeight(i, Double.POSITIVE_INFINITY);
        }
        setWeight(source, 0);
    }

    /** Returns the current weight of edge (U, V) in the graph.  If (U, V) is
     *  not in the graph, returns positive infinity. */
    @Override
    protected abstract double getWeight(int u, int v);

    @Override
    public double getWeight(int v)  {
        return distances[v];
    }

    @Override
    protected void setWeight(int v, double w)  {
        distances[v] = w;
    }

    @Override
    public int getPredecessor(int v)  {
        return parent[v];
    }

    @Override
    protected void setPredecessor(int v, int u)  {
        parent[v] = u;
    }

}
