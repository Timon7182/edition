package kz.danik.edition.app;

import io.jmix.core.DataManager;
import io.jmix.core.SaveContext;
import io.jmix.data.Sequence;
import io.jmix.data.Sequences;
import kz.danik.edition.dto.BookedHourDto;
import kz.danik.edition.dto.BookingRequestDto;
import kz.danik.edition.dto.RequestDateDto;
import kz.danik.edition.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

@Component("edt_BookingService")
public class BookingService {

    @Autowired
    DataManager dataManager;
    @Autowired
    protected Sequences sequences;

    public void createBooking(BookingRequestDto requestDto) {
        SaveContext saveContext  = new SaveContext();

        Request request = dataManager.create(Request.class);
        request.setStatus(RequestStatus.NEW);
        request.setEmail(requestDto.getEmail());
        request.setName(requestDto.getName());
        request.setPhone(requestDto.getPhone());

        request.setLangValueRu("Заявка: " + sequences.createNextValue(Sequence.withName("REQUEST")));

        List<ProductRequest> productRequests = new ArrayList<>();
        for (RequestDateDto item : requestDto.getItems()) {
            ProductRequest productRequest = dataManager.create(ProductRequest.class);
            productRequest.setProduct(getProductById(item.getId()));
            productRequest.setDateFrom(item.getStartDate());
            productRequest.setDateTo(item.getEndDate());
            productRequest.setRequest(request);
            productRequest.setHoursFrom(item.getStartHour());
            productRequest.setHoursTo(item.getEndHour());
            productRequest.setLangValueRu(productRequest.getProduct().getLangValueRu());
            productRequest.setPrice(item.getTotalPrice());
            productRequests.add(productRequest);
            saveContext.saving(productRequest);
        }
        request.setProductRequest(productRequests);
        saveContext.saving(request);
        dataManager.save(saveContext);
    }


    protected Product getProductById(UUID id) {
        return dataManager.load(Product.class)
                .id(id)
                .optional().orElse(null);

    }
    public List<Date> getBookedDates(UUID roomId) {
        List<ProductBooking> productBookings = dataManager.load(ProductBooking.class)
                .query("select e from edt_ProductBooking e where e.status = 'APPROVED' " +
                        "and e.product.id = :roomId " +
                        "and e.dateFrom is not null and e.dateTo is not null")
                .parameter("roomId", roomId)
                .list();

        List<Date> bookedDates = new ArrayList<>();

        for (ProductBooking booking : productBookings) {
            Date current = booking.getDateFrom();
            Date end = booking.getDateTo();

            if (current == null || end == null || current.after(end)) {
                continue;
            }

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(current);

            while (!calendar.getTime().after(end)) {
                bookedDates.add(calendar.getTime());
                calendar.add(Calendar.DATE, 1);
            }
        }

        return bookedDates;
    }

    public List<BookedHourDto> getBookedHours(UUID roomId, String date) {

        LocalDate localDate;
        try {
            localDate = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Expected yyyy-MM-dd", e);
        }

        LocalDateTime startOfDay = localDate.atStartOfDay();
        LocalDateTime endOfDay = localDate.atTime(LocalTime.MAX);

        Date start = Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
        Date end = Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());

        List<ProductBooking> productBookings = dataManager.load(ProductBooking.class)
                .query("select e from edt_ProductBooking e where e.status = 'APPROVED' " +
                        "and e.product.id = :roomId " +
                        "and e.dateFrom is not null and e.dateTo is null " +
                        "and e.dateFrom between :start and :end")
                .parameter("roomId", roomId)
                .parameter("start", start)
                .parameter("end", end)
                .list();

        List<BookedHourDto> bookedHourDtos = new ArrayList<>();

        for (ProductBooking booking : productBookings) {
            Date bookedDate = booking.getDateFrom();
            Integer bookedHourFrom = booking.getHoursFrom();
            Integer bookedHourTo = booking.getHoursTo();

            if (bookedDate == null || bookedHourFrom == null || bookedHourTo == null)
                continue;

            List<Integer> hours = new ArrayList<>();
            for (int h = bookedHourFrom; h < bookedHourTo; h++) {
                hours.add(h);
            }

            BookedHourDto dto = new BookedHourDto();
            dto.setDate(bookedDate);
            dto.setHours(hours);

            bookedHourDtos.add(dto);
        }

        Map<Date, List<Integer>> grouped = bookedHourDtos.stream()
                .collect(Collectors.toMap(
                        BookedHourDto::getDate,
                        BookedHourDto::getHours,
                        (h1, h2) -> {
                            h1.addAll(h2);
                            return h1.stream().distinct().sorted().collect(Collectors.toList());
                        }
                ));

        return grouped.entrySet().stream()
                .map(e -> new BookedHourDto(e.getKey(), e.getValue()))
                .sorted(Comparator.comparing(BookedHourDto::getDate))
                .collect(Collectors.toList());
    }

}