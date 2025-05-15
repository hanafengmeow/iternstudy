package com.quick.immi.ai.entity.employmentBased.i829;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdditionalInformation {

    // Petitioner's Name and A-Number (if any)
    private String familyName;
    private String givenName;
    private String middleName;
    private String aNumber;

    // Additional Information Details (Up to 7 entries as seen in the form)
    private List<Information> addtionalInformation;

    @Data
    public static class Information {
        private String pageNumber;
        private String partNumber;
        private String itemNumber;
        private String content;
    }
}