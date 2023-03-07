package service;

import domain.Dish;
import view.Utility;

import java.util.*;

/**
 * each order has one this instance
 *
 * @author o0wen0o
 * @create 2023-02-17 5:35 PM
 */
public class DishOrderService implements Service {
    private Map<String, ArrayList<Dish>> dishOrderList = new HashMap<>();
    private String srcPath = "DishOrder.txt";

    public DishOrderService(Menu menu) {
        List<String> elements = Utility.readFile(srcPath);

        ArrayList<Dish> dishOrder = new ArrayList<>();
        String orderID = elements.get(0);

        for (int i = 1; i < elements.size(); i += 2) {
            String dishID = elements.get(i + 0);
            int quantity = Integer.parseInt(elements.get(i + 1));
            dishOrder.add(new Dish(menu.getDishByID(dishID), quantity));

            // if element is last two element
            if (i == elements.size() - 2) {
                dishOrderList.put(orderID, dishOrder);
                break;
            }

            // if element is order ID
            if (elements.get(i + 2).matches("^O[0-9]{4}$")) {
                dishOrderList.put(orderID, dishOrder);
                orderID = elements.get(++i + 1);
                dishOrder = new ArrayList<>();
            }
        }
    }

    public void showDishOrderByOrderID(String orderID) {
        showDishOrder(orderID, null);
    }

    public void showDishOrderByList(ArrayList<Dish> dishOrder) {
        showDishOrder(null, dishOrder);
    }

    private void showDishOrder(String orderID, ArrayList<Dish> dishOrderNew) {
        ArrayList<Dish> dishOrder = new ArrayList<>();

        if (dishOrderNew == null) {
            dishOrder = dishOrderList.get(orderID);
        }

        if (orderID == null) {
            dishOrder = dishOrderNew;
        }

        System.out.println(String.format("%76s", " ").replace(' ', '-'));
        System.out.printf("|%-10s|%-30s|%-10s|%-10s|%-10s|%n", "Dish ID", "Dish Name", "Quantity", "Unit Price", "Amount");
        System.out.println(String.format("%76s", " ").replace(' ', '-'));

        double total = 0;
        for (Dish dish : dishOrder) {
            int quantity = dish.getQuantity();
            double unitPrice = dish.getUnitPrice();
            double amount = quantity * unitPrice;
            total += amount;
            System.out.printf("|%-10s|%-30s|%-10d|%-10.2f|%-10.2f|%n", dish.getDishID(), dish.getDishName(), quantity, unitPrice, amount);
        }

        System.out.println(String.format("%76s", " ").replace(' ', '-'));
        System.out.println("Total: $" + total);
        System.out.println(String.format("%76s", " ").replace(' ', '-'));
        System.out.println();
    }

    public void addDishOrder(String orderID, ArrayList<Dish> dishOrder) {
        dishOrderList.put(orderID, dishOrder);
        saveFile();
    }

    public void cancelDishOrder(String orderID) {
        dishOrderList.remove(orderID);
        saveFile();
    }

    public List<Dish> getDishOrderByOrderID(String orderID) {
        return dishOrderList.get(orderID);
    }

    public void saveFile() {
        StringBuilder str = new StringBuilder();

        for (Map.Entry<String, ArrayList<Dish>> entry : dishOrderList.entrySet()) {
            String key = entry.getKey();
            ArrayList<Dish> value = entry.getValue();

            str.append(key);
            for (Dish dish : value) {
                str.append(String.format(",%s,%s", dish.getDishID(), dish.getQuantity()));
            }
            str.append("\n");
        }

        Utility.saveFile(srcPath, str.toString());
    }
}
