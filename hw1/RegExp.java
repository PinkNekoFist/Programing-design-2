
// https://chuangkt.notion.site/PD2-Homework-1-3fce92cd9b504721bdefc0d214e9bad0
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class RegExp {

    public static void main(String[] args) {
        String str1 = args[1];
        String str2 = args[2];
        int s2Count = Integer.parseInt(args[3]);

        // For your testing of input correctness
        /*
         * System.out.println("The input file:" + args[0]);
         * System.out.println("str1=" + str1);
         * System.out.println("str2=" + str2);
         * System.out.println("num of repeated requests of str2 = " + s2Count);
         */

        try {
            BufferedReader reader = new BufferedReader(new FileReader(args[0]));
            String line;
            while ((line = reader.readLine()) != null) {
                // You main code should be invoked here
                line = line.toLowerCase();
                // System.out.println(line);
                int l = line.length();
                char b[] = { 'N', 'N', 'N', 'N' };
                b[0] = solve1(line, l);
                b[1] = solve2(line, str1, l, str1.length());
                b[2] = solve3(line, str2, l, str2.length(), s2Count);
                b[3] = solve4(line, l);
                System.out.printf("%c,%c,%c,%c\n", b[0], b[1], b[2], b[3]);
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static char solve1(String s, int l) {
        for (int i = 0; i < l / 2; i++) {
            if (s.charAt(i) != s.charAt(l - 1 - i))
                return 'N';
        }
        return 'Y';
    }

    public static char solve2(String str1, String str2, int l1, int l2) {
        for (int i = 0; i <= l1 - l2; i++) {
            if (str1.charAt(i) == str2.charAt(0)) {
                boolean isSubstr = true;
                for (int j = 1; j + i < l1 && j < l2; j++) {
                    if (str1.charAt(i + j) != str2.charAt(j)) {
                        isSubstr = false;
                        break;
                    }
                }
                if (isSubstr)
                    return 'Y';
            }
        }
        return 'N';
    }

    public static char solve3(String str1, String str2, int l1, int l2, int n) {
        for (int i = 0; i <= l1 - l2; i++) {
            if (str1.charAt(i) == str2.charAt(0)) {
                boolean isSubstr = true;
                for (int j = 1; j + i < l1 && j < l2; j++) {
                    if (str1.charAt(i + j) != str2.charAt(j)) {
                        isSubstr = false;
                        break;
                    }
                }
                if (isSubstr)
                    n--;
            }
        }
        // System.out.println(n);
        if (n <= 0)
            return 'Y';
        return 'N';
    }

    public static char solve4(String str, int l) {
        for (int i = 0; i < l - 2; i++) {
            if (str.charAt(i) == 'a') {
                for (int j = i + 1; j < l - 1; j++) {
                    if (str.charAt(j) == 'b' && str.charAt(j + 1) == 'b')
                        return 'Y';
                }
            }
        }
        return 'N';
    }
}
