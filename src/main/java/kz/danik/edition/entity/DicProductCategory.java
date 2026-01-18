package kz.danik.edition.entity;

import io.jmix.core.MetadataTools;
import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.core.metamodel.annotation.JmixProperty;
import jakarta.persistence.*;

@JmixEntity
@Table(name = "EDT_DIC_PRODUCT_CATEGORY")
@Entity(name = "edt_DicProductCategory")
public class DicProductCategory extends AbstractDictionary {

    @Lob
    @Column(name = "DESCRIPTION_RU")
    private String descriptionRu;

    @Lob
    @Column(name = "DESCRIPTION_KZ")
    private String descriptionKz;

    @Lob
    @Column(name = "DESCRIPTION_EN")
    private String descriptionEn;

    @JmixProperty
    @Transient
    private String description;

    @Column(name = "ORDER_")
    private Integer order;

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescriptionEn() {
        return descriptionEn;
    }

    public void setDescriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
    }

    public String getDescriptionKz() {
        return descriptionKz;
    }

    public void setDescriptionKz(String descriptionKz) {
        this.descriptionKz = descriptionKz;
    }

    public String getDescriptionRu() {
        return descriptionRu;
    }

    public void setDescriptionRu(String descriptionRu) {
        this.descriptionRu = descriptionRu;
    }

    @InstanceName
    @DependsOnProperties({"langValueRu"})
    public String getInstanceName(MetadataTools metadataTools) {
        return metadataTools.format(getLangValueRu());
    }
}