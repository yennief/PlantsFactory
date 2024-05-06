package org.justynafraczek.plantsshop.gateway.models;

import java.util.List;

import org.justynafraczek.plantsshop.types.PlantsOrder;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetOrdersResponse {
    @JsonProperty("orders")
    private List<PlantsOrder> orders;

    public List<PlantsOrder> getOrders() {
        return orders;
    }

    public void setOrders(List<PlantsOrder> orders) {
        this.orders = orders;
    }
}
