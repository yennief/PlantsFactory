
package org.justynafraczek.plantsshop.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for plantsOrder complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="plantsOrder"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="total_cost" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="items" type="{http://www.plantsshop.justynafraczek.org/types}orderItemsList"/&gt;
 *         &lt;element name="status" type="{http://www.plantsshop.justynafraczek.org/types}processingState"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "plantsOrder", propOrder = {
    "id",
    "totalCost",
    "items",
    "status"
})
public class PlantsOrder {

    @XmlElement(required = true)
    protected String id;
    @XmlElement(name = "total_cost")
    protected double totalCost;
    @XmlElement(required = true)
    protected OrderItemsList items;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected ProcessingState status;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the totalCost property.
     * 
     */
    public double getTotalCost() {
        return totalCost;
    }

    /**
     * Sets the value of the totalCost property.
     * 
     */
    public void setTotalCost(double value) {
        this.totalCost = value;
    }

    /**
     * Gets the value of the items property.
     * 
     * @return
     *     possible object is
     *     {@link OrderItemsList }
     *     
     */
    public OrderItemsList getItems() {
        return items;
    }

    /**
     * Sets the value of the items property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrderItemsList }
     *     
     */
    public void setItems(OrderItemsList value) {
        this.items = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link ProcessingState }
     *     
     */
    public ProcessingState getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProcessingState }
     *     
     */
    public void setStatus(ProcessingState value) {
        this.status = value;
    }

}
