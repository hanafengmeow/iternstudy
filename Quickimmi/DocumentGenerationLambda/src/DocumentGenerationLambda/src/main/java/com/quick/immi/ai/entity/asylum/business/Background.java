package com.quick.immi.ai.entity.asylum.business;

import lombok.Data;

import java.util.List;

@Data
public class Background {
    //max 2 Use Form I-589 Supplement B, or additional sheets of paper, if necessary Q1
    private List<AddressHistory> addressHistoriesBeforeUS;
    //max 5 Use Form I-589 Supplement B, or additional sheets of paper, if necessary Q2
    private List<AddressHistory> usAddressHistoriesPast5Years;
    //Use Form I-589 Supplement B, or additional sheets of paper, if necessary.)
    private List<EducationHistory> educationHistories;

    private List<EmploymentHistory> employmentHistories;
}
