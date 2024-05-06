
package org.justynafraczek.plantsshop.types;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for processingState.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="processingState"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="NONE"/&gt;
 *     &lt;enumeration value="ORDER_PLACED"/&gt;
 *     &lt;enumeration value="AWAITING_PAYMENT"/&gt;
 *     &lt;enumeration value="ORDER_BEING_PREPARED"/&gt;
 *     &lt;enumeration value="REFUNDING_ORDER"/&gt;
 *     &lt;enumeration value="ORDER_CANCELLED"/&gt;
 *     &lt;enumeration value="ORDER_FINISHED"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "processingState")
@XmlEnum
public enum ProcessingState {

    NONE,
    ORDER_PLACED,
    AWAITING_PAYMENT,
    ORDER_BEING_PREPARED,
    REFUNDING_ORDER,
    ORDER_CANCELLED,
    ORDER_FINISHED;

    public String value() {
        return name();
    }

    public static ProcessingState fromValue(String v) {
        return valueOf(v);
    }

}
