import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
//import java.lang.NullPointerException;

public class WordNet {
    private List<Integer> id;
    private List<String> noun;
    private List<String> def;
    private Digraph G;
    private SAP sap;
    private String shortStr = null;
    
    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        In in1 = new In(synsets);
        In in2 = new In(hypernyms);
        String tmpArray;
        id = new ArrayList<Integer>();
        noun = new ArrayList<String>();
        def = new ArrayList<String>();
        G = new Digraph(82192);
        
        while ((tmpArray = in1.readLine()) != null) {
            String[] array = tmpArray.split(",");
            id.add(Integer.parseInt(array[0]));
            noun.add(array[1]);
            def.add(array[2]);
        }
        while ((tmpArray = in2.readLine()) != null) {
            String[] array = tmpArray.split(",");
            int num = 1;
            while (num < array.length) {
                int v = Integer.parseInt(array[0]);
                int w = Integer.parseInt(array[num]);
                G.addEdge(v, w);
                num++;
            }
        }
        sap = new SAP(G);
                 
    }

   // returns all WordNet nouns
    public Iterable<String> nouns() {
        return new Iterable<String>() {
            public Iterator<String> iterator() {
                return noun.iterator();
            }
        };
    }
    

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        boolean flag = false;
        
        for (String nouns: nouns()) {
            String[] str = nouns.split(" ");
            int i = 0;
            
            while (i < str.length) {
                if (str[i].equals(word)) {
                     flag = true;
                     break;
                }
                i++;
            }
            if (flag)
                break;
        }
        return flag;
    }
    

   // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        int v = 0;
        int w = 0;
        int di = 0;
        int shortdi = 0;
        boolean flag = false;
        
        if (!isNoun(nounA))
            return -1;
        if (!isNoun(nounB))
            return -1;
        
        for (String nouns1: nouns()) {
            String[] str1 = nouns1.split(" ");
            int i = 0;
            
             while (i < str1.length) {
                 
                 if (str1[i].equals(nounA)) {
                     w = 0;
                     for (String nouns2: nouns()) {
                         String[] str2 = nouns2.split(" ");
                         i = 0;
            
                         while (i < str2.length) {
                             if (str2[i].equals(nounB)) { 
                                 di = sap.length(v, w);
                                 if (!flag) {
                                     flag = true;
                                     shortdi = di;
                                     shortStr = noun.get(sap.ancestor(v, w));
                                 }
                                 else if (di < shortdi) {
                                     shortdi = di;
                                     shortStr = noun.get(sap.ancestor(v, w));
                                 }
                                 break;
                             }
                             i++;
                         }
                         w++;
                     }
                     break;     
                 }
                 i++; 
             }
             v++;
        }     
        return shortdi;
    }

       
   // a synset (second field of synsets.txt) 
   // that is the common ancestor of nounA and nounB
   // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (!isNoun(nounA))
            return null;
        if (!isNoun(nounB))
            return null;
        
        distance(nounA, nounB);
          
        return shortStr;
    }
   

    // do unit testing of this class
    public static void main(String[] args) {
        WordNet wn = new WordNet("synsets.txt", "hypernyms.txt");
        StdOut.println("is JiangWen a noun:" + wn.isNoun("JiangWen"));
        StdOut.println("is AWOL a noun:" + wn.isNoun("AWOL")); 
        StdOut.println("is A_level a noun:" + wn.isNoun("A_level")); 
        StdOut.println("the distance between worm and bird : "
                           + wn.distance("worm", "bird"));
        StdOut.println("the synset between worm and bird:" 
                           + wn.sap("worm", "bird"));
    }
   
}