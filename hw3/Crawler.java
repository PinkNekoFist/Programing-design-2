import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

public class Crawler {
    private StringBuilder head;
    private StringBuilder newPrice;
    private int day;

    public Crawler(String webName) {
        try {
            Document doc = Jsoup.connect(webName).get();
            System.out.println(doc.title());

            Elements company = doc.select("th");
            Elements price = doc.select("td");
            String dayString = new String(doc.title());
            day = Integer.parseInt(dayString.substring(3));
            head = new StringBuilder();
            System.out.println(company);
            for (Element name : company) {
                head.append(name.text());
                head.append(",");
            }
            head.setCharAt(head.length() - 1, '\n');
            // System.out.println(head);

            newPrice = new StringBuilder();
            for (Element pric : price) {
                newPrice.append(pric.text());
                newPrice.append(",");
            }
            newPrice.setCharAt(newPrice.length() - 1, '\n');
            // System.out.println(newPrice);

        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    public int getDay() {
        return day;
    }

    public void makeNewFile(String fileName) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
            bw.write("");
            bw.close();
        } catch (IOException err) {
            System.out.println("bruh, make new file fail");
            err.printStackTrace();
        }
        return;
    }

    public void addNewHead(String fileName) {
        try {
            File data = new File(fileName);
            FileWriter fw = new FileWriter(data, true);
            fw.append(head);
            fw.close();
        } catch (Exception err) {
            System.out.println("bruh, fail");
            err.printStackTrace();
        }
        return;
    }

    public void addNewLine(String fileName) {
        try {
            File data = new File(fileName);
            FileWriter fw = new FileWriter(data, true);
            fw.append(newPrice);
            fw.close();
        } catch (Exception err) {
            System.out.println("bruh, fail");
            err.printStackTrace();
        }
        return;
    }
}
