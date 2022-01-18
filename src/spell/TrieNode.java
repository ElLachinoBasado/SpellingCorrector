package spell;

public class TrieNode implements INode{
    private TrieNode parent;
    private TrieNode[] children;
    private int count;
    //private char[] alphaArray = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};

    /**
     *
     * @param pNode the node that will be this node's parent
     */
    public TrieNode(TrieNode pNode) {
        parent = pNode;
        children = new TrieNode[26];
        count = 0;
    }

    public TrieNode(TrieNode pNode, TrieNode[] cNodes, int c) {
        parent = pNode;
        children = cNodes;
        count = c;
    }

    public TrieNode() { //only the root node should use this
        parent = null;
        children = new TrieNode[26];
        count = 0;
    }

    @Override
    public int getValue() {
        return count;
    }

    @Override
    public void incrementValue() {
        count += 1;
    }

    @Override
    public INode[] getChildren() {
        return children;
    }

    public TrieNode getChild(int position) {
        return children[position];
    }

    public TrieNode getParent() {
        return parent;
    }

    public void addChild(TrieNode pNode, int position) {
        children[position] = new TrieNode(pNode);
    }

    public TrieNode copy() {
        return new TrieNode(parent, children, count);
    }
}
