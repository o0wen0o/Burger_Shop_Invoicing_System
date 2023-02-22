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
            String phoneNumber = elements.get(i + 2);

            adminList.add(new Admin(adminID, adminName, phoneNumber));
        }
    }

    public void showAdminList() {
        System.out.println();
        System.out.println(String.format("%62s", " ").replace(' ', '-'));

        String str = String.format("|%-12s|%-30s|%-16s|", "Admin ID", "Admin Name", "Phone Number");
        System.out.println(str);

        System.out.println(String.format("%62s", " ").replace(' ', '-'));

        for (Admin admin : adminList) {
            System.out.println(admin);
        }

        System.out.println(String.format("%62s", " ").replace(' ', '-'));
        System.out.println();
    }
}
