package spell;

import java.util.Locale;

public class Trie implements ITrie{
    private TrieNode root;
    private int wordCount;
    private int nodeCount;
    private StringBuilder result;

    public Trie() {
        root = new TrieNode();
        wordCount = 0;
        nodeCount = 1;
        result = new StringBuilder();
    }

    public char[] convertString(String word) {
        char[] charArray = new char[word.length()];

        for (int i = 0; i < word.length(); i++) { //creates an array of chars from the word
            charArray[i] = word.charAt(i);
        }
        return charArray;
    }

    public int[] convertCharArray(char[] charArray) { // returns array of ints that correspond to an alphabet ascii -97
        int[] intArray = new int[charArray.length];
        for (int i = 0; i < charArray.length; i++) {
            intArray[i] = (int) charArray[i] - 97;
        }
        return intArray;
    }

    @Override
    public void add(String word) {
        /**word = word.toLowerCase(); //decapitalizes everything
        char[] charArray = convertString(word); //creates an array of chars from the word
        int[] intArray = convertCharArray(charArray); // creates an array of ints corresponding to ascii

        TrieNode foundNode = (TrieNode) find(word);
        if (foundNode == null) { // if the word is not found in the tree
            recursiveAdd(root, intArray, 0);
            wordCount++;
        } else {
            foundNode.incrementValue();
        }**/

        TrieNode current = root;
        word = word.toLowerCase();
        for (int i = 0; i < word.length(); i++) {
            if (current.getChildren()[word.charAt(i)-97] == null) {
                current.getChildren()[word.charAt(i)-97] = new TrieNode(current);
                nodeCount++;
            }
            current = (TrieNode) current.getChildren()[word.charAt(i)-97];
        }
        if (current.getValue() == 0) {
            wordCount++;
        }
        current.incrementValue();
    }

//    public void recursiveAdd(TrieNode currentNode, int[] intArray, int pos) {
//        if (currentNode.getChild(intArray[pos]) == null) {
//            currentNode.addChild(currentNode,intArray[pos]);
//            nodeCount++;
//        }
//
//        if (pos < intArray.length - 1) {
//            TrieNode nextNode = currentNode.getChild(intArray[pos]);
//            recursiveAdd(nextNode, intArray, pos += 1);
//        } else {
//            currentNode.incrementValue();
//        }
//    }

    @Override
    public INode find(String word) {
        /**char[] charArray = convertString(word); //creates an array of chars from the word
        int[] intArray = convertCharArray(charArray); // creates an array of ints corresponding to ascii

        int i = 0;
        boolean foundNull = false;
        TrieNode nodeToCheck = root.copy();
        while (!foundNull && i < intArray.length) {
            TrieNode next = nodeToCheck.getChild(intArray[i]);
            if (i < intArray.length - 1) {
                if (next != null) {
                    nodeToCheck = next.copy();
                } else {
                    nodeToCheck = null;
                }
            } else {
                nodeToCheck = nodeToCheck.getChild(intArray[i]);
            }

            if (nodeToCheck == null) {foundNull = true;}
            else {i++;}
        }

        if (foundNull) {return null;}
        else {return nodeToCheck;} **/
        TrieNode current = root;
        word = word.toLowerCase();
        for (int i = 0; i < word.length(); i++) {
            if (current.getChildren()[word.charAt(i)-97] == null) return null;
            else current = (TrieNode) current.getChildren()[word.charAt(i)-97];
        }
        if (current.getValue() > 0) return current;
        else return null;
    }

    @Override
    public int getWordCount() {
        return wordCount;
    }

    @Override
    public int getNodeCount() {
        return nodeCount;
    }

    @Override
    public String toString() { //MUST BE RECURSIVE!
        StringBuilder convertedTrie = new StringBuilder("");
        result.setLength(0);
        toStringHelper(root,convertedTrie);
        return result.toString();
    }

    private void toStringHelper(TrieNode currentTrie, StringBuilder currentString) {
        if (currentTrie == null) {
            return;
        } else if (currentTrie.getValue() > 0) {
            result.append(currentString.toString());
            result.append('\n');
        }

        TrieNode[] children = (TrieNode[]) currentTrie.getChildren();
        for (int i = 0; i < children.length; i++) {
            if (children[i] != null) {
                currentString.append((char)(i+97));
                toStringHelper(children[i],currentString);
            }
        }
        if (currentString.length() > 0) {
            currentString.deleteCharAt(currentString.length()-1);
        }
    }

    @Override
    public int hashCode() {
        int hash = 0;
        for (int i = 0; i < root.getChildren().length; i++) {
            if (root.getChild(i) != null) {
                hash += i;
            }
        }
        return (wordCount * nodeCount * 31 * hash);
    }

    @Override
    public boolean equals(Object var1) {
        if (!(var1 instanceof Trie)) return false;
        if (((Trie) var1).getNodeCount() != nodeCount || ((Trie) var1).getWordCount() != wordCount) return false;
        return equalsHelper(root, ((Trie) var1).getRoot());
    } //MUST BE RECURSIVE!

    private boolean equalsHelper(TrieNode node1, TrieNode node2) {
        boolean isEqual = true;
        if (node1.getValue() != node2.getValue()) {
            return false;
        }

        TrieNode[] children1 = (TrieNode[]) node1.getChildren();
        TrieNode[] children2 = (TrieNode[]) node2.getChildren();
        for (int i = 0; i < children1.length; i++) {
            if (children1[i] != null && children2[i] != null) {
                isEqual = equalsHelper(children1[i], children2[i]);
            }

            if (!isEqual) return false;
        }
        return isEqual;
    }

    public TrieNode getRoot() {
        return root;
    }
}
