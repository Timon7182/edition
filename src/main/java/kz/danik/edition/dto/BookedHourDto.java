package kz.danik.edition.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BookedHourDto extends AbstractDto{

    List<Integer> hours;
    Date date;

    public BookedHourDto(Date date,List<Integer> hours) {
        this.hours = hours;
        this.date = date;
    }
}
