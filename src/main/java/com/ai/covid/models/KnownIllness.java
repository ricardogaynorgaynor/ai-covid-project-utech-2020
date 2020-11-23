package com.ai.covid.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

public class KnownIllness {
    private Long id;
    private String name;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public KnownIllness(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public KnownIllness() {
    }

    public static List<KnownIllness> getIllnesses(){
        return Arrays.asList(
                new KnownIllness(1L, "Heart Condition"),
                new KnownIllness(2L, "Weakened Immune System"),
                new KnownIllness(3L, "Obesity"),
                new KnownIllness(4L, "Pregnancy"),
                new KnownIllness(5L, "Sickle Cell Disease"),
                new KnownIllness(6L, "Smoking"),
                new KnownIllness(7L, "Diabetes"),
                new KnownIllness(8L, "Asthma"),
                new KnownIllness(9L, "Cerebrovascular Disease"),
                new KnownIllness(9L, "Cystic Fibrosis"),
                new KnownIllness(9L, "Hypertension or High Blood Pressure"),
                new KnownIllness(9L, "Neurologic Conditions"),
                new KnownIllness(9L, "Liver Disease"),
                new KnownIllness(9L, "Pulmonary Fibrosis"),
                new KnownIllness(9L, "Thalassemia")
        );
    }
}
