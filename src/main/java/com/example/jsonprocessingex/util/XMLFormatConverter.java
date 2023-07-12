package com.example.jsonprocessingex.util;

import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;

@Component("xml_format_converter")
public class XMLFormatConverter implements FormatConverter {
    private boolean prettyPrint = false;


    @Override
    public void setPrettyPrinting() {
        this.prettyPrint = true;
    }

    @Override
    public String serialize(Object o) {
        try (StringWriter sw = new StringWriter()) {
            JAXBContext jaxbContext = JAXBContext.newInstance(o.getClass());
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, this.prettyPrint);
            marshaller.marshal(o, sw);
            return sw.toString();
        } catch (JAXBException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void serialize(Object o, String fileName) throws IOException {
        try (FileWriter fw = new FileWriter(fileName)) {
            JAXBContext jaxbContext = JAXBContext.newInstance(o.getClass());
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, this.prettyPrint);
            marshaller.marshal(o, fw);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T deserialize(String input, Class<T> clazz) throws IOException {
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes())) {
            JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            unmarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, this.prettyPrint);
            return (T) unmarshaller.unmarshal(inputStream);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T deserializeFromFile(String fileName, Class<T> clazz) throws IOException {
        try (FileReader fileReader = new FileReader(fileName)) {
            JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            unmarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, this.prettyPrint);
            return (T) unmarshaller.unmarshal(fileReader);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }
}
