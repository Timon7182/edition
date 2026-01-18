package kz.danik.edition.repository;

import io.jmix.core.repository.JmixDataRepository;
import kz.danik.edition.entity.RoomBooking;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoomBookingRepository extends JmixDataRepository<RoomBooking, UUID> {
}