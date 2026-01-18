package kz.danik.edition.dto;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.entity.annotation.JmixId;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public abstract class AbstractDto {
    @JmixGeneratedValue
    @JmixId
    protected UUID id;
}
