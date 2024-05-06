package org.justynafraczek.plantsshop.warehouse;

import java.util.HashMap;

import org.justynafraczek.plantsshop.PlantsOrderRequest;
import org.justynafraczek.plantsshop.types.ProcessingState;
import org.springframework.stereotype.Service;

@Service
public class OrdersDatabase {

    private HashMap<String, PlantsOrderRequest> plantsOrders = new HashMap<>();

    public void addOrder(PlantsOrderRequest order) {
        plantsOrders.put(order.getPlantsOrder().getId(), order);
    }

    public PlantsOrderRequest getOrder(String orderID) {
        return plantsOrders.get(orderID);
    }

    public boolean contains(String orderID) {
        return plantsOrders.containsKey(orderID);
    }

    public void setOrderStatus(String orderID, ProcessingState state) {
        plantsOrders.get(orderID).getPlantsOrder().setStatus(state);
    }

    public void removeOrder(String orderID) {
        plantsOrders.remove(orderID);
    }

}
