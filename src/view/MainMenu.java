package view;

import service.ClientListService;
import service.DishOrderService;
import service.Menu;
import service.OrderListService;

/**
 * @author o0wen0o
 * @create 2023-02-17 3:36 PM
 */
public class MainMenu {

    private Menu menu = new Menu();
    private ClientListService clientListService = new ClientListService();
    private DishOrderService dishOrderService = new DishOrderService(menu);
    private OrderListService orderListService = new OrderListService(dishOrderService);

    public void enterMainMenu() {
        char option;
        boolean isRun = true;

        while (isRun) {
            System.out.println("\n--------------------------------------");
            System.out.println("<< Welcome To Burger Shop System >>");
            System.out.println("--------------------------------------");
            System.out.println("(1)Create Order");
            System.out.println("(2)Update/Maintain Orders");
            System.out.println("(3)Cancel Order");
            System.out.println("(4)View Billing Statements");
            System.out.println("(5)Show Menu");
            System.out.println("(6)Create User Profile");
            System.out.println("(7)Quit");
            System.out.println("--------------------------------------");

            System.out.print("Option >> ");
            option = Utility.readMenuSelection();

            switch (option) {
                case '1':
                    break;

                case '2':
                    break;

                case '3':
                    break;

                case '4':
                    System.out.print("Please enter order ID: ");
                    String orderID = Utility.readString(5);
                    orderListService.showOrderByID(orderID, clientListService, dishOrderService);
                    break;

                case '5':
                    menu.showMenu();
                    break;

                case '6':
                    break;

                case '7':
                    System.out.print("Are you sure to end the program? (Y/N): ");
                    char exit = Utility.readConfirmSelection();
                    if (exit == 'Y') {
                        isRun = false;
                        System.out.print("Thank You For Using The System.\n");
                    }
                    break;

                default:
                    System.out.print("--------------------------------------\n");
                    System.out.print("Invalid Input!\n");
                    Utility.readReturn();
                    break;
            }
        }
    }

    public static void main(String[] args) {
        MainMenu mainMenu = new MainMenu();
        mainMenu.enterMainMenu();
    }
}
