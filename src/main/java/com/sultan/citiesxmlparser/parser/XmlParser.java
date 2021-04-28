package com.sultan.citiesxmlparser.parser;

import com.sultan.citiesxmlparser.model.District;
import com.sultan.citiesxmlparser.model.Municipality;
import com.sultan.citiesxmlparser.parser.dto.ParsingResult;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class XmlParser {

    public ParsingResult parse(File file) throws ParsingException {
        List<Municipality> municipalities = new ArrayList<>();
        List<District> districts = new ArrayList<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        Document document = null;
        try {
            builder = factory.newDocumentBuilder();
            document = builder.parse(file);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new ParsingException("Parsing error", e);
        }

        NodeList municipalityElements = document.getElementsByTagName("vf:Obec");
        for (int i = 0; i < municipalityElements.getLength(); i++) {
            Element municipalityElement = (Element) municipalityElements.item(i);
            String code = municipalityElement.getElementsByTagName("obi:Kod").item(0).getFirstChild().getNodeValue();
            String name = municipalityElement.getElementsByTagName("obi:Nazev").item(0).getFirstChild().getNodeValue();
            Municipality municipality = new Municipality(code, name);
            municipalities.add(municipality);
        }

        NodeList districtElements = document.getElementsByTagName("vf:CastObce");
        for (int i = 0; i < districtElements.getLength(); i ++) {
            Element districtElement = (Element) districtElements.item(i);
            String code = districtElement.getElementsByTagName("coi:Kod").item(0).getFirstChild().getNodeValue();
            String name = districtElement.getElementsByTagName("coi:Nazev").item(0).getFirstChild().getNodeValue();
            Element municipalityElement = (Element) districtElement.getElementsByTagName("coi:Obec").item(0);
            String municipalityCode = municipalityElement.getElementsByTagName("obi:Kod").item(0).getFirstChild().getNodeValue();
            District district = new District(code, name, municipalityCode);
            districts.add(district);
        }

        return new ParsingResult(municipalities, districts);
    }
}
