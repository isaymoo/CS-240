package spell;

public class Trie implements ITrie {
    private Node root;
    private Node currentNode;
    private int numWords = 0;
    private int nodeCount = 0;
    private String output = "";
    private String thisWord = "";
    /**
     * Adds the specified word to the trie (if necessary) and increments the word's frequency count.
     *
     * @param word the word being added to the trie
     */
    public void add(String word){
        currentNode = root;
        if(this.find(word) == null) {
            numWords++;
            int tempCount = 0;
            //code to add the word
            while(tempCount != word.length() - 1){
                char thisLetter = word.charAt(tempCount);
                Node[] helperNode = currentNode.getChildren();
                helperNode[thisLetter - 'a'] = new Node();
                nodeCount++;
                currentNode = helperNode[thisLetter - 'a'];
                tempCount++;
            }
            currentNode.incrementValue();
            numWords++;
        }
        currentNode = root;
    }

    /**
     * Searches the trie for the specified word.
     *
     * @param word the word being searched for.
     *
     * @return a reference to the trie node that represents the word,
     * 			or null if the word is not in the trie
     */
    public Node find(String word){
        int tempCount = 0;
        currentNode = root;
        while(tempCount != word.length() - 1){
            char thisLetter = word.charAt(tempCount);
            Node[] helperNode = currentNode.getChildren();
            if (helperNode[thisLetter - 'a'] == null) return null;
            currentNode = helperNode[thisLetter - 'a'];
            tempCount++;
        }
        return currentNode;
    }

    /**
     * Returns the number of unique words in the trie.
     *
     * @return the number of unique words in the trie
     */
    public int getWordCount(){
        return numWords;
    }

    /**
     * Returns the number of nodes in the trie.
     *
     * @return the number of nodes in the trie
     */
    public int getNodeCount(){
        return nodeCount;
    }

    /**
     * The toString specification is as follows:
     * For each word, in alphabetical order:
     * <word>\n
     * MUST BE RECURSIVE.
     */
    @Override
    public String toString(){
        Node[] currentChildren = currentNode.getChildren();
        for (int i = 0; i < 26; i++) {
            if (currentChildren[i] != null) {
                int p = i + 'a';
                thisWord += (char) p;
                if (currentChildren[i].getValue() > 0) {
                    output += thisWord;
                    output += '\n';
                }
                currentNode = currentChildren[i];
                toString();
                thisWord = thisWord.substring(0, thisWord.length() - 1);
            }
        }
        Node[] rootChildren = root.getChildren();
        if (currentChildren == rootChildren) System.out.println(output);
        return output;
    }

    /**
     * Returns the hashcode of this trie.
     * MUST be constant time.
     * @return a uniform, deterministic identifier for this trie.
     */
    @Override
    public int hashCode(){

    }

    /**
     * Checks if an object is equal to this trie.
     * MUST be recursive.
     * @param o Object to be compared against this trie
     * @return true if o is a Trie with same structure and node count for each node
     * 		   false otherwise
     */
    @Override
    public boolean equals(Object o){

    }
}
