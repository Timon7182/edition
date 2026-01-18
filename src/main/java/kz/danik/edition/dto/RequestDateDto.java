package kz.danik.edition.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class RequestDateDto extends AbstractDto{

    ProductDto room;
    Date startDate;
    Date endDate;
    Integer startHour;
    Integer endHour;
    BigDecimal totalPrice;
}

