package kz.danik.edition.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientShortDto extends AbstractDto{

    private String name;
    private String phone;
    private String email;
}
