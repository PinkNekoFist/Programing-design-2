import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VarPaser {
    private String ass;
    private String name;
    private String type;
    private String c;

    Pattern pattern = Pattern.compile("(\\w+) *: *(\\+|\\-) *(\\w+\\[*\\]*) +(\\w+)");

    public VarPaser(String line) {
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            if (matcher.group(2).equals("+")) {
                ass = "public";
            } else {
                ass = "private";
            }
            c = matcher.group(1);
            type = matcher.group(3);
            name = matcher.group(4);
        } else {
            System.out.println("not found :(");
        }
    }

    public String result() {
        return ("    " + ass + " " + type + " " + name + ";");
    }

    public String getC() {
        return c;
    }
}
