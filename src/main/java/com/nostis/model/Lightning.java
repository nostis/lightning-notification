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

    private int count;

    private float distance;

    private String direction;

    private int interval;

    private Calendar created;

    @ElementCollection
    private List<Float> area;

    public Lightning(Long id, int count, float distance, String direction, int interval, Calendar created, List<Float> area) {
        this.id = id;
        this.count = count;
        this.distance = distance;
        this.direction = direction;
        this.interval = interval;
        this.created = created;
        this.area = area;
    }

    public Lightning(){}
}
