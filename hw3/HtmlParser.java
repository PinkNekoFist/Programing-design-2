import java.io.IOException;
import java.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

public class HtmlParser {
    public static void main(String args[]) {
        if (args[0].equals("0")) {
            Crawler crawler = new Crawler("https://pd2-hw3.netdb.csie.ncku.edu.tw/");
            if (crawler.getDay() == 1) {
                crawler.makeNewFile("data.csv");
                crawler.addNewHead("data.csv");
            }
            crawler.addNewLine("data.csv");
        } else if (args[0].equals("1")) {
            DataModel dataModel = new DataModel("data.csv");
            Output output = new Output();
            if (args[1].equals("0")) {
                output.task0();
                return;
            }

            int start = Integer.valueOf(args[3]);
            int end = Integer.valueOf(args[4]);
            String name = args[2];
            if (args[1].equals("1")) {
                output.task1(start, end, name, dataModel);
            } else if (args[1].equals("2")) {
                output.task2(start, end, name, dataModel);
            } else if (args[1].equals("3")) {
                output.task3(start, end, dataModel);
            } else if (args[1].equals("4")) {
                output.task4(start, end, name, dataModel);
            }
        } else {
            // err
        }
    }
}