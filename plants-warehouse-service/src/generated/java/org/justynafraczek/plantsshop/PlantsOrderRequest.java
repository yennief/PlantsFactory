
package org.justynafraczek.plantsshop;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.justynafraczek.plantsshop.types.PlantsOrder;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;element name="plantsOrder" type="{http://www.plantsshop.justynafraczek.org/types}plantsOrder"/&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "plantsOrder"
})
@XmlRootElement(name = "plantsOrderRequest")
public class PlantsOrderRequest {

    @XmlElement(required = true)
    protected PlantsOrder plantsOrder;

    /**
     * Gets the value of the plantsOrder property.
     * 
     * @return
     *     possible object is
     *     {@link PlantsOrder }
     *     
     */
    public PlantsOrder getPlantsOrder() {
        return plantsOrder;
    }

    /**
     * Sets the value of the plantsOrder property.
     * 
     * @param value
     *     allowed object is
     *     {@link PlantsOrder }
     *     
     */
    public void setPlantsOrder(PlantsOrder value) {
        this.plantsOrder = value;
    }

}
