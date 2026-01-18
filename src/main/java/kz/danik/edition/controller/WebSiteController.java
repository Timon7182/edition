package kz.danik.edition.controller;

import io.jmix.core.DataManager;
import kz.danik.edition.app.BookingService;
import kz.danik.edition.app.CommonMapperService;
import kz.danik.edition.app.ProductService;
import kz.danik.edition.dto.BookedHourDto;
import kz.danik.edition.dto.BookingRequestDto;
import kz.danik.edition.dto.DictionaryDto;
import kz.danik.edition.dto.ProductDto;
import kz.danik.edition.entity.DicProductCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

@RestController
@RequestMapping("/edition")
public class WebSiteController {


    @Autowired
    DataManager dataManager;
    @Autowired
    CommonMapperService commonMapperService;
    @Autowired
    ProductService productService;
    @Autowired
    BookingService bookingService;


    protected <T> ResponseEntity<T> handleRequest(Supplier<T> supplier) {
        try {
            return ResponseEntity.ok(supplier.get());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    protected ResponseEntity<Void> handleRequest(Runnable runnable) {
        try {
            runnable.run();
            return ResponseEntity.ok().<Void>build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("/categories")
    public ResponseEntity<List<DictionaryDto>> calculateTotalAmount() {
        List<DicProductCategory> categories = dataManager.load(DicProductCategory.class)
                .query("select e from edt_DicProductCategory e where e.active = true")
                .fetchPlan("_base")
                .list();

        List<DictionaryDto> dictionaries = new ArrayList<>();

        for (DicProductCategory category : categories) {
            DictionaryDto dictionaryDto = commonMapperService.mapDicToDto(category);
            dictionaryDto.setDescription(category.getDescriptionRu());
            dictionaryDto.setOrder(category.getOrder());
            dictionaries.add(dictionaryDto);

        }

        return handleRequest(() -> dictionaries);
    }

    @GetMapping("/rooms")
    public ResponseEntity<List<ProductDto>> getProductListByCategory(@RequestParam("category") String category) {
        return handleRequest(() -> productService.getProducts(category));
    }


    @PostMapping("/bookings")
    public ResponseEntity<Void> createBooking(@RequestBody BookingRequestDto bookingRequestDto) {
        return handleRequest(() -> bookingService.createBooking(bookingRequestDto));
    }

    @GetMapping("/booked-dates")
    public ResponseEntity<List<Date>> getProductBookedDates(@RequestParam("roomId") UUID roomId) {
        return handleRequest(() -> bookingService.getBookedDates(roomId));
    }

    @GetMapping("/booked-hours")
    public ResponseEntity<List<BookedHourDto>> getProductBookedHours(@RequestParam("roomId") UUID roomId,
                                                                     @RequestParam("date") String date) {
        return handleRequest(() -> bookingService.getBookedHours(roomId,date));
    }



}
