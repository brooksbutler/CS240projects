package spell;

import java.util.SortedSet;
import java.util.TreeSet;

public class Trie implements ITrie {
    private Node root;
    private int nodeCount;
    private int uniqueWordCount;
    private int globalFrequencyCount;
    // This sorted set will be used to print the words in our toString function.
    private  SortedSet<String> prevWords;

    // Construct the Trie object
    public Trie() {
        root = new Node();
        nodeCount = 1;
        uniqueWordCount = 0;
        prevWords = new TreeSet<>();
        globalFrequencyCount = 0;
    }

    @Override
    public void add(String word) {
        prevWords.add(word);
        Node currentNode = root;

        // For every letter in the word to be added
        for (int i = 0; i < word.length(); i++) {
            int charIndex = word.charAt(i) - 'a';
            // If no node exsists, create a new node and count it
            if (currentNode.nodes[charIndex] == null){
                currentNode.nodes[charIndex] = new Node();
                nodeCount++;
                // if this is the last letter count the word
                if (i == word.length()-1) {
                    uniqueWordCount++;
                    currentNode.nodes[charIndex].frequency++;
                    globalFrequencyCount++;
                }
                // move the current node to the next in the Trie
                currentNode = currentNode.nodes[charIndex];
            }
            else {
                if (i == word.length()-1) {
                    if (currentNode.nodes[charIndex].frequency == 0) {
                        uniqueWordCount++;
                    }
                    currentNode.nodes[charIndex].frequency++;
                    globalFrequencyCount++;
                }
                else {
                    currentNode = currentNode.nodes[charIndex];
                }
            }
        }
    }

    @Override
    public INode find(String word) {
        Node currentNode = root;
        for (int i = 0; i < word.length(); i++) {
            int charIndex = word.charAt(i) - 'a';
            if (currentNode.nodes[charIndex] == null)
                return null;
            else {
                if (i == word.length() - 1) {
                    if (currentNode.nodes[charIndex].frequency > 0) {
                        return currentNode.nodes[charIndex];
                    } else
                        return null;
                }
                currentNode = currentNode.nodes[charIndex];
            }
        }
        return null;
    }

    @Override
    public int getWordCount() {
        return uniqueWordCount;
    }

    @Override
    public int getNodeCount() {
        return nodeCount;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (String word : prevWords) {
            result.append(word).append("\n");
        }
        return result.toString();
    }

    public int getStringHash() {
        int hash = 0;
        for (String word : prevWords) {
            hash += word.hashCode();
        }
        return hash;
    }

    @Override
    public int hashCode() {
        return  this.getStringHash() * nodeCount * uniqueWordCount * globalFrequencyCount;
    }

    public int getFrequencySum(Trie Trie) {
        for(int i = 0; i < 26; i++) {

        }
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (this.getClass() != obj.getClass())
            return false;

        Trie otherTrie = (Trie) obj;
        // this.root.equals(otherTrie.root) &&
        return this.uniqueWordCount == otherTrie.uniqueWordCount && this.nodeCount == otherTrie.nodeCount
                && this.globalFrequencyCount == otherTrie.globalFrequencyCount;
    }


}
