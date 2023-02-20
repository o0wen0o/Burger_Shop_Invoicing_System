package domain;

/**
 * @author o0wen0o
 * @create 2023-02-17 11:14 AM
 */
public class Dish {
    private String dishID;
    private String dishName;
    private double unitPrice;
    private int quantity;

    public Dish() {
    }

    // get a dish from menu to create a new instance of dish when order
    public Dish(Dish dish, int quantity) {
        this.dishID = dish.dishID;
        this.dishName = dish.dishName;
        this.unitPrice = dish.unitPrice;
        this.quantity = quantity;
    }

    public Dish(String dishID, String dishName, double unitPrice) {
        this.dishID = dishID;
        this.dishName = dishName;
        this.unitPrice = unitPrice;
    }

    public String getDishID() {
        return dishID;
    }

    public void setDishID(String dishID) {
        this.dishID = dishID;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        String str = String.format("|%-10s|%-30s|%-10.2f|", getDishID(), getDishName(), getUnitPrice());
        return str;
    }
}

