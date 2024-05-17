import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class TFIDFCalculator {
    public static void main (String args[]) {
        // DocsReader dr = new DocsReader(args[0]);
        DocsReader dr = new DocsReader("/home/share/hw4/docs.txt");
        
        ArrayList<Trie> wordsInDoc = new ArrayList<>();
        Trie numsOfDocsHasTerm = new Trie();
        for (String s : dr.getDocs()) {
            // s is 5 line doc
            Trie t = new Trie(s);
            wordsInDoc.add(t);
            // if (t.root != null) System.out.println("t is null");
            numsOfDocsHasTerm.insertToAnother(numsOfDocsHasTerm.root, t.root);
        }

        try {
            // File f = new File(args[1]);
            File f = new File("tc0.txt");
            Scanner scanner = new Scanner(f);
            String terms[] = scanner.nextLine().split(" ");
            String temp[] = scanner.nextLine().split(" ");
            int nums[] = Arrays.stream(temp).mapToInt(Integer::parseInt).toArray();
            scanner.close();

            StringBuilder sb = new StringBuilder();
            for (int i = 0;i < terms.length;i++ ) {
                System.out.println(tfIdfCalculate(wordsInDoc.get(nums[i]), numsOfDocsHasTerm, terms[i], dr.getDocs().size()));
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static double tf(Trie doc, String term) {
        return doc.search(term) / doc.getSize();
    }

    public static double idf(Trie docs, String term, int size) {
        return Math.log(size / docs.search(term));
    }
    
    public static double tfIdfCalculate(Trie doc, Trie docs, String term, int size) {
      return tf(doc, term) * idf(docs, term, size);
    }
}
