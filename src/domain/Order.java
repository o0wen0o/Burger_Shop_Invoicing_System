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

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getTableNo() {
        return tableNo;
    }

    public void setTableNo(String tableNo) {
        this.tableNo = tableNo;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public List<Dish> getDishOrder() {
        return dishOrder;
    }

    public void setDishOrder(List<Dish> dishOrder) {
        this.dishOrder = dishOrder;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
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