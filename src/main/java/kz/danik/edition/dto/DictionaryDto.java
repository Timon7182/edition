package kz.danik.edition.dto;

import io.jmix.core.metamodel.annotation.JmixEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JmixEntity(name = "edt__DictionaryDto")
public class DictionaryDto extends AbstractDto{

    protected String langValue;

    protected String code;

    protected String description;
    protected Integer order;
}
