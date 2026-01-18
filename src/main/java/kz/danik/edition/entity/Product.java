package kz.danik.edition.entity;

import io.jmix.core.DeletePolicy;
import io.jmix.core.MetadataTools;
import io.jmix.core.entity.annotation.OnDelete;
import io.jmix.core.metamodel.annotation.Composition;
import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@JmixEntity
@Table(name = "EDT_PRODUCT", indexes = {
        @Index(name = "IDX_EDT_PRODUCT_CATEGORY", columnList = "CATEGORY_ID")
})
@Entity(name = "edt_Product")
public class Product extends AbstractDictionary {

    @JoinColumn(name = "CATEGORY_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private DicProductCategory category;

    @OnDelete(DeletePolicy.CASCADE)
    @Composition
    @OneToMany(mappedBy = "product")
    private List<ProductBooking> bookings;

    @JoinTable(name = "EDT_PRODUCT_IMAGE_LINK",
            joinColumns = @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "IMAGE_ID", referencedColumnName = "ID"))
    @ManyToMany
    private List<Image> images;

    @Column(name = "PEOPLE_COUNT")
    private Integer peopleCount;

    @Column(name = "PRICE_TYPE")
    private String priceType;

    @Column(name = "PRICE", precision = 19, scale = 2)
    private BigDecimal price;

    @JoinTable(name = "EDT_PRODUCT_DIC_AMENITIES_LINK",
            joinColumns = @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "DIC_AMENITIES_ID", referencedColumnName = "ID"))
    @ManyToMany
    private List<DicAmenities> amenities;

    @Column(name = "FLOOR_")
    private Integer floor;

    @Column(name = "BOOKING_TYPE")
    private String bookingType;

    @Column(name = "FLOOR_STRING_VALUE")
    private String floorStringValue;

    @Column(name = "ROOM_SPACE")
    private String roomSpace;

    public String getRoomSpace() {
        return roomSpace;
    }

    public void setRoomSpace(String roomSpace) {
        this.roomSpace = roomSpace;
    }

    public String getFloorStringValue() {
        return floorStringValue;
    }

    public void setFloorStringValue(String floorStringValue) {
        this.floorStringValue = floorStringValue;
    }

    public List<ProductBooking> getBookings() {
        return bookings;
    }

    public void setBookings(List<ProductBooking> bookings) {
        this.bookings = bookings;
    }

    public BookingType getBookingType() {
        return bookingType == null ? null : BookingType.fromId(bookingType);
    }

    public void setBookingType(BookingType bookingType) {
        this.bookingType = bookingType == null ? null : bookingType.getId();
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public List<DicAmenities> getAmenities() {
        return amenities;
    }

    public void setAmenities(List<DicAmenities> amenities) {
        this.amenities = amenities;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public PriceType getPriceType() {
        return priceType == null ? null : PriceType.fromId(priceType);
    }

    public void setPriceType(PriceType priceType) {
        this.priceType = priceType == null ? null : priceType.getId();
    }

    public Integer getPeopleCount() {
        return peopleCount;
    }

    public void setPeopleCount(Integer peopleCount) {
        this.peopleCount = peopleCount;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public DicProductCategory getCategory() {
        return category;
    }

    public void setCategory(DicProductCategory category) {
        this.category = category;
    }

    @InstanceName
    @DependsOnProperties({"langValueRu"})
    public String getInstanceName(MetadataTools metadataTools) {
        return metadataTools.format(getLangValueRu());
    }
}