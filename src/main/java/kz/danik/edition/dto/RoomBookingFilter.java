package kz.danik.edition.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
public class RoomBookingFilter {

    private UUID roomId;
    private UUID clientId;

    /** inclusive */
    private OffsetDateTime from;

    /** exclusive (or inclusive - choose one style; here exclusive is safer) */
    private OffsetDateTime to;

    private String status; // optional
}

