
package org.justynafraczek.plantsshop;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import org.justynafraczek.plantsshop.types.Fault;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.justynafraczek.plantsshop package. 
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

    private final static QName _OrderFault_QNAME = new QName("http://www.plantsshop.justynafraczek.org", "orderFault");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.justynafraczek.plantsshop
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PlantsOrderRequest }
     * 
     */
    public PlantsOrderRequest createPlantsOrderRequest() {
        return new PlantsOrderRequest();
    }

    /**
     * Create an instance of {@link NewPlantsOrderResponse }
     * 
     */
    public NewPlantsOrderResponse createNewPlantsOrderResponse() {
        return new NewPlantsOrderResponse();
    }

    /**
     * Create an instance of {@link CancelOrderRequest }
     * 
     */
    public CancelOrderRequest createCancelOrderRequest() {
        return new CancelOrderRequest();
    }

    /**
     * Create an instance of {@link CancelOrderResponse }
     * 
     */
    public CancelOrderResponse createCancelOrderResponse() {
        return new CancelOrderResponse();
    }

    /**
     * Create an instance of {@link GetPlantRequest }
     * 
     */
    public GetPlantRequest createGetPlantRequest() {
        return new GetPlantRequest();
    }

    /**
     * Create an instance of {@link GetPlantResponse }
     * 
     */
    public GetPlantResponse createGetPlantResponse() {
        return new GetPlantResponse();
    }

    /**
     * Create an instance of {@link GetPlantsRequest }
     * 
     */
    public GetPlantsRequest createGetPlantsRequest() {
        return new GetPlantsRequest();
    }

    /**
     * Create an instance of {@link GetPlantsResponse }
     * 
     */
    public GetPlantsResponse createGetPlantsResponse() {
        return new GetPlantsResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Fault }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Fault }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.plantsshop.justynafraczek.org", name = "orderFault")
    public JAXBElement<Fault> createOrderFault(Fault value) {
        return new JAXBElement<Fault>(_OrderFault_QNAME, Fault.class, null, value);
    }

}
