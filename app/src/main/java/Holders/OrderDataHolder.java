package Holders;

import PizzaApp.Order;

public class OrderDataHolder {

    private Order order = null;
    private static final OrderDataHolder data = new OrderDataHolder();

    private OrderDataHolder(){
        order = new Order();
    }

    public static OrderDataHolder getOrderDataHolder(){
        return data;
    }

    public Order getOrder(){
        return order;
    }
}