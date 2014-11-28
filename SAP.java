public class SAP {
    Digraph graph;
    int anc;
    
    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        graph = G;
        anc = 0;
    }
    

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        int len = 0;
        int shortlength = 0;
        BreadthFirstDirectedPaths dfsv = new BreadthFirstDirectedPaths(graph, v);
        BreadthFirstDirectedPaths dfsw = new BreadthFirstDirectedPaths(graph, w);
        boolean flag = false;
        if (v>graph.V() || w > graph.V())
            return -1;
        else {
            for(int x = 0; x < graph.V(); x++) {
                if(dfsv.hasPathTo(x) && dfsw.hasPathTo(x)) {
                    for(int tmp: dfsv.pathTo(x))
                        len++;
                    for(int tmp: dfsw.pathTo(x))
                        len++;
                    
                    if (!flag) {
                        anc = x;
                        shortlength = len;
                    }
                    else if(len < shortlength) {
                        anc = x;
                        shortlength = len;
                    }
                    
                    flag = true;
                    len = 0;
                }
            }
        }
        if (!flag) {
            anc = -1;
            return -1;
        }
        
        return shortlength - 2;
                
    }        

        

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        if (v>graph.V() || w > graph.V())
            return -1;
        else
            length(v, w);
        
        return anc;
    }  
                

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        int len = 0;
        int shortlength = 0;
        boolean flag = false;

        for (int x: v) {
            for (int y: w) {
                len = length(x, y);
                if(len != -1)
                    flag = true;
                
                if (!flag) {
                    shortlength = len;
                }
                else if(len < shortlength) {
                    shortlength = len;
                }
            }
        }
        return shortlength;
                
    }    


    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        length(v, w);
        
        return anc;
    } 

    
    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
      
}
