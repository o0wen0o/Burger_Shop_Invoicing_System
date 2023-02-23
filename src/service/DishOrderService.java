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
public class DishOrderService {
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
        ArrayList<Dish> dishOrder = dishOrderList.get(orderID.toUpperCase());
        System.out.println(String.format("|%-10s|%-30s|%-10s|%-10s|%-10s|", "Dish ID", "Dish Name", "Quantity", "Unit Price", "Amount"));
        System.out.println(String.format("%76s", " ").replace(' ', '-'));

        double total = 0;
        for (Dish dish : dishOrder) {
            int quantity = dish.getQuantity();
            double unitPrice = dish.getUnitPrice();
            double amount = quantity * unitPrice;
            total += amount;
            System.out.println(String.format("|%-10s|%-30s|%-10d|%-10.2f|%-10.2f|", dish.getDishID(), dish.getDishName(), quantity, unitPrice, amount));
        }

        System.out.println(String.format("%76s", " ").replace(' ', '-'));
        System.out.println("Total: $" + total);
    }

    public void addDishOrder(String orderID, ArrayList<Dish> dishOrder) {
        dishOrderList.put(orderID, dishOrder);
        saveFile();
    }

    public List<Dish> getDishOrderByOrderID(String orderID) {
        return dishOrderList.get(orderID.toUpperCase());
    }

    private void saveFile() {
        String str = "";

        for (Map.Entry<String, ArrayList<Dish>> entry : dishOrderList.entrySet()) {
            String key = entry.getKey();
            ArrayList<Dish> value = entry.getValue();

            str += key;
            for (Dish dish : value) {
                str += String.format(",%s,%s", dish.getDishID(), dish.getQuantity());
            }
            str += "\n";
        }

        Utility.saveFile(srcPath, str);
    }
}
