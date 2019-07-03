package com.nostis.lightning_core.model;

import lombok.Data;
import javax.persistence.*;
import java.util.Calendar;
import java.util.List;

@Entity
@Data
public class Lightning {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int count;

    private float distance;

    private String direction;

    private int inter;

    private Calendar created;

    @ElementCollection
    private List<Float> area;

    public Lightning(Long id, int count, float distance, String direction, int inter, Calendar created, List<Float> area) {
        this.id = id;
        this.count = count;
        this.distance = distance;
        this.direction = direction;
        this.inter = inter;
        this.created = created;
        this.area = area;
    }

    public Lightning(){}
}
