
package org.justynafraczek.plantsshop;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.justynafraczek.plantsshop.types.Plant;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;element name="plant" type="{http://www.plantsshop.justynafraczek.org/types}plant"/&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "plant"
})
@XmlRootElement(name = "getPlantResponse")
public class GetPlantResponse {

    @XmlElement(required = true)
    protected Plant plant;

    /**
     * Gets the value of the plant property.
     * 
     * @return
     *     possible object is
     *     {@link Plant }
     *     
     */
    public Plant getPlant() {
        return plant;
    }

    /**
     * Sets the value of the plant property.
     * 
     * @param value
     *     allowed object is
     *     {@link Plant }
     *     
     */
    public void setPlant(Plant value) {
        this.plant = value;
    }

}
