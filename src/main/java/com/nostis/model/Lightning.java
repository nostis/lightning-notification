package com.nostis.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Calendar;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Data
public class Lightning {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonProperty("occurredAt")
    private Calendar occurredAt;

    @JsonProperty("provider")
    private String provider;

    @JsonProperty("lightningType")
    private String lightningType;

    @JsonProperty("elevationInKilometers")
    private int elevationInKilometers;

    @JsonProperty("currentInAmpere")
    private int currentInAmpere;

    @JsonProperty("location")
    @ElementCollection
    private List<Float> location;

    public Lightning(Long id, Calendar occurredAt, String provider, String lightningType, int elevationInKilometers, int currentInAmpere, List<Float> location) {
        this.id = id;
        this.occurredAt = occurredAt;
        this.provider = provider;
        this.lightningType = lightningType;
        this.elevationInKilometers = elevationInKilometers;
        this.currentInAmpere = currentInAmpere;
        this.location = location;
    }

    public Lightning(){}
}
