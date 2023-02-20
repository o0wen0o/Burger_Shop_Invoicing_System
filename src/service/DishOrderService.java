package service;

import domain.Dish;
import view.Utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * each order has one this instance
 *
 * @author o0wen0o
 * @create 2023-02-17 5:35 PM
 */
public class DishOrderService {
    private Map<String, ArrayList<Dish>> dishOrderList = new HashMap<>();
    private String srcPath = "DishOrder.txt";

    public DishOrderService(Menu menu) {
        List<String> elements = Utility.readFile(srcPath);

        ArrayList<Dish> dishes = new ArrayList<>();
        String orderID = elements.get(0);

        for (int i = 1; i < elements.size(); i += 2) {
            String dishID = elements.get(i + 0);
            int quantity = Integer.parseInt(elements.get(i + 1));
            dishes.add(new Dish(menu.getDishByID(dishID), quantity));

            if (elements.get(i + 1).charAt(0) == 'O' || i == elements.size() - 2) {
                dishOrderList.put(orderID, dishes);
                orderID = elements.get(i + 1);
                dishes = new ArrayList<>();
            }
        }
    }

    public void showDishOrderByOrderID(String orderID) {
        ArrayList<Dish> dishes = dishOrderList.get(orderID);
        System.out.println(String.format("|%-10s|%-30s|%-10s|%-10s|%-10s|", "Dish ID", "Dish Name", "Quantity", "Unit Price", "Amount"));
        System.out.println(String.format("%76s", " ").replace(' ', '-'));

        double total = 0;
        for (Dish dish : dishes) {
            int quantity = dish.getQuantity();
            double unitPrice = dish.getUnitPrice();
            double amount = quantity * unitPrice;
            total += amount;
            System.out.println(String.format("|%-10s|%-30s|%-10d|%-10.2f|%-10.2f|", dish.getDishID(), dish.getDishName(), quantity, unitPrice, amount));
        }

        System.out.println(String.format("%76s", " ").replace(' ', '-'));
        System.out.println("Total: $" + total);
    }

    public List<Dish> getDishOrderByOrderID(String orderID) {
        return dishOrderList.get(orderID);
    }
}
