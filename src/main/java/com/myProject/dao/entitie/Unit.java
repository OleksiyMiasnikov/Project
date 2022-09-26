package com.myProject.dao.entitie;

import java.util.HashMap;
import java.util.Map;

public enum Unit {
    KG ("кг", "kg"),
    PCS ("шт", "pcs");

    private static final Map<String, Unit> BY_LABEL = new HashMap<>();

    static {
        for (Unit e: values()) {
            BY_LABEL.put(e.labelUa, e);
        }
    }

    public final String labelUa;
    public final String labelUk;

    Unit(String labelUa, String labelUk) {
        this.labelUa = labelUa;
        this.labelUk = labelUk;
    }

    public String getLabelUa() {
        return labelUa;
    }

    public String getLabelUk() {
        return labelUk;
    }

    public static Unit valueOfLabel(String label) {
        return BY_LABEL.get(label);
    }
}

