package com.quick.immi.ai.entity.i485;

import lombok.Data;

@Data
public class Benefit {
    private String pageNumber = "20";
    private String partNumber = "9";
    private String itemNumber = "65";

    private String benefitReceived;
    private String startDate;
    private String endDate;
    private String dollarAmount;
    private String isExemptFromPublicChargeYes;
    private String isExemptFromPublicChargeNo;
}
