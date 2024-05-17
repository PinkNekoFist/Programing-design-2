import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class DocsReader {
    private ArrayList<String> docs;

    public DocsReader(String path){
        docs = new ArrayList<String>();
        String file = "";

        try {
            file = new String(Files.readString(Paths.get(path)));
        } catch (IOException err){
            System.out.println("can't open file");
            System.err.println(err);
        }

        int numOfn = 0;
        char last = ' ';
        StringBuilder sb = new StringBuilder();
        for (char c : file.toCharArray()) {
            if (c == '\n') numOfn++;
            if (numOfn >= 5) {
		        numOfn = 0;

                if (last != ' ') sb.append(' ');
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

    private char charPaser(char c){
        int ascii = c;
        if (ascii >= 65 && ascii <= 90) {
            // is upper case
	    // System.out.println("is upper case");
            return (char)(ascii+32);
        } else if (ascii >= 97 && ascii <= 122){
            // is lower case
	    // System.out.println("is lower case");
            return c;
        }
        // System.out.println("is not upper or lower case");
        return ' ';
    }

    public ArrayList<String> getDocs(){
        return docs;
    }
}
