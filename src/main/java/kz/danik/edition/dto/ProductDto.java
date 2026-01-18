package kz.danik.edition.dto;

import kz.danik.edition.entity.ImageDto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ProductDto extends AbstractDto{

    String name;
    String type;
    Integer floor;
    String floorStringLabel;
    BigDecimal price;
    Integer capacity;
    List<String> amenities;
    String image;
    List<ImageDto> images;
    Boolean available;
    List<Date> bookedDates;
    List<BookedHourDto> bookedHours;
    String priceType;
    String bookingType;
    String roomSize;
}
