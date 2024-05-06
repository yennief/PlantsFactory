
package org.justynafraczek.plantsshop;

import java.util.ArrayList;
import java.util.List;
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
 *       &lt;sequence&gt;
 *         &lt;element name="plants" type="{http://www.plantsshop.justynafraczek.org/types}plant" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "plants"
})
@XmlRootElement(name = "getPlantsResponse")
public class GetPlantsResponse {

    @XmlElement(required = true)
    protected List<Plant> plants;

    /**
     * Gets the value of the plants property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the plants property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPlants().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Plant }
     * 
     * 
     */
    public List<Plant> getPlants() {
        if (plants == null) {
            plants = new ArrayList<Plant>();
        }
        return this.plants;
    }

}
