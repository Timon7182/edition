package kz.danik.edition.repository;

import io.jmix.core.repository.JmixDataRepository;
import kz.danik.edition.entity.Request;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RequestRepository extends JmixDataRepository<Request, UUID> {
}