package view;

import domain.Admin;
import domain.Dish;
import domain.User;
import service.DishOrderService;

import java.io.*;
import java.util.*;

/**
 * @author o0wen0o
 * @create 2023-02-16 8:27 PM
 */
public class Utility {
    private static Scanner scanner = new Scanner(System.in);

    public static ArrayList<String> readFile(String srcPath) {
        ArrayList<String> elements = new ArrayList<>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("src/service/data/" + srcPath));

            String line;
            while ((line = br.readLine()) != null) {
                elements.addAll(Arrays.asList(line.split(",")));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return elements;
    }

    // obj is list or map
    public static void saveFile(String srcPath, String str) {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter("src/service/data/" + srcPath, false));

            bw.write(str);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // limit the input
    public static char readSelection(char[] conditions) {
        char c = 0;
        boolean isValid = false;

        while (true) {
            String str = readKeyBoard(1, false).toUpperCase();
            c = str.charAt(0);

            for (char condition : conditions) {
                if (c == condition) {
                    isValid = true;
                    break;
                }
            }

            if (isValid) {
                break;
            }

            System.out.print("Invalid Input! Please Try Again: ");
        }
        return c;
    }

    // read integer which not greater than 6
    public static int readInt() {
        int n;
        for (; ; ) {
            String str = readKeyBoard(6, false);
            try {
                n = Integer.parseInt(str);
                break;
            } catch (NumberFormatException e) {
                System.out.print("Invalid Input! Please Try Again: ");
            }
        }
        return n;
    }

    // read a string which not greater than the limit
    public static String readString(int limit) {
        return readKeyBoard(limit, false);
    }

    // read 'Y' or 'N' only
    public static char readConfirmSelection() {
        char c;
        for (; ; ) {
            String str = readKeyBoard(1, false).toUpperCase();
            c = str.charAt(0);
            if (c == 'Y' || c == 'N') {
                break;
            } else {
                System.out.print("Invalid Input! Please Try Again: ");
            }
        }
        return c;
    }

    // The program will continue to run after the user press enter
    public static void readReturn() {
        System.out.print("Press Enter To Continue...... ");
        readKeyBoard(100, true);
        System.out.println();
    }

    private static String readKeyBoard(int limit, boolean blankReturn) {
        String line = "";

        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            if (line.length() == 0) {
                if (blankReturn)
                    return line;
                else {
                    System.out.print("Invalid Input! Please Try Again: ");
                    continue;
                }
            }

            if (line.length() < 1 || line.length() > limit) {
                System.out.print("Input exceed " + limit + ". Please Try Again: ");
                continue;
            }
            break;
        }
        return line;
    }
}
