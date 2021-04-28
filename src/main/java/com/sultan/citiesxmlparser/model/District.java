package com.sultan.citiesxmlparser.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class District {

    @Id
    private String code;

    private String name;

    private String municipalityCode;
}
