package kz.danik.edition.entity;

import io.jmix.core.metamodel.datatype.EnumClass;

import org.springframework.lang.Nullable;


public enum BookingType implements EnumClass<String> {

    DAILY("DAILY"),
    HOUR("HOUR"),
    MONTH("MONTH");

    private final String id;

    BookingType(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static BookingType fromId(String id) {
        for (BookingType at : BookingType.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}