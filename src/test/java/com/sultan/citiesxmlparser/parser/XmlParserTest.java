package com.sultan.citiesxmlparser.parser;

import com.sultan.citiesxmlparser.parser.dto.ParsingResult;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class XmlParserTest {

    private final XmlParser xmlParser = new XmlParser();

    @Test
    public void testParse() throws Exception {
        File testXmlFile = new File("src/test/resources/test_xml_file.xml");
        ParsingResult result = xmlParser.parse(testXmlFile);
        assertNotNull(result);
        assertEquals(1, result.getMunicipalities().size());
        assertEquals("573060", result.getMunicipalities().get(0).getCode());
        assertEquals("Kopidlno", result.getMunicipalities().get(0).getName());
        assertEquals(1, result.getDistricts().size());
        assertEquals("69299", result.getDistricts().get(0).getCode());
        assertEquals("Kopidlno", result.getDistricts().get(0).getName());
        assertEquals("573060", result.getDistricts().get(0).getMunicipalityCode());

    }
}