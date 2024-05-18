import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class TFIDFCalculator {
    public static void main (String args[]) {
        // TODO
        // DocsReader dr = new DocsReader(args[0]);
        DocsReader dr = new DocsReader("/home/share/hw4/docs.txt");
        
        ArrayList<Trie> wordsInDoc = new ArrayList<>();
        Trie numsOfDocsHasTerm = new Trie();
        for (String s : dr.getDocs()) {
            // s is 5 line doc
            Trie t = new Trie(s);
            wordsInDoc.add(t);
            // if (t.root != null) System.out.println("t is null");
            // TODO
            numsOfDocsHasTerm.merge2(numsOfDocsHasTerm.root, t.root);
        }

        try {
            // TODO
            // File f = new File(args[1]);
            File f = new File("tc0.txt");
            Scanner scanner = new Scanner(f);
            String terms[] = scanner.nextLine().split(" ");
            String temp[] = scanner.nextLine().split(" ");
            int nums[] = Arrays.stream(temp).mapToInt(Integer::parseInt).toArray();
            scanner.close();

            for (int i = 0;i < terms.length;i++ ) {
                double d = tfIdfCalculate(wordsInDoc.get(nums[i]), numsOfDocsHasTerm, terms[i], dr.getDocs().size());
                System.out.println(d);
                // output
                File file = new File("output.txt");
                if (file.createNewFile()) {
                    FileWriter fw = new FileWriter(file, true);
                    fw.write(String.format("%.5f", d) + " ");
                    fw.close();
                } else {
                    FileWriter fw = new FileWriter(file, true);
                    fw.write(String.format("%.5f", d) + " ");
                    fw.close();
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("new file fail");
            e.printStackTrace();
        }
    }

    public static double tf(Trie doc, String term) {
        System.out.println("tf = " + (float)doc.search(term) / (float)doc.getSize());
        return ((float)doc.search(term) / (float)doc.getSize());
    }

    public static double idf(Trie docs, String term, int size) {
        System.out.println("idf = " + Math.log((float)size / (float)docs.search(term)));
        return Math.log((float)size / (float)docs.search(term));
    }
    
    public static double tfIdfCalculate(Trie doc, Trie docs, String term, int size) {
        return tf(doc, term) * idf(docs, term, size);
    }
}
