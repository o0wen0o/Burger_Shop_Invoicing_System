package view;

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
            br = new BufferedReader(new FileReader("src/service/" + srcPath));

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

    // public static void saveFile(String srcPath, List list) {
    //     BufferedWriter bw = null;
    //     try {
    //         bw = new BufferedWriter(new FileWriter("src/assignment/service/" + srcPath, false));
    //
    //         String str = "";
    //         for (Object obj : list) {
    //             switch (srcPath) {
    //                 case "CustomerData.txt":
    //                 case "AdminData.txt":
    //                     User u = (User) obj;
    //                     str = u.getId() + "," + u.getUsername() + "," + u.getPassword() + "," + u.getAge() + ","
    //                             + u.getPhoneNumber() + "," + u.getEmail() + "," + u.getAddress();
    //
    //                     if ("CustomerData.txt".equals(srcPath)) {
    //                         Customer customer = (Customer) obj;
    //                         str += "," + customer.getBikeId();
    //
    //                     } else {
    //                         Admin admin = (Admin) obj;
    //                         str += "," + admin.getSalary();
    //                     }
    //                     break;
    //
    //                 case "BicycleData.txt":
    //                     Bicycle b = (Bicycle) obj;
    //                     str = b.getId() + "," + b.getBikeType() + "," + b.getStatus() + "," + b.getBikeAge() + ","
    //                             + b.getWheelDiameter() + "," + b.getRentPerHour();
    //                     break;
    //
    //                 case "RentalDescriptionData.txt":
    //                     RentalDescription rd = (RentalDescription) obj;
    //                     str = rd.getBikeId() + "," + rd.getRentalTime() + "," + rd.getTotalRent();
    //                     break;
    //             }
    //
    //             bw.write(str);
    //             bw.newLine();
    //         }
    //
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     } finally {
    //         if (bw != null) {
    //             try {
    //                 bw.close();
    //             } catch (IOException e) {
    //                 e.printStackTrace();
    //             }
    //         }
    //     }
    // }

    // limit the input as 1-5, Q or q
    public static char readMenuSelection() {
        char c;
        for (; ; ) {
            String str = readKeyBoard(1, false).toUpperCase();
            c = str.charAt(0);
            if (c != '1' && c != '2' && c != '3' && c != '4' && c != '5'&& c != '6'&& c != '7') {
                System.out.print("Invalid Input! Please Try Again: ");
            } else
                break;
        }
        return c;
    }

    // limit the input as A,R,S,U,Q
    public static char readAdminMenuSelection() {
        char c;
        for (; ; ) {
            String str = readKeyBoard(1, false).toUpperCase();
            c = str.charAt(0);
            if (c != 'A' && c != 'R' && c != 'S' && c != 'U' && c != 'Q') {
                System.out.print("Invalid Input! Please Try Again: ");
            } else
                break;
        }
        return c;
    }

    // read character
    public static char readChar() {
        String str = readKeyBoard(1, false);
        return str.charAt(0);
    }

    // read a character, return current value if user does not intend to change to
    // new value
    public static char readChar(char defaultValue) {
        String str = readKeyBoard(1, true);
        return (str.length() == 0) ? defaultValue : str.charAt(0);
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

    // read an integer, return current value if user does not intend to change to
    // new value
    public static int readInt(int defaultValue) {
        int n;
        for (; ; ) {
            String str = readKeyBoard(6, true);
            if (str.equals("")) {
                return defaultValue;
            }

            try {
                n = Integer.parseInt(str);
                break;
            } catch (NumberFormatException e) {
                System.out.print("Invalid Input! Please Try Again: ");
            }
        }
        return n;
    }

    // read double datatype value which not greater than 8
    public static double readDouble() {
        double n;
        for (; ; ) {
            String str = readKeyBoard(8, false);
            try {
                n = Double.parseDouble(str);
                break;
            } catch (NumberFormatException e) {
                System.out.print("Invalid Input! Please Try Again: ");
            }
        }
        return n;
    }

    // read a double, return current value if user does not intend to change to new
    // value
    public static double readDouble(double defaultValue) {
        double n;
        for (; ; ) {
            String str = readKeyBoard(8, true);
            if (str.equals("")) {
                return defaultValue;
            }

            try {
                n = Double.parseDouble(str);
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

    // read a character which not greater than the limit, return current value if
    // user does not intend to change to new value
    public static String readString(int limit, String defaultValue) {
        String str = readKeyBoard(limit, true);
        return str.equals("") ? defaultValue : str;
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
