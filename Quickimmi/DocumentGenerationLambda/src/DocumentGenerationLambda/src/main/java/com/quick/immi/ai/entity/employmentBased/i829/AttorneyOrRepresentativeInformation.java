package com.quick.immi.ai.entity.employmentBased.i829;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttorneyOrRepresentativeInformation {

    /**
     * Indicates if Form G-28 is attached.
     * Expected values: "Yes" or "No"
     */
    private String formG28Attached;

    /**
     * Attorney's State Bar Number, if applicable.
     */
    private String attorneyStateBarNumber;

    /**
     * Attorney or Accredited Representative USCIS Online Account Number, if any.
     */
    private String uscisOnlineAccountNumber;
}