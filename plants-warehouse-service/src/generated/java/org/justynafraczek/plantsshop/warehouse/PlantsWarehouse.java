package org.justynafraczek.plantsshop.warehouse;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 3.3.2
 * 2024-01-10T18:55:19.001+01:00
 * Generated source version: 3.3.2
 *
 */
@WebService(targetNamespace = "http://www.plantsshop.justynafraczek.org/warehouse/", name = "PlantsWarehouse")
@XmlSeeAlso({org.justynafraczek.plantsshop.ObjectFactory.class, org.justynafraczek.plantsshop.types.ObjectFactory.class})
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface PlantsWarehouse {

    @WebMethod(action = "http://www.plantsshop.justynafraczek.org/warehouse/newOrder")
    @WebResult(name = "newPlantsOrderResponse", targetNamespace = "http://www.plantsshop.justynafraczek.org", partName = "payload")
    public org.justynafraczek.plantsshop.NewPlantsOrderResponse newOrder(

        @WebParam(partName = "payload", name = "plantsOrderRequest", targetNamespace = "http://www.plantsshop.justynafraczek.org")
        org.justynafraczek.plantsshop.PlantsOrderRequest payload
    ) throws OrderFaultMsg;

    @WebMethod(action = "http://www.plantsshop.justynafraczek.org/warehouse/getPlants")
    @WebResult(name = "getPlantsResponse", targetNamespace = "http://www.plantsshop.justynafraczek.org", partName = "payload")
    public org.justynafraczek.plantsshop.GetPlantsResponse getPlants(

        @WebParam(partName = "payload", name = "getPlantsRequest", targetNamespace = "http://www.plantsshop.justynafraczek.org")
        org.justynafraczek.plantsshop.GetPlantsRequest payload
    ) throws OrderFaultMsg;

    @WebMethod(action = "http://www.plantsshop.justynafraczek.org/warehouse/getPlant")
    @WebResult(name = "getPlantResponse", targetNamespace = "http://www.plantsshop.justynafraczek.org", partName = "payload")
    public org.justynafraczek.plantsshop.GetPlantResponse getPlant(

        @WebParam(partName = "payload", name = "getPlantRequest", targetNamespace = "http://www.plantsshop.justynafraczek.org")
        org.justynafraczek.plantsshop.GetPlantRequest payload
    ) throws OrderFaultMsg;

    @WebMethod(action = "http://www.plantsshop.justynafraczek.org/warehouse/cancelOrder")
    @WebResult(name = "cancelOrderResponse", targetNamespace = "http://www.plantsshop.justynafraczek.org", partName = "payload")
    public org.justynafraczek.plantsshop.CancelOrderResponse cancelOrder(

        @WebParam(partName = "payload", name = "cancelOrderRequest", targetNamespace = "http://www.plantsshop.justynafraczek.org")
        org.justynafraczek.plantsshop.CancelOrderRequest payload
    ) throws OrderFaultMsg;
}
