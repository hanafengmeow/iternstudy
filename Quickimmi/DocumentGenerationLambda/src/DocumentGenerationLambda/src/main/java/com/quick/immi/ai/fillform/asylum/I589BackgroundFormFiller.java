package com.quick.immi.ai.fillform.asylum;

import com.quick.immi.ai.entity.asylum.i589.*;
import com.quick.immi.ai.utils.FormFillUtils;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class I589BackgroundFormFiller {

    private PDDocument pdDocument;
    private Properties properties;

    public I589BackgroundFormFiller(PDDocument pdDocument, Properties properties) {
        this.pdDocument = pdDocument;
        this.properties = properties;
    }

    public void fillBackground(Background background) throws IOException, IllegalAccessException {
        List<AddressHistory> addressHistoriesBeforeUS = background.getAddressHistoriesBeforeUS();
        for(int i = 0; i < 2 && i < addressHistoriesBeforeUS.size(); i++){
            AddressHistory addressHistory = addressHistoriesBeforeUS.get(i);
            FormFillUtils.fillAllTextField(pdDocument, properties,
                    String.format("%s.%s", "background.addressHistoriesBeforeUS", i + 1),
                    addressHistory);
        }

        List<AddressHistory> usAddressHistoriesPast5Years = background.getUsAddressHistoriesPast5Years();
        for(int i = 0; i < 5 && i < usAddressHistoriesPast5Years.size(); i++){
            AddressHistory addressHistory = usAddressHistoriesPast5Years.get(i);

            FormFillUtils.fillAllTextField(pdDocument, properties,
                    String.format("%s.%s", "background.usAddressHistoriesPast5Years", i + 1),
                    addressHistory);
        }

        List<EducationHistory> educationHistories = background.getEducationHistories();
        for(int i = 0; i < 4 && i < educationHistories.size(); i++){
            EducationHistory educationHistory = educationHistories.get(i);

            FormFillUtils.fillAllTextField(pdDocument, properties,
                    String.format("%s.%s", "background.educationHistories", i + 1),
                    educationHistory);
        }

        List<EmploymentHistory> employmentHistories = background.getEmploymentHistories();

        for(int i = 0; i < 3 && i < employmentHistories.size(); i++){
            EmploymentHistory educationHistory = employmentHistories.get(i);

            FormFillUtils.fillAllTextField(pdDocument, properties,
                    String.format("%s.%s", "background.employmentHistories", i + 1),
                    educationHistory);
        }

        FormFillUtils.fillAllTextField(pdDocument, properties, "background.mother",  background.getMother());
        FormFillUtils.fillAllTextField(pdDocument, properties, "background.father",  background.getFather());

        List<FamilyMember> siblings = background.getSiblings();

        for(int i = 0; i < 4 && i < siblings.size(); i++){
            FamilyMember familyMember = siblings.get(i);

            FormFillUtils.fillAllTextField(pdDocument, properties,
                    String.format("%s.%s", "background.siblings", i + 1),
                    familyMember);
        }
    }
}
