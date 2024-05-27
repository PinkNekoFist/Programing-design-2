import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Indexer implements Serializable {
    private ArrayList<Trie> wordsInDoc;
    private Trie numsOfDocsHasTerm;

    public Indexer(Path p) throws IOException {
        String file = new String(Files.readString(p));
        DocsReader dr = new DocsReader(file);
        wordsInDoc = new ArrayList<>();
        numsOfDocsHasTerm = new Trie();

        for (String s : dr.getDocs()) {
            Trie t = new Trie(s);
            wordsInDoc.add(t);
            numsOfDocsHasTerm.merge(numsOfDocsHasTerm.root, t.root);
        }
    }
}

class TrieNode {
    TrieNode[] children = new TrieNode[26];
    int numOfword = 0;
}

class Trie {
    TrieNode root = new TrieNode();
    int size = 0;

    Trie() {
    }

    Trie(String s) {
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

    // idf 2
    public void merge(TrieNode r, TrieNode t) {
        if (t == null)
            return;
        for (int i = 0; i < 26; i++) {
            if (t.children[i] == null)
                continue;
            if (r.children[i] == null) {
                r.children[i] = new TrieNode();
            }
            if (t.children[i].numOfword > 0) {
                r.children[i].numOfword++;
            }
        }
        for (int i = 0; i < 26; i++) {
            merge(r.children[i], t.children[i]);
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

class DocsReader {
    private ArrayList<String> docs;

    public DocsReader(String path) {
        docs = new ArrayList<String>();
        String file = "";

        try {
            file = new String(Files.readString(Paths.get(path)));
        } catch (IOException err) {
            System.out.println("can't open file");
            System.err.println(err);
        }

        int numOfn = 0;
        char last = ' ';
        StringBuilder sb = new StringBuilder();
        for (char c : file.toCharArray()) {
            if (c == '\n')
                numOfn++;
            if (numOfn >= 5) {
                numOfn = 0;

                if (last != ' ')
                    sb.append(' ');
                last = ' ';

                docs.add(sb.toString());
                sb = new StringBuilder();
            } else {
                // filter the char;
                c = charPaser(c);
                if (c == ' ' && last != ' ') {
                    last = c;
                    sb.append(c);
                } else if (c != ' ') {
                    last = c;
                    sb.append(c);
                }
            }
        }
        docs.add(sb.toString());
    }

    private char charPaser(char c) {
        int ascii = c;
        if (ascii >= 65 && ascii <= 90) {
            // is upper case
            return (char) (ascii + 32);
        } else if (ascii >= 97 && ascii <= 122) {
            // is lower case
            return c;
        }
        // System.out.println("is not upper or lower case");
        return ' ';
    }

    public ArrayList<String> getDocs() {
        return docs;
    }
}
