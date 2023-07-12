package com.example.jsonprocessingex.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

@Component("json_format_converter")
public class JSONFormatConverter implements FormatConverter {

    private final GsonBuilder gsonBuilder;
    private Gson gson;

    @Autowired
    public JSONFormatConverter(GsonBuilder gsonBuilder) {
        this.gsonBuilder = gsonBuilder;
        this.gson = null;
    }

    @Override
    public void setPrettyPrinting() {
        gsonBuilder.setPrettyPrinting();
        this.gson = null;
    }


    @Override
    public String serialize(Object o) {
        return this.getGson().toJson(o);
    }

    @Override
    public void serialize(Object o, String fileName) throws IOException {
        try (FileWriter fw = new FileWriter(fileName)) {
            this.getGson().toJson(o, fw);
        }
    }

    @Override
    public <T> T deserialize(String input, Class<T> clazz) {
        return this.getGson().fromJson(input, clazz);
    }

    @Override
    public <T> T deserializeFromFile(String fileName, Class<T> clazz) throws IOException {
        try (FileReader fileReader = new FileReader(fileName)) {
            return this.getGson().fromJson(fileReader, clazz);
        }
    }

    private Gson getGson() {
        if (this.gson == null) {
            this.gson = this.gsonBuilder.create();
        }
        return this.gson;
    }
}
