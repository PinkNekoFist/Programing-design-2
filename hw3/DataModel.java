import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DataModel {
    private Map<String, Integer> company;
    private ArrayList<ArrayList<Float>> priceTable;
    private int numOfStock;

    public DataModel(String fileName) {
        String theFile = "";
        try {
            theFile = Files.readString(Paths.get(fileName));
        } catch (IOException e) {
            System.err.println("cant open " + fileName);
            e.printStackTrace();
            return;
        }

        String lines[] = theFile.split("\n");
        numOfStock = lines[0].split(",").length;

        // company number(ALL == 1)
        company = new HashMap<>();
        String companys[] = lines[0].split(",");
        for (int j = 0; j < numOfStock; j++) {
            company.put(companys[j], j);
        }

        // initialize priceTable
        priceTable = new ArrayList<>();
        for (int j = 0; j < numOfStock; j++) {
            priceTable.add(new ArrayList<Float>());
        }

        for (int j = 1; j < lines.length; j++) {
            String price[] = lines[j].split(",");
            for (int i = 0; i < numOfStock; i++) {
                priceTable.get(i).add(Float.valueOf(price[i]));
            }
        }
    }

    public int getNumOfStock() {
        return numOfStock;
    }

    public ArrayList<String> getCompanys() {
        ArrayList<String> arr = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : company.entrySet()) {
            arr.add(entry.getKey());
        }
        return arr;
    }

    // [start-1,end-1]
    public float standardDeviation(int start, int end, String name) {
        float avg = averageOfStock(start, end, name);
        ArrayList<Float> arr = new ArrayList<>();
        int num = company.get(name);
        for (int i = start - 1; i < end; i++) {
            float nums;
            nums = (priceTable.get(num).get(i) - avg) * (priceTable.get(num).get(i) - avg);
            arr.add(nums);
        }
        double temp = sumOfArrayList(arr);
        temp /= (end - start);
        return ((float) Math.sqrt(temp));
    }

    public float averageOfStock(int start, int end, String name) {
        return sumOfStock(start, end, name) / (end - start + 1);
    }

    public float sumOfStock(int start, int end, String name) {
        float ans = 0;
        int num = company.get(name);
        for (int i = start - 1; i < end; i++) {
            ans += priceTable.get(num).get(i);
        }
        return ans;
    }

    public float sumOfArrayList(ArrayList<Float> arr) {
        float ans = 0;
        for (float f : arr) {
            ans += f;
        }
        return ans;
    }

    public float slope(int start, int end, String name) {
        float avg = averageOfStock(start, end, name);
        float avgOfDay = (float)(start + end) * (float)(start - end + 1) / (2.0f * (start - end + 1));
        float upPart = 0;
        float downPart = 0;
        int num = company.get(name);
        for (int i = start - 1; i < end; i++ ) {
            downPart += (float)(i + 1 - avgOfDay) * (float)(i + 1 - avgOfDay);
            upPart += (i + 1 - avgOfDay) * (priceTable.get(num).get(i) - avg);
        }
        return (upPart/downPart);
    }

    public float intercept(int start, int end, float slope, String name) {
        float avgOfDay = (float)(start + end) * (float)(start - end + 1) / (2.0f * (start - end + 1));
        float avg = averageOfStock(start, end, name);
        return (avg - slope * avgOfDay);
    }
}