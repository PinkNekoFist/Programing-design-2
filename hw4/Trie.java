class TrieNode {
    TrieNode[] children = new TrieNode[26];
    int numOfword = 0;
}

public class Trie {
    TrieNode root = new TrieNode();
    int size = 0;

    Trie () {}
    Trie (String s) {
        insert(s);
    }

    // insert a word to Trie
    public void insert(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            if (c == ' ') {
                node.numOfword++;
                size++;
                node = root;
                continue;
            } else if (node.children[c - 'a'] == null) {
                node.children[c - 'a'] = new TrieNode();
            }
            node = node.children[c - 'a'];
        }
    }

    // idf
    public void insertToAnother(TrieNode root, TrieNode t) {
        if (t == null) return;
        else if (root == null) {
            root = new TrieNode();
        }
        if (t.numOfword > 0) {
            root.numOfword++;
        }
        for (int i = 0;i < 26;i++) {
            insertToAnother(root.children[i], t.children[i]);
        }
        return;
    }

    // Search the word in Trie
    public int search(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            node = node.children[c - 'a'];
            if (node == null) {
                return 0;
            }
        }
        return node.numOfword;
    }

    public int getSize() {
        return size;
    }
}