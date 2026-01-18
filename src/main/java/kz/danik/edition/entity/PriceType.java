package kz.danik.edition.entity;

import io.jmix.core.metamodel.datatype.EnumClass;

import org.springframework.lang.Nullable;


public enum PriceType implements EnumClass<String> {

    DAILY("DAILY"),
    HOUR("HOUR"),
    MONTH("MONTH");

    private final String id;

    PriceType(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static PriceType fromId(String id) {
        for (PriceType at : PriceType.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}