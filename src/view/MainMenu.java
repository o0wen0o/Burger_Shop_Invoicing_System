package view;

import domain.Admin;
import domain.Client;
import domain.Dish;
import service.*;

/**
 * @author o0wen0o
 * @create 2023-02-17 3:36 PM
 */
public class MainMenu {

    private String identifyId; // current user id
    private Menu menu = new Menu();
    private AdminListService adminListService = new AdminListService();
    private ClientListService clientListService = new ClientListService();
    private DishOrderService dishOrderService = new DishOrderService(menu);
    private OrderListService orderListService = new OrderListService(dishOrderService);

    public void enterMainMenu() {
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
        System.out.print("Admin/Client ID: ");
        identifyId = Utility.readString(5).toUpperCase();
        System.out.print("Password: ");
        String pwd = Utility.readString(20);

        String menu = "None";

        for (Admin admin : adminListService.getAllAdmins()) {
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

        for (Client client : clientListService.getAllClients()) {
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
                    orderListService.createOrder(identifyId, menu, clientListService, dishOrderService);
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

    private void adminMenu() {
        char option;
        boolean isRun = true;

        while (isRun) {
            System.out.println("\n--------------------------------------");
            System.out.println("<< Welcome To Burger Shop System >>");
            System.out.println("--------------------------------------");
            System.out.println("(1) Update/Maintain Order");
            System.out.println("(2) Cancel Order");
            System.out.println("(3) View Billing Statement");
            System.out.println("(4) Update Menu");
            System.out.println("(5) Quit");
            System.out.println("--------------------------------------");

            System.out.print("Option >> ");
            option = Utility.readSelection(new char[]{'1', '2', '3', '4', '5'});

            switch (option) {
                case '1':
                    orderListService.updateOrder(menu, clientListService, dishOrderService);
                    break;

                case '2':
                    orderListService.cancelOrder(dishOrderService);
                    break;

                case '3':
                    orderListService.showOrderByID(clientListService, dishOrderService);
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

            String dishID;
            switch (option) {
                case '1':
                    menu.createDish();
                    break;

                case '2':
                    menu.updateDish();
                    break;

                case '3':
                    menu.deleteDish();
                    break;

                case '4':
                    System.out.print("Quit? (Y/N): ");
                    char exit = Utility.readConfirmSelection();
                    if (exit == 'Y') {
                        isRun = false;
                    }
                    break;
            }
            Utility.readReturn();
        }
    }

    public static void main(String[] args) {
        MainMenu mainMenu = new MainMenu();
        mainMenu.enterMainMenu();
    }
}
