package view;

import domain.Admin;
import domain.Client;
import domain.Dish;
import domain.Order;
import service.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author o0wen0o
 * @create 2023-02-17 3:36 PM
 */
public class MainMenu {

    private String identifyId; // current user id
    private final Service<Admin> adminListService = new AdminListService();
    private final Service<Client> clientListService = new ClientListService();
    private final OrderService<Dish> menu = new Menu();
    private final OrderService<Order> orderListService = new OrderListService(menu);
    private final Map<String, Object> data = new HashMap<>();

    public static void main(String[] args) {
        MainMenu mainMenu = new MainMenu();
        mainMenu.enterMainMenu();
    }

    public void enterMainMenu() {
        data.put("clientID", identifyId);
        data.put("menu", menu);
        data.put("clientListService", clientListService);

        char option;
        boolean isRun = true;

        while (isRun) {
            System.out.println("--------------------------------------");
            System.out.println("<< Welcome To Burger Shop System >>");
            System.out.println("--------------------------------------");
            System.out.println("(1) Login");
            System.out.println("(2) Register");
            System.out.println("(3) Quit");
            System.out.println("--------------------------------------");

            System.out.print("Option >> ");
            option = Utility.readSelection(new char[]{'1', '2', '3'});

            switch (option) {
                case '1':
                    login();
                    break;

                case '2':
                    ((ClientListService) clientListService).createClientProfile();
                    break;

                case '3':
                    System.out.print("End the program? (Y/N): ");
                    char exit = Utility.readConfirmSelection();
                    if (exit == 'Y') {
                        isRun = false;
                        System.out.print("Thank You For Using The System.\n");
                    }
                    break;
            }
        }
    }

    private void login() {
        System.out.print("--------------------------------------\n");

        System.out.print("Admin/Client ID (Ex.A0001/C0001): ");
        identifyId = Utility.readString(5).toUpperCase();
        data.put("clientID", identifyId);

        System.out.print("Password: ");
        String pwd = Utility.readString(20);

        String menu = "None";

        for (Admin admin : adminListService.getList()) {
            if (admin.getUserID().equals(identifyId) && admin.getPassword().equals(pwd)) {
                menu = "Admin";
                break;
            }
        }

        // if the user is admin
        if ("Admin".equals(menu)) {
            adminMenu();
            return;
        }

        for (Client client : clientListService.getList()) {
            if (client.getUserID().equals(identifyId) && client.getPassword().equals(pwd)) {
                menu = "Client";
                break;
            }
        }

        // if the user is customer
        if ("Client".equals(menu)) {
            clientMenu();
            return;
        }

        // if user not found
        System.out.println("--------------------------------------");
        System.out.print("ID And Password Incorrect. Please Try Again.\n");
        Utility.readReturn();
    }

    private void adminMenu() {
        char option;
        boolean isRun = true;

        while (isRun) {
            System.out.println("\n--------------------------------------");
            System.out.println("<< Welcome To Burger Shop System >>");
            System.out.println("--------------------------------------");
            System.out.println("(1) Update Order");
            System.out.println("(2) Cancel Order");
            System.out.println("(3) View Billing Statement");
            System.out.println("(4) Update Menu");
            System.out.println("(5) Quit");
            System.out.println("--------------------------------------");

            System.out.print("Option >> ");
            option = Utility.readSelection(new char[]{'1', '2', '3', '4', '5'});

            switch (option) {
                case '1':
                    orderListService.update(data);
                    break;

                case '2':
                    orderListService.delete();
                    break;

                case '3':
                    System.out.print("Please enter order ID (Ex.O0001): ");
                    String orderID = Utility.readString(5).toUpperCase();

                    ((OrderListService) orderListService).showOrderByID(orderID, clientListService);
                    break;

                case '4':
                    updateMenu();
                    break;

                case '5':
                    System.out.print("Logout account? (Y/N): ");
                    char exit = Utility.readConfirmSelection();
                    if (exit == 'Y') {
                        isRun = false;
                        System.out.print("Thank You For Using The System.\n");
                    }
                    break;
            }
            Utility.readReturn();
        }
    }

    private void clientMenu() {
        char option;
        boolean isRun = true;

        while (isRun) {
            System.out.println("\n--------------------------------------");
            System.out.println("<< Welcome To Burger Shop System >>");
            System.out.println("--------------------------------------");
            System.out.println("(1) Create Order");
            System.out.println("(2) Quit");
            System.out.println("--------------------------------------");

            System.out.print("Option >> ");
            option = Utility.readSelection(new char[]{'1', '2'});

            switch (option) {
                case '1':
                    orderListService.create(data);
                    break;

                case '2':
                    System.out.print("Logout account? (Y/N): ");
                    char exit = Utility.readConfirmSelection();
                    if (exit == 'Y') {
                        isRun = false;
                        System.out.print("Thank You For Using The System.\n");
                    }
                    break;
            }
            Utility.readReturn();
        }
    }

    public void updateMenu() {
        char option;
        boolean isRun = true;

        while (isRun) {
            System.out.println("\n--------------------------------------");
            System.out.println("(1) Create Dish");
            System.out.println("(2) Update Dish");
            System.out.println("(3) Delete Dish");
            System.out.println("(4) Quit");
            System.out.println("--------------------------------------");

            System.out.print("Option >> ");
            option = Utility.readSelection(new char[]{'1', '2', '3', '4'});

            switch (option) {
                case '1':
                    menu.create(data);
                    break;

                case '2':
                    menu.update(data);
                    break;

                case '3':
                    menu.delete();
                    break;

                case '4':
                    System.out.print("Quit? (Y/N): ");
                    char exit = Utility.readConfirmSelection();
                    if (exit == 'Y') {
                        isRun = false;
                    }
                    break;
            }
        }
    }
}
