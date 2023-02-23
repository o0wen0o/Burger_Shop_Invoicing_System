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
            option = Utility.readMenuSelection();

            switch (option) {
                case '1':
                    System.out.print("--------------------------------------\n");
                    System.out.print("ID: ");
                    identifyId = Utility.readString(5);
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
                        break;
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
                        break;
                    }

                    // if user not found
                    if ("None".equals(menu)) {
                        System.out.println("--------------------------------------");
                        System.out.print("ID And Password Incorrect. Please Try Again.\n");
                        Utility.readReturn();
                    }
                    break;

                case '2':
                    System.out.print("\nPlease enter your information.[Customer Only]\n");// login and register
                    clientListService.createClientProfile();
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

    public void adminMenu() {
        char option;
        boolean isRun = true;

        while (isRun) {
            System.out.println("\n--------------------------------------");
            System.out.println("<< Welcome To Burger Shop System >>");
            System.out.println("--------------------------------------");
            System.out.println("(1) Update/Maintain Orders");
            System.out.println("(2) Cancel Order");
            System.out.println("(3) View Billing Statements");
            System.out.println("(4) Search Dish");
            System.out.println("(5) Create Admin Profile");
            System.out.println("(6) Quit");
            System.out.println("--------------------------------------");

            System.out.print("Option >> ");
            option = Utility.readAdminMenuSelection();

            switch (option) {
                case '1':
                    break;

                case '2':
                    break;

                case '3':
                    System.out.print("Please enter order ID: ");
                    String orderID = Utility.readString(5);
                    orderListService.showOrderByID(orderID, clientListService, dishOrderService);
                    break;

                case '4':
                    System.out.print("Please enter dish ID: ");
                    String dishID = Utility.readString(3);

                    if (!menu.isExist(dishID)) {
                        System.out.println("Dish does not exist. Please try again.");
                        break;
                    }

                    Dish dish = menu.getDishByID(dishID);
                    menu.showDish(dish);
                    break;

                case '5':
                    adminListService.createAdminProfile();
                    break;

                case '6':
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

    public void clientMenu() {
        char option;
        boolean isRun = true;

        while (isRun) {
            System.out.println("\n--------------------------------------");
            System.out.println("<< Welcome To Burger Shop System >>");
            System.out.println("--------------------------------------");
            System.out.println("(1) Create Order");
            System.out.println("(2) View Billing Statements");
            System.out.println("(3) Quit");
            System.out.println("--------------------------------------");

            System.out.print("Option >> ");
            option = Utility.readMenuSelection();

            switch (option) {
                case '1':
                    menu.showMenu();
                    orderListService.createOrder(identifyId, menu, clientListService, dishOrderService);
                    break;

                case '2':
                    System.out.print("Please enter order ID: ");
                    String orderID = Utility.readString(5);
                    orderListService.showOrderByID(orderID, clientListService, dishOrderService);
                    break;

                case '3':
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

    public static void main(String[] args) {
        MainMenu mainMenu = new MainMenu();
        mainMenu.enterMainMenu();
    }
}
