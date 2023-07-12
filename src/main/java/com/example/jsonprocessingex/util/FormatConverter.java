package com.example.jsonprocessingex.util;

import java.io.IOException;

public interface FormatConverter {
    void setPrettyPrinting();

    String serialize(Object o);

    void serialize(Object o, String fileName) throws IOException;

    <T> T deserialize(String input, Class<T> clazz) throws IOException;

    <T> T deserializeFromFile(String fileName, Class<T> clazz) throws IOException;

}
