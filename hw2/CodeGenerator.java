import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class CodeGenerator {
    public static void main(String[] args) {
        // open file
        if (args.length == 0) {
            System.err.println("please input file name");
            return;
        }
        String fileName = args[0];
        System.out.println("File name: " + fileName);
        String mermaidCode = "";
        try {
            mermaidCode = Files.readString(Paths.get(fileName));
        } catch (IOException e) {
            System.err.println("cant read file" + fileName);
            e.printStackTrace();
            return;
        }

        // preprocess file
        String[] lines = mermaidCode.split("\n");

        // class , content
        Map<String, List<String>> mp = new HashMap<>();
        String defaultClass = "";

        for (int i = 1; i < lines.length; i++) {
            // delete space
            lines[i] = lines[i].trim();
            // skip empty line
            if (lines[i].length() == 0 || lines[i].length() == 1) {
                System.out.println(lines[i]);
                continue;
            }

            if (lines[i].startsWith("class")) {
                // is a class
                String[] temp = lines[i].split(" +");
                defaultClass = new String(temp[1]);
                if (mp.containsKey(defaultClass))
                    continue;
                mp.put(temp[1], new ArrayList<>());
                mp.get(temp[1]).add("public class " + temp[1] + " {");
            } else {
                // is a fun or val
                if (lines[i].startsWith("+") || lines[i].startsWith("-")) {
                    // challange point
                    if (lines[i].contains("(")) {
                        // is a fun
                        lines[i] = (defaultClass + " : " + lines[i]);
                        FunPaser fnPaser = new FunPaser(lines[i]);
                        mp.get(fnPaser.getC()).add(fnPaser.result());
                    } else {
                        // is a val
                        lines[i] = (defaultClass + " : " + lines[i]);
                        VarPaser vPaser = new VarPaser(lines[i]);
                        mp.get(vPaser.getC()).add(vPaser.result());
                    }
                } else if (lines[i].contains("(")) {
                    // is a fun
                    FunPaser fnPaser = new FunPaser(lines[i]);
                    mp.get(fnPaser.getC()).add(fnPaser.result());
                } else {
                    // is a val
                    VarPaser vPaser = new VarPaser(lines[i]);
                    mp.get(vPaser.getC()).add(vPaser.result());
                }
            }
        }
        for (Map.Entry<String, List<String>> cl : mp.entrySet()) {
            String content = "";
            for (String s : cl.getValue()) {
                content = content.concat(s + "\n");
            }
            content = content.concat("}");
            makeOutPutFile(cl.getKey(), content);
        }
    }

    public static void makeOutPutFile(String output, String content) {
        output = output.concat(".java");
        try {
            File file = new File(output);
            if (!file.exists()) {
                file.createNewFile();
            }
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                bw.write(content);
            }
            System.out.println("Java class has been generated: " + output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
