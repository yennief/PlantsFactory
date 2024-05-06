
package org.justynafraczek.plantsshop.types;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for processingEvent.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="processingEvent"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="START"/&gt;
 *     &lt;enumeration value="STORE_ENOUGH_PLANTS"/&gt;
 *     &lt;enumeration value="STORE_NOT_ENOUGH_PLANTS"/&gt;
 *     &lt;enumeration value="PAYMENT_TIMED_OUT"/&gt;
 *     &lt;enumeration value="PAYMENT_DECLINED"/&gt;
 *     &lt;enumeration value="PAYMENT_SUCCESSFUL"/&gt;
 *     &lt;enumeration value="CANCEL_ORDER"/&gt;
 *     &lt;enumeration value="COMPLETE"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "processingEvent")
@XmlEnum
public enum ProcessingEvent {

    START,
    STORE_ENOUGH_PLANTS,
    STORE_NOT_ENOUGH_PLANTS,
    PAYMENT_TIMED_OUT,
    PAYMENT_DECLINED,
    PAYMENT_SUCCESSFUL,
    CANCEL_ORDER,
    COMPLETE;

    public String value() {
        return name();
    }

    public static ProcessingEvent fromValue(String v) {
        return valueOf(v);
    }

}
