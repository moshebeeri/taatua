package org.vidad.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.bson.types.ObjectId;
import org.vidad.tools.conf.Collection;

import com.google.gson.Gson;

public class Trie extends Collectionable<Trie> {

	@Override
	public Collection getCollection() {
		return Collection.TRIE;
	}
	Collection collection;
	ObjectId rootId;
	String name;

    private final TrieNode root;

    /**
     * Creates a new empty TRIE object.
     */
    public Trie() {
        root = new TrieNode();
    }


    /**
     * Inserts the specified key into this Trie object.
     * @param key
     */
    public void insert(String key) {
        TrieNode currNode = root;
        for (char c : key.toCharArray()) {
            TrieNode child = currNode.traverse(c);
            if (child == null) {
                currNode = currNode.addEdge(c);
            } else {
                currNode = child;
            }
        }
        currNode.setKey(key);
    }

    /**
     * Returns all the keys in the Trie which start with the specified prefix.
     * @param prefix
     * @return
     */
    public List<String> search(String prefix) {
        TrieNode currNode = root;
        for (char c : prefix.toCharArray()) {
            TrieNode child = currNode.traverse(c);
            if (child == null) {
                return Collections.emptyList();
            } else {
                currNode = child;
            }
        }
        List<String> matches = new ArrayList<String>();
        preorderTraverse(currNode, matches);
        return matches;
    }

    /**
     * Does preorder traversal of Trie and add retrieved keys in the specified results list.
     * @param currNode
     * @param results
     */
    private void preorderTraverse(TrieNode currNode, List<String> results) {
        if (currNode == null) return;
        if (currNode.getKey() != null) {
            results.add(currNode.getKey());
        }
        Iterator<TrieNode> children = currNode.getChildren();
        if (children != null) {
            while (children.hasNext()) {
                preorderTraverse(children.next(), results);
            }
        }
    }
        /**
     * A Trie Node
     */
    public class TrieNode {


        /**
         * The key stored in this node if any.
         */
        private String key;

        /**
         * the outgoing edges of this node, implemented as a sorted map of character to the child node.
         */
        private Map<Character, TrieNode> edges;

		TrieNode addEdge(char c) {
            if (edges == null) {
            	edges = new TreeMap<Character, TrieNode>();
            }
            TrieNode childNode = new TrieNode();
            edges.put(c, childNode);
            return childNode;
        }

        TrieNode traverse(char c) {
            return (edges == null) ? null : edges.get(c);
        }

        TrieNode deleteEdge(char c) {
            return (edges == null) ? null : edges.remove(c);
        }

        Iterator<TrieNode>  getChildren() {
            return (edges == null) ? null : edges.values().iterator();
        }

        void setKey(String key) {
            this.key = key;

        }

        String getKey() {
            return key;
        }

        public int getChildrenCnt() {
            return edges == null ? 0 : edges.size();
        }
    }
    public static void main(String[] args) {
    	Gson gson = new Gson();
    	Trie trie = new Trie();
    	trie.insert("hello");
    	trie.insert("world");
    	String trieJson = gson.toJson(trie);
    	Trie t = gson.fromJson(trieJson, Trie.class);
    	System.out.println(t.search("he"));
    }
    
    public static void main1(String[] args) {
        Trie t = new Trie();
        t.insert("vino");
        t.insert("vinod");
        t.insert("vin");
        t.insert("jyo");
        t.insert("jyotsna");
        t.insert("jyot");
        t.insert("jyots");
        t.insert("jyotsn");
        t.insert("joe");

        System.out.println(t.search("vino"));
        System.out.println(t.search("j"));
        System.out.println(t.search("jy"));
        System.out.println(t.search("joe"));

        System.out.println(t.search("bhalblah"));
    }
}