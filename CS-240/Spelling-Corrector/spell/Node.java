package spell;

public class Node implements INode {
    private Node[] children = new Node[26];
    private int count = 0;
    /**
     * Returns the frequency count for the word represented by the node.
     *
     * @return the frequency count for the word represented by the node.
     */
    public int getValue(){
        return this.count;
    }

    /**
     * Increments the frequency count for the word represented by the node.
     */
    public void incrementValue(){
        this.count++;
    }

    /**
     * Returns the child nodes of this node.
     *
     * @return the child nodes.
     */
    public Node[] getChildren(){
        return this.children;
    }
}
