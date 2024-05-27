import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Paths;

public class BuildIndex {
    public static void main(String args[]) throws IOException {
        File f = new File(args[0]);
        Indexer idx = new Indexer(Paths.get(args[0]));
        try {
            String fileName = f.getName();
            String temp[] = fileName.split(".");
            FileOutputStream fos = new FileOutputStream((temp[0] + ".ser"));
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(idx);
            
            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();	
        }
    }
}