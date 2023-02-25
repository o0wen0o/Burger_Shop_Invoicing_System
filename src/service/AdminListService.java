package service;

import domain.Admin;
import view.Utility;

import java.util.ArrayList;
import java.util.List;

/**
 * @author o0wen0o
 * @create 2023-02-22 8:28 PM
 */
public class AdminListService {
    private List<Admin> adminList = new ArrayList<>();
    private String srcPath = "AdminData.txt";

    public AdminListService() {
        List<String> elements = Utility.readFile(srcPath);

        for (int i = 0; i < elements.size(); i += 4) {
            String adminID = elements.get(i + 0);
            String adminName = elements.get(i + 1);
            String password = elements.get(i + 2);
            String phoneNumber = elements.get(i + 3);

            adminList.add(new Admin(adminID, adminName, password, phoneNumber));
        }
    }

    public void showAdminList() {
        System.out.println();
        System.out.println(String.format("%52s", " ").replace(' ', '-'));

        String str = String.format("|%-12s|%-20s|%-16s|", "Admin ID", "Admin Name", "Phone Number");
        System.out.println(str);

        System.out.println(String.format("%52s", " ").replace(' ', '-'));

        for (Admin admin : adminList) {
            System.out.println(admin);
        }

        System.out.println(String.format("%52s", " ").replace(' ', '-'));
        System.out.println();
    }

    public void createAdminProfile() {
        // not allow repeat same ID, no need input from user
        // get last order ID
        String lastId = adminList.get(adminList.size() - 1).getUserID();
        // get the number part of the ID and plus 1
        int newId = Integer.parseInt(lastId.substring(1)) + 1;
        // form a new ID
        String adminID = "A" + String.format("%04d", newId);
        System.out.println("Admin ID: " + adminID);

        System.out.print("Admin Name: ");
        String name = Utility.readString(20);

        System.out.print("Password: ");
        String password = Utility.readString(20);

        System.out.print("Phone number: ");
        String phoneNumber = Utility.readString(13);

        adminList.add(new Admin(adminID, name, password, phoneNumber));
        saveFile();
        System.out.println("\u001B[33mAdded Successfully.\u001B[0m");
    }

    public List<Admin> getAllAdmins() {
        return adminList;
    }


    private void saveFile() {
        String str = "";

        for (Admin admin : adminList) {
            str += String.format("%s,%s,%s,%s\n", admin.getUserID(), admin.getUserName(), admin.getPassword(), admin.getPhoneNumber());
        }

        Utility.saveFile(srcPath, str);
    }
}
