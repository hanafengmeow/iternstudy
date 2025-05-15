package com.quick.immi.ai.entity.i485;

import lombok.Data;

@Data
public class Institutionalization {
    private String pageNumber = "20";
    private String partNumber = "9";
    private String itemNumber = "66";

    private String institutionNameCityState;
    private String dateFrom;
    private String dateTo;
    private String reason;
    private String isExemptFromPublicChargeYes;
    private String isExemptFromPublicChargeNo;
}
