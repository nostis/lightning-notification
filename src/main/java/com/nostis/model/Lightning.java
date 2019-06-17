package com.nostis.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Id;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Lightning {

    @Id
    private Long id;

    @JsonProperty("occurredAt")
    private Date occurredAt;

    @JsonProperty("provider")
    private String provider;

    @JsonProperty("lightningType")
    private String lightningType;

    @JsonProperty("elevationInKilometers")
    private int elevationInKilometers;

    @JsonProperty("currentInAmpere")
    private int currentInAmpere;

    @JsonProperty("location")
    private List<Float> location;
}
