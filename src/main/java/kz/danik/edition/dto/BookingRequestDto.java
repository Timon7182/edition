package kz.danik.edition.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookingRequestDto extends AbstractDto{

    String name;
    String email;
    String phone;
    List<RequestDateDto> items;

}
