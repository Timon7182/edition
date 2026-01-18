package kz.danik.edition.repository;

import io.jmix.core.repository.JmixDataRepository;
import kz.danik.edition.entity.MeetingRoom;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MeetingRoomRepository extends JmixDataRepository<MeetingRoom, UUID> {
}