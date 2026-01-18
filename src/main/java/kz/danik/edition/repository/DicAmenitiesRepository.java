package kz.danik.edition.repository;

import io.jmix.core.repository.JmixDataRepository;
import kz.danik.edition.entity.DicAmenities;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DicAmenitiesRepository extends JmixDataRepository<DicAmenities, UUID> {
}