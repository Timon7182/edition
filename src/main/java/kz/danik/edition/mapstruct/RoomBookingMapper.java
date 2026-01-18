package kz.danik.edition.mapstruct;

import kz.danik.edition.dto.ClientShortDto;
import kz.danik.edition.dto.MeetingRoomShortDto;
import kz.danik.edition.dto.RoomBookingDto;
import kz.danik.edition.entity.Client;
import kz.danik.edition.entity.MeetingRoom;
import kz.danik.edition.entity.RoomBooking;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface RoomBookingMapper {

    /* =========================
       Entity → DTO
       ========================= */

    @Mapping(target = "room", source = "room")
    @Mapping(target = "client", source = "client")
    RoomBookingDto toDto(RoomBooking entity);


    /* =========================
       DTO → Entity (CREATE)
       ========================= */

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "room", ignore = true)
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    @Mapping(target = "deletedDate", ignore = true)
    RoomBooking toEntity(RoomBookingDto dto);


    /* =========================
       DTO → Entity (UPDATE)
       ========================= */

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "room", ignore = true)
    @Mapping(target = "client", ignore = true)
    void update(RoomBookingDto dto, @MappingTarget RoomBooking entity);


    /* =========================
       Nested mappers
       ========================= */

    MeetingRoomShortDto toDto(MeetingRoom room);

    ClientShortDto toDto(Client client);

}
