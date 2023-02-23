package domain;

import service.OrderType;

import java.util.List;

/**
 * @author o0wen0o
 * @create 2023-02-17 11:14 AM
 */
public class Order {
    private String orderID;
    private String tableNo;
    private String clientID;
    private OrderType orderType = OrderType.DINE_IN;
    private List<Dish> dishOrder;
    private String dateTime;

    public Order() {
    }

    public Order(String orderID, String tableNo, String clientID, OrderType orderType, List<Dish> dishOrder, String dateTime) {
        this.orderID = orderID;
        this.tableNo = tableNo;
        this.clientID = clientID;
        this.orderType = orderType;
        this.dishOrder = dishOrder;
        this.dateTime = dateTime;
    }

    public String getOrderID() {
        return orderID;
    }

    public String getTableNo() {
        return tableNo;
    }

    public String getClientID() {
        return clientID;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public List<Dish> getDishOrder() {
        return dishOrder;
    }

    public String getDateTime() {
        return dateTime;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderID='" + orderID + '\'' +
                ", tableNo='" + tableNo + '\'' +
                ", clientID='" + clientID + '\'' +
                ", orderType=" + orderType +
                ", dishOrderList=" + dishOrder +
                ", dateTime='" + dateTime + '\'' +
                '}';
    }
}