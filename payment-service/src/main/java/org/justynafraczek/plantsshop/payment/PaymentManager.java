package org.justynafraczek.plantsshop.payment;

import java.util.ArrayList;
import java.util.List;

import org.justynafraczek.plantsshop.payment.models.*;

public class PaymentManager {
    private static PaymentManager instance;

    private List<WarehouseOrderUpdate> orders = new ArrayList<>();

    // Private constructor to prevent instantiation from outside the class
    private PaymentManager() {
    }

    public void addOrder(WarehouseOrderUpdate order) {
        orders.add(order);
    }

    public WarehouseOrderUpdate getOrder(String orderId) {
        return orders.stream().filter(o -> o.getOrderId().equals(orderId)).findFirst().get();
    }

    public boolean pay(String orderId, PaymentCard paymentCard) {
        WarehouseOrderUpdate order = orders.stream().filter(o -> o.getOrderId().equals(orderId)).findFirst().get();
        if (order == null) {
            return false;
        }
        if (paymentCard.getNumber().charAt(0) == '4' || paymentCard.getNumber().charAt(0) == '5') {
            order.setEvent("PAYMENT_SUCCESSFUL");
            return true;
        } else {
            order.setEvent("PAYMENT_DECLINED");
            return false;
        }
    }

    public void cancelOrder(String orderId) {
        WarehouseOrderUpdate order = orders.stream().filter(o -> o.getOrderId().equals(orderId)).findFirst().get();
        if (order == null) {
            return;
        }
        // write a code to remove object from orders list
        orders.remove(order);
    }

    // Public method to get the instance of the Singleton class
    public static PaymentManager getInstance() {
        if (instance == null) {
            instance = new PaymentManager();
        }
        return instance;
    }

}
