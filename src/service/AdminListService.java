package service;

import domain.Admin;
import view.Utility;

import java.util.ArrayList;
import java.util.List;

/**
 * @author o0wen0o
 * @create 2023-02-22 8:28 PM
 */
public class AdminListService implements Service<Admin> {
    private final List<Admin> adminList = new ArrayList<>();
    private final String srcPath = "AdminData.txt";

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

    @Override
    public void showList() {
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

    public List<Admin> getList() {
        return adminList;
    }

    public void saveFile() {
        StringBuilder str = new StringBuilder();

        for (Admin admin : adminList) {
            str.append(String.format("%s,%s,%s,%s\n", admin.getUserID(), admin.getUserName(), admin.getPassword(), admin.getPhoneNumber()));
        }

        Utility.saveFile(srcPath, str.toString());
    }
}
