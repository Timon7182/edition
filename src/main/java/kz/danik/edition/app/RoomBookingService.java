package kz.danik.edition.app;

import io.jmix.core.DataManager;
import kz.danik.edition.dto.RoomBookingDto;
import kz.danik.edition.dto.RoomBookingFilter;
import kz.danik.edition.entity.Client;
import kz.danik.edition.entity.MeetingRoom;
import kz.danik.edition.entity.RoomBooking;
import kz.danik.edition.mapstruct.RoomBookingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component("edt_RoomBookingService")
public class RoomBookingService {

    @Autowired
    protected DataManager dataManager;
    @Autowired
    protected RoomBookingMapper mapper;

    public RoomBookingDto create(RoomBookingDto dto)  {
        if (dto.getId() != null) {
            throw new IllegalArgumentException("For create id must be null");
        }
        validateRequired(dto);
        validateDates(dto.getStartAt(), dto.getEndAt());

        UUID roomId = dto.getRoom().getId();
        UUID clientId = dto.getClient().getId();

        // overlap check
        if (existsOverlap(roomId, dto.getStartAt(), dto.getEndAt(), null)) {
            throw new IllegalArgumentException("На данное время переговорная уже занята");
        }

        RoomBooking entity = mapper.toEntity(dto);
        entity.setRoom(dataManager.getReference(MeetingRoom.class, roomId));
        entity.setClient(dataManager.getReference(Client.class, clientId));
        entity.setId(UUID.randomUUID());
        RoomBooking saved = dataManager.save(entity);
        return mapper.toDto(saved);
    }

    public RoomBookingDto update(RoomBookingDto dto) {
        if (dto.getId() == null) {
            throw new IllegalArgumentException("For update id must not be null");
        }

        RoomBooking entity = dataManager.load(RoomBooking.class)
                .id(dto.getId())
                .one();

        // if start/end provided -> validate
        OffsetDateTime newStart = dto.getStartAt() != null ? dto.getStartAt() : entity.getStartAt();
        OffsetDateTime newEnd = dto.getEndAt() != null ? dto.getEndAt() : entity.getEndAt();
        validateDates(newStart, newEnd);

        // if room changed (or provided) use dto.room.id else entity.room.id
        UUID roomId = (dto.getRoom() != null && dto.getRoom().getId() != null)
                ? dto.getRoom().getId()
                : (entity.getRoom() != null ? entity.getRoom().getId() : null);

        if (roomId == null) {
            throw new IllegalArgumentException("room.id is required");
        }

        // overlap check excluding current booking id
        if (dto.getStartAt() != null || dto.getEndAt() != null || (dto.getRoom() != null && dto.getRoom().getId() != null)) {
            if (existsOverlap(roomId, newStart, newEnd, entity.getId())) {
                throw new IllegalArgumentException("На данное время переговорная уже занята");
            }
        }

        mapper.update(dto, entity);

        // set relations only if provided
        if (dto.getRoom() != null && dto.getRoom().getId() != null) {
            entity.setRoom(dataManager.getReference(MeetingRoom.class, dto.getRoom().getId()));
        }
        if (dto.getClient() != null && dto.getClient().getId() != null) {
            entity.setClient(dataManager.getReference(Client.class, dto.getClient().getId()));
        }

        RoomBooking saved = dataManager.save(entity);
        return mapper.toDto(saved);
    }

    public List<RoomBookingDto> list(RoomBookingFilter filter, int offset, int limit) {

        StringBuilder jpql = new StringBuilder("""
            select b from edt_RoomBooking b
            where b.deletedDate is null
        """);

        Map<String, Object> params = new HashMap<>();

        if (filter != null) {
            if (filter.getRoomId() != null) {
                jpql.append(" and b.room.id = :roomId");
                params.put("roomId", filter.getRoomId());
            }
            if (filter.getClientId() != null) {
                jpql.append(" and b.client.id = :clientId");
                params.put("clientId", filter.getClientId());
            }
            if (filter.getStatus() != null && !filter.getStatus().isBlank()) {
                jpql.append(" and b.status = :status");
                params.put("status", filter.getStatus());
            }
            if (filter.getFrom() != null) {
                // booking intersects [from, to) logic: endAt > from
                jpql.append(" and b.endAt > :from");
                params.put("from", filter.getFrom());
            }
            if (filter.getTo() != null) {
                // booking intersects [from, to) logic: startAt < to
                jpql.append(" and b.startAt < :to");
                params.put("to", filter.getTo());
            }
        }

        jpql.append(" order by b.startAt asc");

        List<RoomBooking> entities = dataManager.load(RoomBooking.class)
                .query(jpql.toString())
                .parameters(params)
                .firstResult(Math.max(offset, 0))
                .maxResults(Math.max(limit, 1))
                .list();

        return entities.stream().map(mapper::toDto).toList();
    }

    private boolean existsOverlap(UUID roomId, OffsetDateTime startAt, OffsetDateTime endAt, UUID excludeId) {

        String jpql = """
            select count(b) from edt_RoomBooking b
            where b.deletedDate is null
              and b.room.id = :roomId
              and b.status <> 'CANCELED'
              and b.startAt < :endAt
              and b.endAt > :startAt
        """;

        Map<String, Object> params = new HashMap<>();
        params.put("roomId", roomId);
        params.put("startAt", startAt);
        params.put("endAt", endAt);

        if (excludeId != null) {
            jpql += " and b.id <> :excludeId";
            params.put("excludeId", excludeId);
        }

        Long cnt = dataManager.loadValue(jpql, Long.class)
                .setParameters(params)
                .one();

        return cnt != null && cnt > 0;
    }

    private void validateRequired(RoomBookingDto dto) {
        if (dto.getRoom() == null || dto.getRoom().getId() == null) {
            throw new IllegalArgumentException("room.id is required");
        }
        if (dto.getClient() == null || dto.getClient().getId() == null) {
            throw new IllegalArgumentException("client.id is required");
        }
        if (dto.getStartAt() == null || dto.getEndAt() == null) {
            throw new IllegalArgumentException("startAt and endAt are required");
        }
    }

    private void validateDates(OffsetDateTime startAt, OffsetDateTime endAt) {
        if (startAt == null || endAt == null) {
            throw new IllegalArgumentException("startAt and endAt are required");
        }
        if (!endAt.isAfter(startAt)) {
            throw new IllegalArgumentException("endAt must be after startAt");
        }
    }

}
