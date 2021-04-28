package com.sultan.citiesxmlparser.parser.dto;

import com.sultan.citiesxmlparser.model.District;
import com.sultan.citiesxmlparser.model.Municipality;
import lombok.Value;

import java.util.List;

@Value
public class ParsingResult {
    List<Municipality> municipalities;
    List<District> districts;
}
