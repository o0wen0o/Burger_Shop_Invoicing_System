package service;

import domain.Client;
import domain.Dish;
import domain.Order;
import view.Utility;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author o0wen0o
 * @create 2023-02-17 3:35 PM
 */
public class OrderListService implements OrderService<Order> {
    private final List<Order> orderList = new ArrayList<>();
    private final String srcPath = "OrderData.txt";
    private final DishOrderService dishOrderService;

    public OrderListService(OrderService<Dish> menu) {
        dishOrderService = new DishOrderService(menu);
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

    @Override
    public void create(Map<String, Object> data) {
        String clientID = (String) data.get("clientID");
        Menu menu = (Menu) data.get("menu");
        ClientListService clientListService = (ClientListService) data.get("clientListService");

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

        DateTimeFormatter formatter3 = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        String dateTime = formatter3.format(LocalDateTime.now());
        System.out.println("Date Time: " + dateTime);

        System.out.println("\nOrder Type: ");
        System.out.println("(1)Dine In");
        System.out.println("(2)Take Away");
        System.out.print("Option >> ");
        char selection = Utility.readSelection(new char[]{'1', '2'});
        OrderType orderType = selection == '1' ? OrderType.DINE_IN : OrderType.TAKE_AWAY;

        List<Dish> dishOrder = addToCart(menu);

        if (dishOrder == null) {
            return;
        }

        createOrder(new Order(orderID, tableNo, clientID, orderType, dishOrder, dateTime), dishOrder);
        System.out.println("Created Successfully!");

        // show the order
        Utility.readReturn();
        System.out.println("Here is your order:");
        showOrderByID(orderID, clientListService);
    }

    @Override
    public void update(Map<String, Object> data) {
        // update order by ID
        Menu menu = (Menu) data.get("menu");
        ClientListService clientListService = (ClientListService) data.get("clientListService");

        System.out.print("Please enter order ID (Ex.O0001): ");
        String orderID = Utility.readString(5).toUpperCase();
        Order order = getOrderById(orderID);

        // index of the object which will be changed
        int index = orderList.indexOf(order);

        if (order == null) {
            System.out.println("Order ID does not exist.\n");
            return;
        }

        String tableNo = "Table 01";
        System.out.println("Table No: " + tableNo);

        String clientID = order.getClientID();
        System.out.println("Bill To: " + clientListService.getClientById(clientID).getUserName());

        DateTimeFormatter formatter3 = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        String dateTime = formatter3.format(LocalDateTime.now());
        System.out.println("Date Time: " + dateTime);

        OrderType orderType = order.getOrderType();
        System.out.println("Order Type: " + orderType);

        // copy the dishOrder
        List<Dish> dishOrderNew = new ArrayList<>();
        for (Dish dish : order.getDishOrder()) {
            dishOrderNew.add(new Dish(dish, dish.getQuantity()));
        }

        char option;
        boolean isRun = true;

        while (isRun) {
            System.out.println("\nHere Is The Order.");
            dishOrderService.showDishOrderByList(dishOrderNew);

            System.out.println("--------------------------------------");
            System.out.println("(1) Add New Dish");
            System.out.println("(2) Update Dish");
            System.out.println("(3) Cancel Dish");
            System.out.println("(4) Complete Update");
            System.out.println("(5) Cancel Update");
            System.out.println("--------------------------------------");

            System.out.print("Option >> ");
            option = Utility.readSelection(new char[]{'1', '2', '3', '4', '5'});

            String dishID;
            switch (option) {
                case '1':
                    (menu).showList();
                case '2':
                    if (option == '1') {
                        System.out.print("Add To Cart (Enter Dish ID): ");
                    } else {
                        System.out.print("Update Dish (Enter Dish ID): ");
                    }

                    dishID = Utility.readString(3).toUpperCase();

                    // check if dish ID exist
                    if (!(menu).isExist(dishID)) {
                        System.out.println("Dish does not exist. Please try again.");
                        break; // continue this method
                    }

                    Dish dish = (menu).getDishByID(dishID);

                    System.out.print("Quantity >> ");
                    int quantity = Utility.readInt(6);

                    boolean isNewDish = true;
                    // if the dish already ordered
                    for (Dish dishOld : dishOrderNew) {
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
                        dishOrderNew.add(new Dish(dish, quantity));
                    }

                    if (option == '1') {
                        System.out.printf("Added %d %s.\n%n", quantity, dish.getDishName());
                    } else {
                        System.out.println("Updated Successfully!");
                    }
                    Utility.readReturn();
                    break;

                case '3':
                    System.out.print("Cancel Dish (Enter Dish ID): ");
                    dishID = Utility.readString(3).toUpperCase();

                    // check if dish ID exist
                    if (!(menu).isExist(dishID)) {
                        System.out.println("Dish does not exist. Please try again.");
                        break; // continue this method
                    }

                    for (Dish dishOld : dishOrderNew) {
                        if (dishOld.getDishID().equals(dishID)) {
                            dishOrderNew.remove(dishOld);
                            break;
                        }
                    }
                    System.out.println("Cancelled Successfully!");
                    Utility.readReturn();
                    break;

                case '4':
                    // to complete the order process
                    // cart can not be empty
                    if (dishOrderNew.isEmpty()) {
                        System.out.println("No dishes in the cart.");
                        break; // continue this method
                    }

                    // end this method
                    updateOrder(index, new Order(orderID, tableNo, clientID, orderType, dishOrderNew, dateTime), dishOrderNew);
                    System.out.println("Updated Successfully!");
                    isRun = false;
                    break;

                case '5':
                    // to cancel the order process
                    System.out.print("Cancel the order? (Y/N): ");
                    char exit = Utility.readConfirmSelection();
                    if (exit == 'Y') {
                        isRun = false;
                    }
                    break;
            }
        }
    }

    // cancel order by ID
    @Override
    public void delete() {
        System.out.print("Please enter order ID (Ex.O0001): ");
        String orderID = Utility.readString(5).toUpperCase().toUpperCase();
        Order order = getOrderById(orderID);

        if (order == null) {
            System.out.println("Order ID does not exist.\n");
            return;
        }

        System.out.print("Confirm cancel order? (Y/N): ");
        char exit = Utility.readConfirmSelection();
        if (exit == 'Y') {
            cancelOrder(order);
            System.out.println("Deleted Successfully!");
        }
    }

    @Override
    public void showList() {
        for (Order order : orderList) {
            String orderID = order.getOrderID();

            System.out.println(String.format("%76s", " ").replace(' ', '-'));
            System.out.println("Order ID  : " + orderID);
            System.out.println("Table No  : " + order.getTableNo());
            System.out.println("Bill To   : " + order.getClientID());
            System.out.println("Order Type: " + order.getOrderType());
            System.out.println("Date      : " + order.getDateTime());

            dishOrderService.showDishOrderByOrderID(orderID);
        }
    }

    @Override
    public List<Order> getList() {
        return orderList;
    }

    public void showOrderByID(String orderID, Service<Client> clientListService) {
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
        System.out.println("Bill To   : " + ((ClientListService) clientListService).getClientById(order.getClientID()).getUserName());
        System.out.println("Order Type: " + order.getOrderType());
        System.out.println("Date      : " + order.getDateTime());

        dishOrderService.showDishOrderByOrderID(orderID);
    }

    public void saveFile() {
        StringBuilder str = new StringBuilder();

        for (Order order : orderList) {
            str.append(String.format("%s,%s,%s,%s,%s\n", order.getOrderID(), order.getTableNo(), order.getClientID(), order.getOrderType(), order.getDateTime()));
        }

        Utility.saveFile(srcPath, str.toString());
    }

    private void createOrder(Order order, List<Dish> dishOrder) {
        dishOrderService.addDishOrder(order.getOrderID(), dishOrder);
        orderList.add(order);
        saveFile();
    }

    private List<Dish> addToCart(Menu menu) {
        menu.showList();

        List<Dish> dishOrder = new ArrayList<>();
        System.out.println("(1) Enter '1' To Complete Order");
        System.out.println("(2) Enter '2' To Cancel Order");

        while (true) {
            System.out.print("Add To Cart (Enter Dish ID): ");
            String dishID = Utility.readString(3).toUpperCase();

            // to quit the order process
            if ("2".equalsIgnoreCase(dishID)) {
                System.out.print("Cancel the order? (Y/N): ");
                char exit = Utility.readConfirmSelection();
                if (exit == 'Y') {
                    return null;
                }
                continue; // continue this method
            }

            // to complete the order process
            if ("1".equalsIgnoreCase(dishID)) {
                if (dishOrder.isEmpty()) {
                    System.out.println("No dishes in the cart.");
                    continue; // continue this method
                }

                // end this method
                return dishOrder;
            }

            // if dish ID does not exist
            if (!menu.isExist(dishID)) {
                System.out.println("Dish does not exist. Please try again.");
                continue; // continue this method
            }

            // if dish exist
            Dish dish = menu.getDishByID(dishID);

            System.out.print("Quantity >> ");
            int quantity = Utility.readInt(6);

            // if the dish already ordered
            boolean isNewDish = true;
            for (Dish dishOld : dishOrder) {
                if (dishOld.getDishID().equals(dishID)) {
                    dishOld.setQuantity(dishOld.getQuantity() + quantity);

                    isNewDish = false;
                    break;
                }
            }

            // if the dish has not been ordered
            if (isNewDish) {
                dishOrder.add(new Dish(dish, quantity));
            }

            System.out.printf("Added %d %s.\n%n", quantity, dish.getDishName());
        }
    }

    private void updateOrder(int index, Order order, List<Dish> dishOrderNew) {
        dishOrderService.addDishOrder(order.getOrderID(), dishOrderNew);
        orderList.set(index, order);
        saveFile();
    }

    private void cancelOrder(Order order) {
        dishOrderService.cancelDishOrder(order.getOrderID());
        orderList.remove(order);
        saveFile();
    }

    private Order getOrderById(String orderID) {
        orderID = orderID.toUpperCase();

        for (Order order : orderList) {
            if (orderID.equals(order.getOrderID())) {
                return order;
            }
        }
        return null;
    }
}
