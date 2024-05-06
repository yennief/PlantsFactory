package org.justynafraczek.plantsshop.warehouse;

import org.apache.cxf.service.model.FaultInfo;
import org.justynafraczek.plantsshop.types.Fault;
import org.justynafraczek.plantsshop.types.OrderItem;
import org.justynafraczek.plantsshop.types.OrderItemsList;
// import org.justynafraczek.plantsshop.types.GetPlantsResponse;
import org.justynafraczek.plantsshop.types.Plant;
import org.justynafraczek.plantsshop.types.PlantsOrder;
import org.justynafraczek.plantsshop.types.ProcessingEvent;
import org.justynafraczek.plantsshop.types.ProcessingState;
import org.justynafraczek.plantsshop.NewPlantsOrderResponse;
import org.justynafraczek.plantsshop.CancelOrderRequest;
import org.justynafraczek.plantsshop.CancelOrderResponse;
import org.justynafraczek.plantsshop.GetPlantRequest;
import org.justynafraczek.plantsshop.GetPlantResponse;
import org.justynafraczek.plantsshop.GetPlantsRequest;
import org.justynafraczek.plantsshop.GetPlantsResponse;
import org.justynafraczek.plantsshop.PlantsOrderRequest;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@javax.jws.WebService(targetNamespace = "http://www.plantsshop.justynafraczek.org/", name = "PlantsWarehouse")
@org.springframework.stereotype.Service
public class PlantsWarehouseEndpoint implements PlantsWarehouse {

	@Autowired
	PlantsDatabase plantsDatabase;

	@Autowired
	KafkaProducer kafkaProducer;

	@Autowired
	KafkaConsumer kafkaConsumer;

	@Autowired
	OrdersDatabase ordersDatabase;

	public ObjectMapper mapper = new ObjectMapper();

	@Override
	public NewPlantsOrderResponse newOrder(PlantsOrderRequest plantsOrderRequest)
			throws OrderFaultMsg {

		NewPlantsOrderResponse plantsOrderSummary = new NewPlantsOrderResponse();
		plantsOrderSummary.setOrderID(plantsOrderRequest.getPlantsOrder().getId());

		System.out.println("PlantsWarehouseService.newOrder: " + plantsOrderRequest);

		PlantsOrder order = plantsOrderRequest.getPlantsOrder();

		OrderItemsList plants = order.getItems();

		for (OrderItem plant : plants.getItem()) {
			System.out.println("Plant: " + plant);
			if (!plantsDatabase.hasEnough(plant.getPlantId(), plant.getQuantity())) {
				plantsOrderSummary.setTotalCost(0);
				plantsOrderSummary.setEvent(ProcessingEvent.STORE_NOT_ENOUGH_PLANTS);
				return plantsOrderSummary;
			} else {
				Plant p = plantsDatabase.getPlant(plant.getPlantId());
				plantsOrderSummary.setTotalCost(plantsOrderSummary.getTotalCost() + plant.getQuantity() * p.getPrice());
			}
		}

		for (OrderItem plant : plants.getItem()) {
			plantsDatabase.removePlant(plant.getPlantId(), plant.getQuantity());
		}

		plantsOrderSummary.setEvent(ProcessingEvent.STORE_ENOUGH_PLANTS);

		plantsOrderRequest.getPlantsOrder().setStatus(ProcessingState.AWAITING_PAYMENT);
		ordersDatabase.addOrder(plantsOrderRequest);

		try {
			String res = mapper.writeValueAsString(plantsOrderSummary);

			kafkaProducer.sendMessage(res);

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return plantsOrderSummary;
	}

	public GetPlantsResponse getPlants(GetPlantsRequest o) throws OrderFaultMsg {
		GetPlantsResponse getPlantsResponse = new GetPlantsResponse();
		for (Plant plant : plantsDatabase.getPlants()) {
			getPlantsResponse.getPlants().add(plant);
		}
		return getPlantsResponse;
	}

	@Override
	public GetPlantResponse getPlant(GetPlantRequest getPlantRequest) throws OrderFaultMsg {
		GetPlantResponse gpr = new GetPlantResponse();
		for (Plant plant : plantsDatabase.getPlants()) {
			if (plant.getId().equals(getPlantRequest.getPlantID())) {
				gpr.setPlant(plant);
				return gpr;
			}
		}
		return new GetPlantResponse();
	}

	private void refundOrder(String orderID) {
		ordersDatabase.setOrderStatus(orderID, ProcessingState.REFUNDING_ORDER);
	}

	@Override
	public CancelOrderResponse cancelOrder(CancelOrderRequest cancelOrderRequest) throws OrderFaultMsg {
		if (!ordersDatabase.contains(cancelOrderRequest.getOrderID())) {
			throw new OrderFaultMsg("Order " + cancelOrderRequest.getOrderID() + " does not exists");
		}

		String ID = cancelOrderRequest.getOrderID();
		PlantsOrder po = ordersDatabase.getOrder(ID).getPlantsOrder();
		CancelOrderResponse cor = new CancelOrderResponse();
		cor.setOrderID(ID);
		if (po.getStatus() == ProcessingState.ORDER_BEING_PREPARED) {
			refundOrder(ID);
			cor.setStatus(ProcessingState.REFUNDING_ORDER);
		} else if (po.getStatus() == ProcessingState.AWAITING_PAYMENT) {
			cor.setStatus(ProcessingState.ORDER_CANCELLED);
		}

		plantsDatabase.restorePlants(ID);

		return cor;
	}
}