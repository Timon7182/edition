package kz.danik.edition.app;

import io.jmix.core.DataManager;
import kz.danik.edition.dto.MeetingRoomShortDto;
import kz.danik.edition.entity.MeetingRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("edt_MeetingRoomService")
public class MeetingRoomService {


    @Autowired
    protected DataManager dataManager;

    public List<MeetingRoomShortDto> list(String search, int offset, int limit) {

        StringBuilder jpql = new StringBuilder("""
            select r from edt_MeetingRoom r
            where 1=1
        """);

        Map<String, Object> params = new HashMap<>();

        if (search != null && !search.isBlank()) {
            jpql.append("""
                and (
                    lower(r.name) like lower(concat('%', :q, '%'))
                    or lower(r.code) like lower(concat('%', :q, '%'))
                    or lower(r.location) like lower(concat('%', :q, '%'))
                )
            """);
            params.put("q", search);
        }

        jpql.append(" order by r.sortOrder asc nulls last, r.name asc");

        List<MeetingRoom> rooms = dataManager.load(MeetingRoom.class)
                .query(jpql.toString())
                .parameters(params)
                .firstResult(Math.max(offset, 0))
                .maxResults(Math.max(limit, 1))
                .list();

        return rooms.stream().map(r -> {
            MeetingRoomShortDto dto = new MeetingRoomShortDto();
            dto.setId(r.getId());
            dto.setName(r.getName());
            dto.setLocation(r.getLocation());
            dto.setHourlyRate(r.getHourlyRate());
            return dto;
        }).toList();
    }
}