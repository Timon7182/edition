package kz.danik.edition.controller;

import kz.danik.edition.app.ClientService;
import kz.danik.edition.app.MeetingRoomService;
import kz.danik.edition.app.MobileException;
import kz.danik.edition.app.RoomBookingService;
import kz.danik.edition.dto.ClientShortDto;
import kz.danik.edition.dto.MeetingRoomShortDto;
import kz.danik.edition.dto.RoomBookingDto;
import kz.danik.edition.dto.RoomBookingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

@RestController
@RequestMapping("/mrest")
public class RoomManagmentController {

    @Autowired
    protected RoomBookingService roomBookingService;
    @Autowired
    protected ClientService clientService;
    @Autowired
    protected MeetingRoomService meetingRoomService;


    protected <T> ResponseEntity<T> handleRequest(Supplier<T> supplier) {
        try {
            return ResponseEntity.ok(supplier.get());
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());

        }
    }

    @PostMapping("/bookingroom/create")
        public ResponseEntity<RoomBookingDto> create(@RequestBody RoomBookingDto dto) {
        return handleRequest(() -> roomBookingService.create(dto));

    }

    @PutMapping("/bookingroom/update")
    public ResponseEntity<RoomBookingDto> update(@RequestBody RoomBookingDto dto) {
        return handleRequest(() -> roomBookingService.update(dto));
    }

    @GetMapping("/bookingroom/list")
    public List<RoomBookingDto> list(
            @RequestParam(required = false) UUID roomId,
            @RequestParam(required = false) UUID clientId,
            @RequestParam(required = false) OffsetDateTime from,
            @RequestParam(required = false) OffsetDateTime to,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "50") int limit
    ) {
        RoomBookingFilter filter = new RoomBookingFilter();
        filter.setRoomId(roomId);
        filter.setClientId(clientId);
        filter.setFrom(from);
        filter.setTo(to);
        filter.setStatus(status);

        return roomBookingService.list(filter, offset, limit);
    }

    @PostMapping("/client/create")
    public ClientShortDto create(@RequestBody ClientShortDto dto) {
        return clientService.create(dto);
    }

    @PutMapping("/client/update")
    public ClientShortDto update(@RequestBody ClientShortDto dto) {
        return clientService.update(dto);
    }

    @GetMapping("/client/list")
    public List<ClientShortDto> clientList(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "50") int limit
    ) {
        return clientService.list(search, offset, limit);
    }

    @GetMapping("/room/list")
    public List<MeetingRoomShortDto> meetingList(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "50") int limit
    ) {
        return meetingRoomService.list(search, offset, limit);
    }


}
