package kz.danik.edition.mapstruct;

import kz.danik.edition.dto.ClientShortDto;
import kz.danik.edition.entity.Client;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ClientMapper {


    ClientShortDto toDto(Client entity);


    /* =========================
       DTO → Entity (CREATE)
       ========================= */

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    Client toEntity(ClientShortDto dto);


    /* =========================
       DTO → Entity (UPDATE)
       ========================= */

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "version", ignore = true)
    void update(ClientShortDto dto, @MappingTarget Client entity);
}
