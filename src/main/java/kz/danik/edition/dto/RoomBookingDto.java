package kz.danik.edition.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
public class RoomBookingDto extends AbstractDto{


    private MeetingRoomShortDto room;
    private ClientShortDto client;

    private OffsetDateTime startAt;
    private OffsetDateTime endAt;

    private String status;
    private String comment;

    private String paymentStatus;
    private String paymentMethod;
    private BigDecimal amount;
    private OffsetDateTime paidAt;

}
