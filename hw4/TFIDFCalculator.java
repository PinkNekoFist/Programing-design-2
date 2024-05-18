import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class TFIDFCalculator {
    public static void main (String args[]) {
        DocsReader dr = new DocsReader(args[0]);
        
        ArrayList<Trie> wordsInDoc = new ArrayList<>();
        Trie numsOfDocsHasTerm = new Trie();
        for (String s : dr.getDocs()) {
            Trie t = new Trie(s);
            wordsInDoc.add(t);
            numsOfDocsHasTerm.merge(numsOfDocsHasTerm.root, t.root);
        }

        try {
            File f = new File(args[1]);
            Scanner scanner = new Scanner(f);
            String terms[] = scanner.nextLine().split(" ");
            String temp[] = scanner.nextLine().split(" ");
            int nums[] = Arrays.stream(temp).mapToInt(Integer::parseInt).toArray();
            scanner.close();

            StringBuilder sb = new StringBuilder();
            for (int i = 0;i < terms.length;i++ ) {
                double d = tfIdfCalculate(wordsInDoc.get(nums[i]), numsOfDocsHasTerm, terms[i], dr.getDocs().size());
                sb.append(String.format("%.5f", d) + " ");
            }
            File file = new File("output.txt");
            FileWriter fw = new FileWriter(file, false);
            fw.write(sb.toString());
            fw.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("new file fail");
            e.printStackTrace();
        }
    }

    public static double tf(Trie doc, String term) {
        return ((float)doc.search(term) / (float)doc.getSize());
    }

    public static double idf(Trie docs, String term, int size) {
        return Math.log((float)size / (float)docs.search(term));
    }
    
    public static double tfIdfCalculate(Trie doc, Trie docs, String term, int size) {
        return tf(doc, term) * idf(docs, term, size);
    }
}
