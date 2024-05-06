package org.justynafraczek.plantsshop.warehouse;

import java.util.ArrayList;
import java.util.List;

// import org.justynafraczek.plantsshop.types.Plant;
import org.justynafraczek.plantsshop.types.Plant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlantsDatabase {

    @Autowired
    OrdersDatabase ordersDatabase;

    private List<Plant> plants = new ArrayList<>();

    public PlantsDatabase() {
        Plant plant = new Plant();
        plant.setId("1");
        plant.setName("Monstera");
        plant.setPrice(100);
        plant.setQuantity(5);
        Plant plant2 = new Plant();
        plant2.setId("2");
        plant2.setName("Tulipan");
        plant2.setPrice(10);
        plant2.setQuantity(10);
        Plant plant3 = new Plant();
        plant3.setId("3");
        plant3.setName("Strelicja");
        plant3.setPrice(150);
        plant3.setQuantity(50);
        plants.add(plant);
        plants.add(plant2);
        plants.add(plant3);
    }

    public List<Plant> getPlants() {
        return plants;
    }

    public Plant getPlant(String plantid) {
        return plants.stream().filter(plant -> plant.getId().equals(plantid)).findFirst().orElse(null);
    }

    public boolean hasEnough(String plantId, int quantity) {
        for (Plant plant : plants) {
            if (plant.getId().equals(plantId)) {
                return plant.getQuantity() > 0;
            }
        }
        return false;
    }

    public void removePlant(String plantId, int quantity) {
        for (Plant plant : plants) {
            if (plant.getId().equals(plantId)) {
                plant.setQuantity(plant.getQuantity() - quantity);
            }
            return;
        }
    }

    public void restorePlants(String orderID) {
        ordersDatabase.getOrder(orderID).getPlantsOrder().getItems().getItem().forEach(item -> {
            for (Plant plant : plants) {
                if (plant.getId().equals(item.getPlantId())) {
                    plant.setQuantity(plant.getQuantity() + item.getQuantity());
                }
            }
        });
    }

}
