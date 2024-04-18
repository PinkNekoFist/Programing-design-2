import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class Output {
    // copy data.csv
    private StringBuilder contain;

    public Output() {
        contain = new StringBuilder();
    }

    public void task0() {
        try {
            contain.append(Files.readString(Paths.get("data.csv")));
        } catch (IOException e) {
            System.err.println("cant open data.csv");
            e.printStackTrace();
            return;
        }
        outPut();
    }

    // Output the 5(n)-day moving average of the specified stock price during the
    // specified time period to output.csv
    public void task1(int start, int end, String name, DataModel data) {
        int n = 5;
        // add head
        contain.append(name + "," + String.valueOf(start) + "," + String.valueOf(end) + "\n");
        for (int i = start; i <= end - n + 1; i++) {
            double avg = data.averageOfStock(i, i + n - 1, name);
            String s = roundAndremoveZero(avg);
            contain.append(s + ",");
        }
        contain.setCharAt(contain.length() - 1, '\n');
        outPut();
    }

    // Output the standard deviation of the specified stock price during the
    // specified time period to output.csv
    public void task2(int start, int end, String name, DataModel dataModel) {
        contain.append(name + "," + String.valueOf(start) + "," + String.valueOf(end) + "\n");
        double ans = dataModel.standardDeviation(start, end, name);
        String s = roundAndremoveZero(ans);
        contain.append(s + ",");
        contain.setCharAt(contain.length() - 1, '\n');
        outPut();
    }

    // Output the top-3 stocks with the highest standard deviation in a specified
    // time period to output.csv
    public void task3(int start, int end, DataModel dataModel) {
        ArrayList<String> companyName = dataModel.getCompanys();
        TreeMap<Float, String> treemap = new TreeMap<>(Collections.reverseOrder());
        for (String name : companyName) {
            float standardDeviation = dataModel.standardDeviation(start, end, name);
            treemap.put(standardDeviation, name);
        }

        String firstLine = new String();
        StringBuilder secondLine = new StringBuilder();
        int i = 0;
        for (Map.Entry<Float, String> entry : treemap.entrySet()) {
            if (i >= 3) {
                break;
            }
            i++;
            firstLine = firstLine.concat(entry.getValue() + ",");
            String temp = roundAndremoveZero(entry.getKey());
            secondLine.append(temp + ",");
        }
        firstLine = firstLine.concat(String.valueOf(start) + "," + String.valueOf(end) + "\n");
        secondLine.setCharAt(secondLine.length() - 1, '\n');
        contain.append(firstLine);
        contain.append(secondLine);
        outPut();
    }

    public void task4(int start, int end, String name, DataModel dataModel) {
        float slope = dataModel.slope(start, end, name);
        float intercept = dataModel.intercept(start, end, slope, name);
        contain.append(name + "," + String.valueOf(start) + "," + String.valueOf(end) + "\n");
        String slopeString = roundAndremoveZero(slope);
        String interceptString = roundAndremoveZero(intercept);
        contain.append(slopeString + "," + interceptString + "\n");
        outPut();
    }

    public String roundAndremoveZero(double d) {
        d = (Math.round(d * 100.0) / 100.0);
        BigDecimal b1 = new BigDecimal(String.valueOf(d));
        return b1.stripTrailingZeros().toString();
    }

    public void outPut() {
        try {
            File file = new File("output.csv");
            if (file.createNewFile()) {
                FileWriter fw = new FileWriter(file, true);
                fw.append((contain.toString()));
                fw.close();
            } else {
                FileWriter fw = new FileWriter(file, true);
                fw.append((contain.toString()));
                fw.close();
            }
        } catch (IOException err) {
            err.printStackTrace();
        }
    }
}
