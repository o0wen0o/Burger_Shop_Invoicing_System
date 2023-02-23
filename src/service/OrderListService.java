package service;

import domain.Dish;
import domain.Order;
import view.Utility;

import javax.rmi.CORBA.Util;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author o0wen0o
 * @create 2023-02-17 3:36 PM
 */
public class OrderListService {
    private List<Order> orderList = new ArrayList<>();
    private String srcPath = "OrderData.txt";

    public OrderListService(DishOrderService dishOrderService) {
        List<String> elements = Utility.readFile(srcPath);

        for (int i = 0; i < elements.size(); i += 5) {
            String orderID = elements.get(i + 0);
            String tableNo = elements.get(i + 1);
            String clientID = elements.get(i + 2);
            OrderType orderType = OrderType.valueOf(elements.get(i + 3));
            List<Dish> dishOrder = dishOrderService.getDishOrderByOrderID(orderID);
            String dateTime = elements.get(i + 4);

            orderList.add(new Order(orderID, tableNo, clientID, orderType, dishOrder, dateTime));
        }
    }

    public void showOrderByID(ClientListService clientListService, DishOrderService dishOrderService) {
        System.out.print("Please enter order ID: ");
        String orderID = Utility.readString(5).toUpperCase();
        Order order = getOrderById(orderID);

        if (order == null) {
            System.out.println("Order ID does not exist.\n");
            return;
        }

        System.out.println();
        System.out.println(String.format("%76s", " ").replace(' ', '-'));
        System.out.println("Burger Shop System");
        System.out.println(String.format("%76s", " ").replace(' ', '-'));
        System.out.println("Order ID  : " + orderID);
        System.out.println("Table No  : " + order.getTableNo());
        System.out.println("Bill To   : " + clientListService.getClientById(order.getClientID()).getUserName());
        System.out.println("Order Type: " + order.getOrderType());
        System.out.println("Date      : " + order.getDateTime());

        dishOrderService.showDishOrderByOrderID(orderID);
    }

    public void createOrder(String clientID, Menu menu, ClientListService clientListService, DishOrderService dishOrderService) {
        // not allow repeat same ID, no need input from user
        // get last order ID
        String lastId = orderList.get(orderList.size() - 1).getOrderID();
        // get the number part of the ID and plus 1
        int newId = Integer.parseInt(lastId.substring(1)) + 1;
        // form a new ID
        String orderID = "O" + String.format("%04d", newId);
        System.out.println("Order ID: " + orderID);

        String tableNo = "Table 01";
        System.out.println("Table No: " + tableNo);

        System.out.println("Bill To: " + clientListService.getClientById(clientID).getUserName());

        System.out.println("\nOrder Type: (1)Dine In\t(2)Take Away");
        System.out.print("Option >> ");
        char selection = Utility.readSelection(new char[]{'1','2'});
        OrderType orderType = selection == '1' ? OrderType.DINE_IN : OrderType.TAKE_AWAY;

        DateTimeFormatter formatter3 = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        String dateTime = formatter3.format(LocalDateTime.now());
        System.out.println("Date Time: " + dateTime);

        ArrayList<Dish> dishOrder = new ArrayList<>();
        System.out.println("\n(Q) to exit. (C) to complete order.");

        while (true) {
            System.out.print("Add To Cart >> (ID) ");
            String dishID = Utility.readString(3).toUpperCase();

            // to quit the order process
            if ("Q".equalsIgnoreCase(dishID)) {
                System.out.print("End order process? (Y/N): ");
                char exit = Utility.readConfirmSelection();
                if (exit == 'Y') {
                    break;
                }
                continue; // continue this method
            }

            // to complete the order process
            if ("C".equalsIgnoreCase(dishID)) {
                if (dishOrder.isEmpty()) {
                    System.out.println("No dishes in the cart.");
                    continue; // continue this method
                }

                // end this method
                dishOrderService.addDishOrder(orderID, dishOrder);
                orderList.add(new Order(orderID, tableNo, clientID, orderType, dishOrder, dateTime));
                saveFile();
                System.out.println("Created Successfully!");
                break;
            }

            // check if dish ID exist
            if (!menu.isExist(dishID)) {
                System.out.println("Dish does not exist. Please try again.");
                continue; // continue this method
            }

            Dish dish = menu.getDishByID(dishID);

            System.out.print("Quantity >> ");
            int quantity = Utility.readInt();

            boolean isNewDish = true;
            // if the dish already ordered
            for (Dish dishOld : dishOrder) {
                if (dishOld.getDishID().equals(dishID)) {
                    dishOld.setQuantity(dishOld.getQuantity() + quantity);

                    isNewDish = false;
                    break;
                }
            }

            // if the dish haven't ordered
            if (isNewDish) {
                dishOrder.add(new Dish(dish, quantity));
            }

            System.out.println(String.format("Added %d %s.\n", quantity, dish.getDishName()));
        }
    }

