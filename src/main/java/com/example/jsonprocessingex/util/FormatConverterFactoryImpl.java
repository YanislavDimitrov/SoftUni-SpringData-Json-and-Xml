package com.example.jsonprocessingex.util;

import com.example.jsonprocessingex.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class FormatConverterFactoryImpl implements FormatConverterFactory {

    private final FormatConverter xml;
    private final FormatConverter json;

    @Autowired
    public FormatConverterFactoryImpl(
            @Qualifier("xml_format_converter") FormatConverter xml,
            @Qualifier("json_format_converter") FormatConverter json) {
        this.xml = xml;
        this.json = json;
    }

    @Override
    public FormatConverter getConverter(String formatType) {
        if (formatType.equalsIgnoreCase("xml")) {
            return xml;
        } else if (formatType.equalsIgnoreCase("json")) {
            return json;
        } else {
            throw new InvalidFormatException();
        }
    }
}
