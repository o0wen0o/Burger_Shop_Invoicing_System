package service;

import domain.Client;
import domain.Dish;
import domain.Order;
import domain.OrderType;
import view.Utility;

import java.util.ArrayList;
import java.util.List;

/**
 * @author o0wen0o
 * @create 2023-02-17 3:36 PM
 */
public class OrderListService {
    private List<Order> orderList = new ArrayList<>();
    private String srcPath = "OrderData.txt";

    public OrderListService(DishOrderService dishOrderService) {
        List<String> elements = Utility.readFile(srcPath);

        for (int i = 0; i < elements.size(); i += 6) {
            String orderID = elements.get(i + 0);
            String tableNo = elements.get(i + 1);
            String clientID = elements.get(i + 2);
            OrderType orderType = OrderType.valueOf(elements.get(i + 3));
            List<Dish> dishOrder = dishOrderService.getDishOrderByOrderID(orderID);
            String dateTime = elements.get(i + 4);

            orderList.add(new Order(orderID, tableNo, clientID, orderType, dishOrder, dateTime));
        }
    }

    public void showOrderByID(String orderID, ClientListService clientListService, DishOrderService dishOrderService) {
        Order order = getOrderById(orderID);

        if (order == null) {
            System.out.println("Order ID does not exist.\n");
            Utility.readReturn();
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
        System.out.println(String.format("%76s", " ").replace(' ', '-'));

        dishOrderService.showDishOrderByOrderID(orderID);

        System.out.println(String.format("%76s", " ").replace(' ', '-'));
        System.out.println();
        Utility.readReturn();
    }

    public List<Order> getAllOrderList() {
        return orderList;
    }

    public Order getOrderById(String orderID) {
        for (Order Order : orderList) {
            if (orderID.equals(Order.getOrderID())) {
                return Order;
            }
        }
        return null;
    }
}