    // update order by ID
    public void updateOrder(Menu menu, ClientListService clientListService, DishOrderService dishOrderService) {
        System.out.print("Please enter order ID: ");
        String orderID = Utility.readString(5).toUpperCase();
        Order order = getOrderById(orderID);

        if (order == null) {
            System.out.println("Order ID does not exist.\n");
            return;
        }

        String tableNo = "Table 01";
        System.out.println("Table No: " + tableNo);

        String clientID = order.getClientID();
        System.out.println("Bill To: " + clientListService.getClientById(clientID).getUserName());

        System.out.println("\nOrder Type: (1)Dine In\t(2)Take Away");
        System.out.print("Option >> ");
        char selection = Utility.readSelection(new char[]{'1','2'});
        OrderType orderType = selection == '1' ? OrderType.DINE_IN : OrderType.TAKE_AWAY;

        List<Dish> dishOrder = order.getDishOrder();

        DateTimeFormatter formatter3 = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        String dateTime = formatter3.format(LocalDateTime.now());
        System.out.println("Date Time: " + dateTime);

        char option;
        boolean isRun = true;

        while (isRun) {
            System.out.println("\nHere Is The Order.");
            dishOrderService.showDishOrderByOrderID(orderID);

            System.out.println("--------------------------------------");
            System.out.println("(1) Add New Dish");
            System.out.println("(2) Update Dish");
            System.out.println("(3) Cancel Dish");
            System.out.println("(Q) to exit. (C) to complete order.");
            System.out.println("--------------------------------------");

            System.out.print("Option >> ");
            option = Utility.readSelection(new char[]{'1','2','3','Q','C'});

            String dishID;
            switch (option) {
                case '1':
                    menu.showMenu();
                case '2':
                    if (option == '1') {
                        System.out.print("Add To Cart >> (ID) ");
                    } else {
                        System.out.print("Update Dish >> (ID) ");
                    }

                    dishID = Utility.readString(3).toUpperCase();

                    // check if dish ID exist
                    if (!menu.isExist(dishID)) {
                        System.out.println("Dish does not exist. Please try again.");
                        break; // continue this method
                    }

                    Dish dish = menu.getDishByID(dishID);

                    System.out.print("Quantity >> ");
                    int quantity = Utility.readInt();

                    boolean isNewDish = true;
                    // if the dish already ordered
                    for (Dish dishOld : dishOrder) {
                        if (dishOld.getDishID().equals(dishID)) {
                            if (option == '1') {
                                dishOld.setQuantity(dishOld.getQuantity() + quantity);
                            } else {
                                dishOld.setQuantity(quantity);
                            }

                            isNewDish = false;
                            break;
                        }
                    }

                    // if the dish haven't ordered
                    if (isNewDish) {
                        dishOrder.add(new Dish(dish, quantity));
                    }

                    if (option == '1') {
                        System.out.println(String.format("Added %d %s.\n", quantity, dish.getDishName()));
                    } else {
                        System.out.println("Updated Successfully!");
                    }
                    Utility.readReturn();
                    break;

                case '3':
                    System.out.print("Cancel Dish >> (ID) ");
                    dishID = Utility.readString(3).toUpperCase();

                    // check if dish ID exist
                    if (!menu.isExist(dishID)) {
                        System.out.println("Dish does not exist. Please try again.");
                        break; // continue this method
                    }

                    for (Dish dishOld : dishOrder) {
                        if (dishOld.getDishID().equals(dishID)) {
                            dishOrder.remove(dishOld);
                            break;
                        }
                    }
                    System.out.println("Cancelled Successfully!");
                    Utility.readReturn();
                    break;

                case 'Q':
                    System.out.print("End order process? (Y/N): ");
                    char exit = Utility.readConfirmSelection();
                    if (exit == 'Y') {
                        isRun = false;
                    }
                    break;

                case 'C':
                    // to complete the order process
                    if (dishOrder.isEmpty()) {
                        System.out.println("No dishes in the cart.");
                        break; // continue this method
                    }

                    // end this method
                    // no need to update dishOrderService
                    // dishOrderService.addDishOrder(orderID, dishOrderNew);
                    orderList.set(orderList.indexOf(order), new Order(orderID, tableNo, clientID, orderType, dishOrder, dateTime));
                    saveFile();
                    System.out.println("Updated Successfully!");
                    isRun = false;
                    break;
            }
        }
    }

    // cancel order by ID
    public void cancelOrder(DishOrderService dishOrderService) {
        System.out.print("Please enter order ID: ");
        String orderID = Utility.readString(5).toUpperCase().toUpperCase();
        Order order = getOrderById(orderID);

        if (order == null) {
            System.out.println("Order ID does not exist.\n");
            return;
        }

        System.out.print("Confirm cancel order? (Y/N): ");
        char exit = Utility.readConfirmSelection();
        if (exit == 'Y') {
            dishOrderService.cancelDishOrder(orderID);
            orderList.remove(order);
            saveFile();
        }
    }

    public Order getOrderById(String orderID) {
        orderID = orderID.toUpperCase();

        for (Order Order : orderList) {
            if (orderID.equals(Order.getOrderID())) {
                return Order;
            }
        }
        return null;
    }

    private void saveFile() {
        String str = "";

        for (Order order : orderList) {
            str += String.format("%s,%s,%s,%s,%s\n", order.getOrderID(), order.getTableNo(), order.getClientID(), order.getOrderType(), order.getDateTime());
        }

        Utility.saveFile(srcPath, str);
    }
}
