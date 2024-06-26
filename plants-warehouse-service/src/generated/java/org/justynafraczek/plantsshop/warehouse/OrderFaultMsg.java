
package org.justynafraczek.plantsshop.warehouse;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 3.3.2
 * 2024-01-10T18:55:18.981+01:00
 * Generated source version: 3.3.2
 */

@WebFault(name = "orderFault", targetNamespace = "http://www.plantsshop.justynafraczek.org")
public class OrderFaultMsg extends Exception {

    private org.justynafraczek.plantsshop.types.Fault orderFault;

    public OrderFaultMsg() {
        super();
    }

    public OrderFaultMsg(String message) {
        super(message);
    }

    public OrderFaultMsg(String message, java.lang.Throwable cause) {
        super(message, cause);
    }

    public OrderFaultMsg(String message, org.justynafraczek.plantsshop.types.Fault orderFault) {
        super(message);
        this.orderFault = orderFault;
    }

    public OrderFaultMsg(String message, org.justynafraczek.plantsshop.types.Fault orderFault, java.lang.Throwable cause) {
        super(message, cause);
        this.orderFault = orderFault;
    }

    public org.justynafraczek.plantsshop.types.Fault getFaultInfo() {
        return this.orderFault;
    }
}
