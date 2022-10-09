package com.myProject.entitie;

public enum Unit {
    KG ("kg"),
    PCS ("pcs");
    private String label;

    Unit(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}

