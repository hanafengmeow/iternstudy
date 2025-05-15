package com.quick.immi.ai.fillform;

public interface PDFFormFiller {

    void init();

    void fillDocument();

    String saveResult();
}
