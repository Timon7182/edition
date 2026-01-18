package kz.danik.edition.entity;

import io.jmix.core.DeletePolicy;
import io.jmix.core.entity.annotation.OnDeleteInverse;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

@JmixEntity
@Table(name = "EDT_PRODUCT_REQUEST", indexes = {
        @Index(name = "IDX_EDT_PRODUCT_REQUEST_REQUEST", columnList = "REQUEST_ID")
})
@Entity(name = "edt_ProductRequest")
public class ProductRequest extends AbstractDictionary {

    @Column(name = "PRICE")
    private BigDecimal price;

    @Column(name = "HOURS_FROM")
    private Integer hoursFrom;

    @Column(name = "HOURS_TO")
    private Integer hoursTo;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_FROM")
    private Date dateFrom;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_TO")
    private Date dateTo;

    @JoinColumn(name = "PRODUCT_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @OnDeleteInverse(DeletePolicy.CASCADE)
    @JoinColumn(name = "REQUEST_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Request request;

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getHoursTo() {
        return hoursTo;
    }

    public void setHoursTo(Integer hoursTo) {
        this.hoursTo = hoursTo;
    }

    public Integer getHoursFrom() {
        return hoursFrom;
    }

    public void setHoursFrom(Integer hoursFrom) {
        this.hoursFrom = hoursFrom;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

}