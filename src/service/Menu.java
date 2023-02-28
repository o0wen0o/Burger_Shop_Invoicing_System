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

    public void updateMenu() {
        char option;
        boolean isRun = true;

        while (isRun) {
            System.out.println("\n--------------------------------------");
            System.out.println("(1) Create Dish");
            System.out.println("(2) Update Dish");
            System.out.println("(3) Delete Dish");
            System.out.println("(4) Quit");
            System.out.println("--------------------------------------");

            System.out.print("Option >> ");
            option = Utility.readSelection(new char[]{'1', '2', '3', '4'});

            switch (option) {
                case '1':
                    createDish();
                    break;

                case '2':
                    updateDish();
                    break;

                case '3':
                    deleteDish();
                    break;

                case '4':
                    System.out.print("Quit? (Y/N): ");
                    char exit = Utility.readConfirmSelection();
                    if (exit == 'Y') {
                        isRun = false;
                    }
                    break;
            }
            Utility.readReturn();
        }
    }

    public void createDish() {
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

        menu.add(new Dish(dishID, dishName, unitPrice));
        saveFile();
        System.out.println("Created Successfully!");
    }

    public void updateDish() {
        showMenu();

        System.out.print("Please enter dish ID: ");
        String dishID = Utility.readString(3).toUpperCase();

        // check if dish ID exist
        if (!isExist(dishID)) {
            System.out.println("Dish does not exist. Please try again.");
            return;
        }

        Dish dish = getDishByID(dishID);

        System.out.println("Press 'Enter' if remain unchanged. ");
        System.out.print("Dish Name >> ");
        String dishName = Utility.readString(30, dish.getDishName());

        System.out.print("Unit Price >> ");
        double unitPrice = Utility.readDouble(6, dish.getUnitPrice());

        menu.set(menu.indexOf(dish), new Dish(dishID, dishName, unitPrice));
        saveFile();
        System.out.println("Updated Successfully!");
    }

    public void deleteDish() {
        showMenu();

        System.out.print("Please enter dish ID: ");
        String dishID = Utility.readString(3).toUpperCase();

        // check if dish ID exist
        if (!isExist(dishID)) {
            System.out.println("Dish does not exist. Please try again.");
            return;
        }

        Dish dish = getDishByID(dishID);

        menu.remove(dish);
        saveFile();
        System.out.println("Deleted Successfully!");
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

    private void saveFile() {
        String str = "";

        for (Dish dish : menu) {
            str += String.format("%s,%s,%s\n", dish.getDishID(), dish.getDishName(), dish.getUnitPrice());
        }

        Utility.saveFile(srcPath, str);
    }
}
