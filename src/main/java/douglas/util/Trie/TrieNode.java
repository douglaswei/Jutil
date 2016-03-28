package douglas.util.Trie;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Author:  wgz
 * Date:    16/3/26
 * Time:    下午3:51
 * Desc:
 */
public class TrieNode<T> implements Serializable {

    private TrieNode parent;
    private Map<T, TrieNode<T>> children = new HashMap();
    private T value;
    private boolean isLeaf = false;
    private int freq = 0;

    public TrieNode(TrieNode parent, T value, boolean isLeaf, int freq) {
        this.parent = parent;
        this.value = value;
        this.isLeaf = isLeaf;
        this.freq = freq;
    }

    public Collection<TrieNode<T>> getChildren() {
        return children.values();
    }

    public T getValue() {
        return value;
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public void setLeaf() {
        isLeaf = true;
    }

    public void addFreq(int freq) {
        this.freq += freq;
    }

    public int getFreq() {
        return freq;
    }

    public TrieNode getParent() {
        return parent;
    }

    public TrieNode<T> getChild(T node) {
        return children.get(node);
    }

    public void addChild(T key, TrieNode<T> node) {
        children.put(key, node);
    }

    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("<node value = " + value + " isLeaf = " + isLeaf + " freq = "
                + freq + " />");
        return buf.toString();
    }
}
