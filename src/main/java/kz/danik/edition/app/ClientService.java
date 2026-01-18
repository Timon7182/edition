package kz.danik.edition.app;

import io.jmix.core.DataManager;
import kz.danik.edition.dto.ClientShortDto;
import kz.danik.edition.entity.Client;
import kz.danik.edition.mapstruct.ClientMapper;
import kz.danik.edition.mapstruct.RoomBookingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component("edt_ClientService")
public class ClientService {

    @Autowired
    protected DataManager dataManager;
    @Autowired
    protected ClientMapper clientMapper;


    public ClientShortDto create(ClientShortDto dto) {
        if (dto.getId() != null) {
            throw new IllegalArgumentException("For create id must be null");
        }
        validate(dto);

        Client entity = clientMapper.toEntity(dto);
        entity.setId(UUID.randomUUID());
        Client saved = dataManager.save(entity);
        return clientMapper.toDto(saved);
    }

    public ClientShortDto update(ClientShortDto dto) {
        if (dto.getId() == null) {
            throw new IllegalArgumentException("For update id must not be null");
        }
        Client entity = dataManager.load(Client.class).id(dto.getId()).one();
        clientMapper.update(dto, entity);
        Client saved = dataManager.save(entity);
        return clientMapper.toDto(saved);
    }

    public List<ClientShortDto> list(String search, int offset, int limit) {

        StringBuilder jpql = new StringBuilder("""
            select c from edt_Client c
            where c.deletedDate is null
        """);

        Map<String, Object> params = new HashMap<>();

        if (search != null && !search.isBlank()) {
            jpql.append("""
                and (
                    lower(c.name) like lower(concat('%', :q, '%'))
                    or lower(c.phone) like lower(concat('%', :q, '%'))
                    or lower(c.email) like lower(concat('%', :q, '%'))
                )
            """);
            params.put("q", search);
        }

        jpql.append(" order by c.name asc");

        List<Client> clients = dataManager.load(Client.class)
                .query(jpql.toString())
                .parameters(params)
                .firstResult(Math.max(offset, 0))
                .maxResults(Math.max(limit, 1))
                .list();

        return clients.stream().map(clientMapper::toDto).toList();
    }

    /** If by phone+name not found -> create */
    public Client getOrCreateByPhoneAndName(String phone, String name, String email) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name is required");
        }

        String jpql = """
            select c from edt_Client c
            where c.deletedDate is null
              and c.phone = :phone
              and c.name = :name
        """;

        List<Client> found = dataManager.load(Client.class)
                .query(jpql)
                .parameter("phone", phone)
                .parameter("name", name)
                .list();

        if (!found.isEmpty()) {
            return found.get(0);
        }

        Client c = dataManager.create(Client.class);
        c.setName(name);
        c.setPhone(phone);
        c.setEmail(email);
        c.setId(UUID.randomUUID());
        return dataManager.save(c);
    }

    private void validate(ClientShortDto dto) {
        if (dto.getName() == null || dto.getName().isBlank()) {
            throw new IllegalArgumentException("name is required");
        }
    }
}