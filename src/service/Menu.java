package service;

import domain.Dish;
import view.Utility;

import java.util.ArrayList;
import java.util.List;

/**
 * @author o0wen0o
 * @create 2023-02-17 3:36 PM
 */
public class Menu {
    private List<Dish> menu = new ArrayList<>();
    private String srcPath = "Menu.txt";

    public Menu() {
        List<String> elements = Utility.readFile(srcPath);

        for (int i = 0; i < elements.size(); i += 3) {
            String dishID = elements.get(i + 0);
            String dishName = elements.get(i + 1);
            double unitPrice = Double.parseDouble(elements.get(i + 2));

            menu.add(new Dish(dishID, dishName, unitPrice));
        }
    }

    public void showMenu() {
        System.out.println("\nMenu:");
        System.out.println(String.format("%54s", " ").replace(' ', '-'));

        String str = String.format("|%-10s|%-30s|%-10s|", "Dish ID", "Dish Name", "Unit Price");
        System.out.println(str);

        System.out.println(String.format("%54s", " ").replace(' ', '-'));

        for (Dish dish : menu) {
            System.out.println(dish);
        }

        System.out.println(String.format("%54s", " ").replace(' ', '-'));
        System.out.println();
    }

    public void showDish(Dish dish) {
        System.out.println(String.format("%54s", " ").replace(' ', '-'));

        String str = String.format("|%-10s|%-30s|%-10s|", "Dish ID", "Dish Name", "Unit Price");
        System.out.println(str);

        System.out.println(String.format("%54s", " ").replace(' ', '-'));

        System.out.println(dish);

        System.out.println(String.format("%54s", " ").replace(' ', '-'));
        System.out.println();
    }

    public boolean isExist(String dishID) {
        return menu.contains(getDishByID(dishID.toUpperCase()));
    }

    public List<Dish> getMenu() {
        return menu;
    }

    public Dish getDishByID(String dishID) {
        dishID = dishID.toUpperCase();

        for (Dish dish : menu) {
            if (dishID.equals(dish.getDishID())) {
                return dish;
            }
        }
        return null;
    }
}
