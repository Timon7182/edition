package kz.danik.edition.entity;

import io.jmix.core.metamodel.annotation.JmixEntity;
import kz.danik.edition.dto.AbstractDto;
import lombok.Getter;
import lombok.Setter;


@JmixEntity(name = "edt_ImageDto")
@Getter
@Setter
public class ImageDto extends AbstractDto {

    public ImageDto(String image, Integer order) {
        this.image = image;
        this.order = order;
    }

    protected String image;
    protected Integer order;


}