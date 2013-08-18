package org.vidad.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.vidad.tools.conf.Collection;


public class Trie extends Collectionable<Trie> {

	
	@Override
	public Collection getCollection() {
		return Collection.TRIE;
	}

    private 	Map<String, Node> roots = new HashMap<>();
    Collection 	collection;
    ObjectId 	rootId;
    String		name;
    
    public Trie(Collection 	collection, ObjectId rootId, String name) {
		super();
		this.collection = collection;
		this.rootId = rootId;
		this.name=name;
	}

    public Trie(Collection 	collection, ObjectId rootId, String name, List<String> argInitialWords) {
    	this(collection, rootId, name);
    	construct(argInitialWords);
    }
    
    public void construct(List<String> argInitialWords){
        for (String word:argInitialWords)
            addWord(word);    	
    }
    
    public void addWord(String argWord) {
            addWord(argWord.toLowerCase().toCharArray());
    }

    private void addWord(char[] argWord) {
            Node currentNode = null;

            if (!roots.containsKey(Character.toString(argWord[0]))) {
                    roots.put(Character.toString(argWord[0]), new Node(argWord[0], "" + argWord[0]));
            }

            currentNode = roots.get(Character.toString(argWord[0]));

            for (int i = 1; i < argWord.length; i++) {
                    if (currentNode.getChild(argWord[i]) == null) {
                            currentNode.addChild(new Node(argWord[i], currentNode.getValue() + argWord[i]));
                    }

                    currentNode = currentNode.getChild(argWord[i]);
            }

            currentNode.setIsWord(true);
    }

    public boolean containsPrefix(String argPrefix) {
            return contains(argPrefix.toCharArray(), false);
    }

    public boolean containsWord(String argWord) {
            return contains(argWord.toCharArray(), true);
    }

    public Node getWord(String argString) {
            Node node = getNode(argString.toCharArray());
            return node != null && node.isWord() ? node : null;
    }

    public Node getPrefix(String argString) {
            return getNode(argString.toCharArray());
    }

    @Override
    public String toString() {
            return roots.toString();
    }

    private boolean contains(char[] argString, boolean argIsWord) {
            Node node = getNode(argString);
            return (node != null && node.isWord() && argIsWord) || (!argIsWord && node != null);
    }

    private Node getNode(char[] argString) {
            Node currentNode = roots.get(Character.toString(argString[0]));

            for (int i = 1; i < argString.length && currentNode != null; i++) {
                    currentNode = currentNode.getChild(argString[i]);

                    if (currentNode == null) {
                            return null;
                    }
            }

            return currentNode;
    }

    public class Node {
    	 
        private final Character ch;
        private final String value;
        private Map<String, Node> children = new HashMap<>();
        private boolean isValidWord;
 
        public Node(char argChar, String argValue) {
                ch = argChar;
                value = argValue;
        }
 
        public boolean addChild(Node argChild) {
                if (children.containsKey(Character.toString(argChild.getChar()))) {
                        return false;
                }
 
                children.put(Character.toString(argChild.getChar()), argChild);
                return true;
        }
 
        public boolean containsChildValue(char c) {
                return children.containsKey(Character.toString(c));
        }
 
        public String getValue() {
                return value.toString();
        }
 
        public char getChar() {
                return ch;
        }
 
        public Node getChild(char c) {
                return children.get(Character.toString(c));
        }
 
        public boolean isWord() {
                return isValidWord;
        }
 
        public void setIsWord(boolean argIsWord) {
                isValidWord = argIsWord;
 
        }
 
        public String toString() {
                return value;
        }
 
}


}
