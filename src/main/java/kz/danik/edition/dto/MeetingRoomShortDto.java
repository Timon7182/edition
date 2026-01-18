package kz.danik.edition.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class MeetingRoomShortDto extends AbstractDto{

    private String code;
    private String name;
    private String location;
    private BigDecimal hourlyRate;
}
