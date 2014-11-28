public class Outcast {
    WordNet wn;
    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        wn = wordnet;
    }
    
       // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        int di = 0;
        int[] dist = new int[nouns.length];
        
        for(int i = 0; i < nouns.length; i++) {
            for(int j = 0; j < nouns.length; j++) {
                di += wn.distance(nouns[i], nouns[j]);
            }
            dist[i] = di;
            di = 0;
        }
        int index = 0;
        for(int i = 0; i < nouns.length; i++) {
            if(dist[i] > dist[index])
                index = i;
        }
        return nouns[index];
        
    }
       
       // see test client below
   public static void main(String[] args)  {
       WordNet wordnet = new WordNet(args[0], args[1]);
       Outcast outcast = new Outcast(wordnet);
       for (int t = 2; t < args.length; t++) {
           In in = new In(args[t]);
           String[] nouns = in.readAllStrings();
           StdOut.println(args[t] + ": " + outcast.outcast(nouns));
       }
   }
}