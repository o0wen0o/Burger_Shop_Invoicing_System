package service;

import domain.Dish;
import view.Utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author o0wen0o
 * @create 2023-02-17 3:36 PM
 */
public class Menu implements OrderService<Dish> {
    private final List<Dish> menu = new ArrayList<>();
    private final String srcPath = "Menu.txt";

    public Menu() {
        List<String> elements = Utility.readFile(srcPath);

        for (int i = 0; i < elements.size(); i += 3) {
            String dishID = elements.get(i + 0);
            String dishName = elements.get(i + 1);
            double unitPrice = Double.parseDouble(elements.get(i + 2));

            menu.add(new Dish(dishID, dishName, unitPrice));
        }
    }

    @Override
    public void create(Map<String, Object> data) {
        // not allow repeat same ID, no need input from user
        // get last order ID
        String lastId = menu.get(menu.size() - 1).getDishID();
        // get the number part of the ID and plus 1
        int newId = Integer.parseInt(lastId.substring(1)) + 1;
        // form a new ID
        String dishID = "D" + String.format("%02d", newId);
        System.out.println("Dish ID: " + dishID);

        System.out.print("Dish Name >> ");
        String dishName = Utility.readString(30);

        System.out.print("Unit Price >> ");
        double unitPrice = Utility.readDouble(6);

        createDish(new Dish(dishID, dishName, unitPrice));
        System.out.println("Created Successfully!");
    }

    @Override
    public void update(Map<String, Object> data) {
        showMenu();

        String dishID = searchDish();
        if (dishID == null)
            return;

        Dish dish = getDishByID(dishID);

        // index of the object which will be changed
        int index = menu.indexOf(dish);

        System.out.println("Press 'Enter' if remain unchanged. ");
        System.out.print("Dish Name >> ");
        String dishName = Utility.readString(30, dish.getDishName());

        System.out.print("Unit Price >> ");
        double unitPrice = Utility.readDouble(6, dish.getUnitPrice());

        updateDish(index, new Dish(dishID, dishName, unitPrice));
        System.out.println("Updated Successfully!");
    }

    @Override
    public void delete() {
        showMenu();

        String dishID = searchDish();

        if (dishID == null)
            return;

        Dish dish = getDishByID(dishID);

        deleteDish(dish);
        System.out.println("Deleted Successfully!");
    }

    @Override
    public void showList() {
        showMenu();
    }

    @Override
    public List<Dish> getList() {
        return menu;
    }

    public boolean isExist(String dishID) {
        return menu.contains(getDishByID(dishID.toUpperCase()));
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

    public void saveFile() {
        StringBuilder str = new StringBuilder();

        for (Dish dish : menu) {
            str.append(String.format("%s,%s,%s\n", dish.getDishID(), dish.getDishName(), dish.getUnitPrice()));
        }

        Utility.saveFile(srcPath, str.toString());
    }

    private void createDish(Dish dish) {
        menu.add(dish);
        saveFile();
    }

    private void updateDish(int index, Dish dish) {
        menu.set(index, dish);
        saveFile();
    }

    private void deleteDish(Dish dish) {
        menu.remove(dish);
        saveFile();
    }

    // search dish by ID, if exist then return the dishID, if not then return null
    private String searchDish() {
        System.out.print("Please enter dish ID: ");
        String dishID = Utility.readString(3).toUpperCase();

        // check if dish ID exist
        if (!isExist(dishID)) {
            System.out.println("Dish does not exist. Please try again.");
            return null;
        }

        return dishID;
    }

    private void showMenu() {
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
}
