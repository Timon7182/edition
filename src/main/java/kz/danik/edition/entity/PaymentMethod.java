package kz.danik.edition.entity;

import io.jmix.core.metamodel.datatype.EnumClass;

import org.springframework.lang.Nullable;


public enum PaymentMethod implements EnumClass<String> {

    CASH("CASH"),
    CARD("CARD");

    private final String id;

    PaymentMethod(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static PaymentMethod fromId(String id) {
        for (PaymentMethod at : PaymentMethod.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}