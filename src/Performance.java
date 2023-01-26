import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Performance {
    private static Scanner sc = new Scanner(System.in);

    public static String getID(String type, String check, String format) {
        String id;
        while (true) {
            System.out.println("Enter " + type + "'s ID " + check + " (X is a digit): ");
            id = sc.nextLine();
            if (!id.matches(format)) {
                System.out.println("Wrong format");
            } else {
                return id;
            }
        }
    }

    public static void writeListToFile(String fName, List<String> content) {
        try {
            File f = new File(fName);
            FileWriter fw = new FileWriter(f);
            PrintWriter pw = new PrintWriter(fw);

            for (String x : content) {
                pw.println(x);
            }
            pw.close();
            fw.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static List<String> readLineFromFile(String fName) {
        List listFile = new ArrayList<>();
        File f = new File(fName);
        if (f.exists()) {
            try {
                FileReader fr = new FileReader(f);
                BufferedReader br = new BufferedReader(fr);
                String line = br.readLine();
                while (line != null) {
                    line = line.trim();
                    if (!line.isEmpty()) {
                        listFile.add(line);
                    }
                }
                br.close();
                fr.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return listFile;
    }

}
