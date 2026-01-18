package kz.danik.edition.entity;

import io.jmix.core.DeletePolicy;
import io.jmix.core.entity.annotation.OnDeleteInverse;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;

import java.util.Date;

@JmixEntity
@Table(name = "EDT_PRODUCT_BOOKING", indexes = {
        @Index(name = "IDX_EDT_PRODUCT_BOOKING_PRODUCT", columnList = "PRODUCT_ID")
})
@Entity(name = "edt_ProductBooking")
public class ProductBooking extends AbstractDictionary {
    @Column(name = "STATUS")
    private String status;

    @OnDeleteInverse(DeletePolicy.CASCADE)
    @JoinColumn(name = "PRODUCT_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Product product;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_FROM")
    private Date dateFrom;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_TO")
    private Date dateTo;

    @Column(name = "HOURS_FROM")
    private Integer hoursFrom;

    @Column(name = "HOURS_TO")
    private Integer hoursTo;

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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public BookingStatus getStatus() {
        return status == null ? null : BookingStatus.fromId(status);
    }

    public void setStatus(BookingStatus status) {
        this.status = status == null ? null : status.getId();
    }

}