package com.quick.immi.ai.entity.eoir28;

import lombok.Data;

@Data
public class ProofOfService {
    // AttorneyName - 31
    private String attorneyName;
    // DeliveryDate - 32
    private String deliveryDate;
    // DHSAddress - 33
    private String dhsAddress;
    // Electronic Service Checkbox - Electronic Service 
    private String electronicServiceCheckbox;
}
