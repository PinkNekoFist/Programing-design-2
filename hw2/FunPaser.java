
// https://chuangkt.notion.site/PD2-Homework-2-925a35651a774200896af8c51bebc9a4
import java.util.regex.*;

class FunPaser {
    private String ass;
    private String name;
    private String argument;
    private String returnType;
    private String content;
    private String c;

    Pattern patten = Pattern.compile(" *(\\w+) *: *(\\+|\\-) *(\\w+) *\\( *([\\w ,]*) *\\) +(\\w+)");

    // class ass name argument returnType
    public FunPaser(String line) {
        // System.out.println(line);
        Matcher matcher = patten.matcher(line);
        if (matcher.find()) {
            c = matcher.group(1);
            if (matcher.group(2).equals("+")) {
                ass = "public";
            } else {
                ass = "private";
            }
            name = matcher.group(3);
            argument = matcher.group(4);
            argument.replaceAll(" {2,}", " ");

            returnType = matcher.group(5);
            if (name.startsWith("set")) {
                String s[] = argument.split(" ");
                content = ("{\n        this." + Character.toLowerCase(name.charAt(3)) + name.substring(4) + " = " + s[1]
                        + ";\n    }");
            } else if (name.startsWith("get")) {
                content = ("{\n        return " + Character.toLowerCase(name.charAt(3)) + name.substring(4)
                        + ";\n    }");
            } else if (returnType.equals("int")) {
                content = "{return 0;}";
            } else if (returnType.equals("String")) {
                content = "{\"\"}";
            } else if (returnType.equals("void")) {
                content = "{;}";
            } else if (returnType.equals("boolean")) {
                content = "{return false;}";
            }
        } else {
            System.out.println("not found :(");
        }
    }

    public String result() {
        return new String("    " + ass + " " + returnType + " " + name + "(" + argument + ") " + content);
    }

    public String getC() {
        System.out.println(c);
        return c;
    }
}
