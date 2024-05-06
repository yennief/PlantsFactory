
package org.justynafraczek.plantsshop.types;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.justynafraczek.plantsshop.types package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.justynafraczek.plantsshop.types
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Plant }
     * 
     */
    public Plant createPlant() {
        return new Plant();
    }

    /**
     * Create an instance of {@link PlantsOrder }
     * 
     */
    public PlantsOrder createPlantsOrder() {
        return new PlantsOrder();
    }

    /**
     * Create an instance of {@link OrderItemsList }
     * 
     */
    public OrderItemsList createOrderItemsList() {
        return new OrderItemsList();
    }

    /**
     * Create an instance of {@link OrderItem }
     * 
     */
    public OrderItem createOrderItem() {
        return new OrderItem();
    }

    /**
     * Create an instance of {@link Fault }
     * 
     */
    public Fault createFault() {
        return new Fault();
    }

}
