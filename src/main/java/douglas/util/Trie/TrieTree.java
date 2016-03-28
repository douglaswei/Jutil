package douglas.util.Trie;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:  wgz
 * Date:    16/3/26
 * Time:    下午3:14
 * Desc:
 */

public class TrieTree {

    private static final int RECURSION_TIME = 5;
    private static int WORD_LEN = 0;
    public TrieNode<Character> root = new TrieNode(null, "/", false, 0);
    private String separator = "/";

    public void insertTrieTree(String query, int freq) {
        char[] wordChar = query.toCharArray();
        TrieNode<Character> curNode = root;
        for (int i = 0; i < wordChar.length; ++i) {
            boolean isLeaf = (i == wordChar.length - 1);
            curNode = addTrieNode(curNode, wordChar[i], freq, isLeaf);
        }
    }

    private TrieNode<Character> addTrieNode(TrieNode<Character> curNode, char c, int freq, boolean isLeaf) {
        TrieNode<Character> ret = curNode.getChild(c);
        if (ret == null) {
            ret = new TrieNode(curNode, c, isLeaf, isLeaf ? freq : 0);
            curNode.addChild(c, ret);
        } else {
            if (curNode.isLeaf()) {
                curNode.addFreq(freq);
            }
        }
        return ret;
    }

    private TrieNode<Character> findEndNode(String query) {
        TrieNode<Character> curNode = root;
        for (char c : query.toCharArray()) {
            curNode = curNode.getChild(c);
            if (curNode == null) {
                return null;
            }
        }
        return curNode;
    }


    private TrieNode<Character> getBestLeaf(TrieNode<Character> cur) {
        TrieNode<Character> bestLeaf = null;
        if (cur.isLeaf()) {
            bestLeaf = cur;
        }
        for (TrieNode<Character> child : cur.getChildren()) {
            TrieNode<Character> leaf = getBestLeaf(child);
            if (bestLeaf == null || bestLeaf.getFreq() < leaf.getFreq()) {
                bestLeaf = leaf;
            }
        }
        return bestLeaf;
    }

    public String getAutoComplete(String query) {
        TrieNode<Character> endNode = findEndNode(query);
        if (endNode == null) {
            return "";
        }
        TrieNode<Character> leaf = getBestLeaf(endNode);
        List<Character> charList = new ArrayList<>();
        while (leaf != endNode) {
            charList.add(leaf.getValue());
            leaf = leaf.getParent();
        }

        StringBuffer sb = new StringBuffer();
        for (Object c : Lists.reverse(charList).toArray()) {
            sb.append((Character)c);
        }
        return sb.toString();
    }

    @Deprecated
    public synchronized String searchTrieTree(String query) {
        char[] textChar = query.toCharArray();
        TrieNode<Character> curNode = root;
        int backStep = 0;
        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < textChar.length; i++) {
            if (curNode.getChild(textChar[i]) != null) {
                if (curNode.getChild(textChar[i]).getValue() == textChar[i]) {
                    if (!curNode.getChild(textChar[i]).isLeaf()) {
                        WORD_LEN++;
                        backStep++;
                        curNode = curNode.getChild(textChar[i]);
                    } else {
                        WORD_LEN++;
                        backStep = 0;
                        i = searchMaxWord(curNode.getChild(textChar[i]),
                                textChar, i + 1);
                        String str = constructWord(textChar, i, WORD_LEN);
                        sb.append(str).append(separator);
                        curNode = root;
                        WORD_LEN = 0;
                    }
                } else {
                    curNode = root;
                    WORD_LEN = 0;
                }
            } else {
                while (backStep > 0) {
                    i--;
                    backStep--;
                }
                curNode = root;
                WORD_LEN = 0;
            }
        }
        return sb.toString().trim();
    }

    /**
     * @param textChar
     * @param endIndex
     * @param len
     * @return
     */
    private String constructWord(char[] textChar, int endIndex, int len) {
        int startIndex = endIndex + 1 - len;
        if (startIndex < 0) {
            startIndex = 0;
        }
        StringBuilder str = new StringBuilder();
        for (int i = startIndex; i <= endIndex; i++) {
//            System.out.println("i==" + i);
            str.append(textChar[i]);
        }
        return str.toString();
    }

    /**
     * 最大正向匹配改进
     *
     * @param node
     * @param textChar
     * @param index
     * @return
     */
    private int searchMaxWord(TrieNode<Character> node, char[] textChar, int index) {
        if (terminateCondition(node, textChar, index)) {
            return --index;
        }
        TrieNode<Character> tempNode = node;
        for (int i = index; i < index + RECURSION_TIME; i++) {
            if (!tempNode.getChild(textChar[i]).isLeaf()) {
                WORD_LEN++;
                tempNode = tempNode.getChild(textChar[i]);
            } else {
                WORD_LEN++;
                return searchMaxWord(tempNode.getChild(textChar[i]),
                        textChar, i + 1);
            }
        }
        return -1;
    }

    /**
     * 改进算法递归终止条件
     *
     * @param node
     * @param textChar
     * @param index
     * @return
     */
    private boolean terminateCondition(TrieNode<Character> node, char[] textChar,
                                       int index) {
        TrieNode<Character> tempNode = node;
        for (int i = index; i < index + RECURSION_TIME; i++) {
            if (i > textChar.length - 1) {
                return true;
            }
            if (tempNode.getChild(textChar[i]) == null) {
                return true;
            }
            if (!tempNode.getChild(textChar[i]).isLeaf()) {
                tempNode = tempNode.getChild(textChar[i]);
            } else {
                return false;
            }
        }
        return true;
    }

    public String toString(String word) {
        char[] c = word.toCharArray();
        TrieNode<Character> tempNode = root;
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < c.length; i++) {
            TrieNode<Character> currentNode = tempNode.getChild(c[i]);
            if (currentNode != null) {
                buf.append(currentNode.toString());
                buf.append("\n");
            }
            tempNode = tempNode.getChild(c[i]);
        }
        return buf.toString();
    }

}
