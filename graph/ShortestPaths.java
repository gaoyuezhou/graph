package graph;

/* See restrictions in Graph.java. */

import java.util.Collection;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.Iterator;

/** The shortest paths through an edge-weighted graph.
 *  By overrriding methods getWeight, setWeight, getPredecessor, and
 *  setPredecessor, the client can determine how to represent the weighting
 *  and the search results.  By overriding estimatedDistance, clients
 *  can search for paths to specific destinations using A* search.
 *  @author Gaoyue Zhou
 */
public abstract class ShortestPaths {

    /** an arraylist to store the path found. */
    private ArrayList<Integer> path;

    /** The shortest paths in G from SOURCE. */
    public ShortestPaths(Graph G, int source)  {
        this(G, source, 0);
    }

    /** A shortest path in G from SOURCE to DEST. */
    public ShortestPaths(Graph G, int source, int dest) {
        _G = G;
        _source = source;
        _dest = dest;
    }


    /** Initialize the shortest paths.  Must be called before using
     *  getWeight, getPredecessor, and pathTo. */
    public void setPaths() {
        Comparator<Integer> totalCompare = (Integer v1, Integer v2) ->  {
            if (v1 == v2) {
                return 0;
            } else {
                if (getWeight(v1) + estimatedDistance(v1)
                        > getWeight(v2) + estimatedDistance(v2)) {
                    return 1;
                } else if (getWeight(v1) + estimatedDistance(v1)
                        < getWeight(v2) + estimatedDistance(v2)) {
                    return -1;
                } else {
                    return 0;
                }
            }
        };

        TreeSet<Integer> temp = new TreeSet<>(totalCompare);
        TreeSetQueue queue = new TreeSetQueue(temp);
        ShortestPathTraversal curTraverse
                = new ShortestPathTraversal(_G, queue);
        curTraverse.traverse(getSource());

    }

    /** Returns the starting vertex. */
    public int getSource() {
        return _source;
    }

    /** Returns the target vertex, or 0 if there is none. */
    public int getDest()  {
        return _dest;
    }

    /** Returns the current weight of vertex V in the graph.  If V is
     *  not in the graph, returns positive infinity. */
    public abstract double getWeight(int v);

    /** Set getWeight(V) to W. Assumes V is in the graph. */
    protected abstract void setWeight(int v, double w);

    /** Returns the current predecessor vertex of vertex V in the graph, or 0 if
     *  V is not in the graph or has no predecessor. */
    public abstract int getPredecessor(int v);

    /** Set getPredecessor(V) to U. */
    protected abstract void setPredecessor(int v, int u);

    /** Returns an estimated heuristic weight of the shortest path from vertex
     *  V to the destination vertex (if any).  This is assumed to be less
     *  than the actual weight, and is 0 by default. */
    protected double estimatedDistance(int v)  {
        return 0.0;
    }

    /** Returns the current weight of edge (U, V) in the graph.  If (U, V) is
     *  not in the graph, returns positive infinity. */
    protected abstract double getWeight(int u, int v);

    /** Returns a list of vertices starting at _source and ending
     *  at V that represents a shortest path to V.  Invalid if there is a
     *  destination vertex other than V. */
    public List<Integer> pathTo(int v) {
        if (_dest != v && _dest != 0) {
            return new ArrayList<>();
        }

        path = new ArrayList<>();
        int dest = v;

        while (dest != _source) {
            path.add(0, dest);
            dest = getPredecessor(dest);
        }
        path.add(0, dest);
        return path;
    }

    /** Returns a list of vertices starting at the source and ending at the
     *  destination vertex. Invalid if the destination is not specified. */
    public List<Integer> pathTo()  {
        return pathTo(getDest());
    }


    /** The graph being searched. */
    protected final Graph _G;
    /** The starting vertex. */
    private final int _source;
    /** The target vertex. */
    private final int _dest;

    /** a QUEUE that has a TreeSet as the main data structure. */
    class TreeSetQueue implements Queue<Integer> {

        /** myqueue with all information. */
        private TreeSet<Integer> myqueue;

        /** a new TREESETQUEUE with QUEUE. */
        TreeSetQueue(TreeSet<Integer> queue)  {
            myqueue = queue;
        }

        @Override
        public int size()  {
            return myqueue.size();
        }

        @Override
        public boolean isEmpty()  {
            return myqueue.isEmpty();
        }

        @Override
        public boolean contains(Object o)  {
            return myqueue.contains(o);
        }

        @Override
        public Iterator<Integer> iterator()  {
            return myqueue.iterator();
        }

        @Override
        public Object[] toArray()  {
            return myqueue.toArray();
        }

        @Override
        public <T> T[] toArray(T[] a)  {
            return myqueue.toArray(a);
        }

        @Override
        public boolean add(Integer integer)  {
            return myqueue.add(integer);
        }

        @Override
        public boolean remove(Object o)  {
            return myqueue.remove(o);
        }

        @Override
        public boolean containsAll(Collection<?> c)  {
            return myqueue.containsAll(c);
        }

        @Override
        public boolean addAll(Collection<? extends Integer> c)  {
            return myqueue.addAll(c);
        }

        @Override
        public boolean removeAll(Collection<?> c)  {
            return myqueue.removeAll(c);
        }

        @Override
        public boolean retainAll(Collection<?> c)  {
            return myqueue.retainAll(c);
        }

        @Override
        public void clear()  {
            myqueue.clear();
        }

        @Override
        public boolean offer(Integer integer)  {
            return add(integer);
        }

        @Override
        public Integer remove()  {
            return poll();
        }

        @Override
        public Integer poll()  {
            return myqueue.pollFirst();
        }

        @Override
        public Integer element()  {
            return peek();
        }

        @Override
        public Integer peek()  {
            return myqueue.first();
        }
    }

    /** a traversal class used for finding the shortest path. */
    class ShortestPathTraversal extends Traversal {

        /** a new shortestpathtraversal
         * for G with fringe FRINGE. */
        ShortestPathTraversal(Graph G, Queue<Integer> fringe)  {
            super(G, fringe);
        }

        @Override
        protected boolean visit(int v) {
            if (v == _dest) {
                return false;
            }
            return true;
        }

        @Override
        protected boolean processSuccessor(int u, int v)  {
            if (!marked(v)) {
                if (getPredecessor(v) == 0 || getWeight(u, v)
                        + getWeight(u) < getWeight(v)) {
                    setPredecessor(v, u);
                    setWeight(v, getWeight(u, v) + getWeight(u));
                    if (_fringe.contains(v)) {
                        _fringe.remove(v);
                    }
                    _fringe.add(v);
                }
                return true;
            }
            return false;
        }

    }

}
