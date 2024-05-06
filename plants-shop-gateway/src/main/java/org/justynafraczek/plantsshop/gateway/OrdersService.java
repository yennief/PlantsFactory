package org.justynafraczek.plantsshop.gateway;

import org.springframework.stereotype.Service;
import org.justynafraczek.plantsshop.gateway.models.OrderRequest;
import org.justynafraczek.plantsshop.types.OrderItem;
import org.justynafraczek.plantsshop.types.OrderItemsList;
import org.justynafraczek.plantsshop.types.PlantsOrder;
import org.justynafraczek.plantsshop.types.ProcessingState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
public class OrdersService {
	HashMap<String, PlantsOrder> orders = new HashMap<>();

	private String generateOrderId() {
		return UUID.randomUUID().toString();
	}

    public String createNewOrder(OrderRequest orderRequest) {

        String orderId = generateOrderId();

        PlantsOrder order = new PlantsOrder();
        order.setId(orderId);
        OrderItemsList oil = new OrderItemsList();
        for (OrderItem item : orderRequest.getItems()) {
            oil.getItem().add(item);
        }
        order.setItems(oil);
        orders.put(orderId, order);

        return orderId;

    }

    public PlantsOrder getOrder(String orderId) {
        return orders.get(orderId);
    }

    public List<PlantsOrder> getAllOrders() {
        return new ArrayList<>(orders.values());
    }

    public void setOrderStatus(String orderid, ProcessingState state) {
        PlantsOrder order = orders.get(orderid);
        order.setStatus(state);
    }

    public void setOrderTotalCost(String orderid, double totalCost) {
        PlantsOrder order = orders.get(orderid);
        order.setTotalCost(totalCost);
    }

}