package kz.danik.edition.repository;

import io.jmix.core.repository.JmixDataRepository;
import kz.danik.edition.entity.Client;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ClientRepository extends JmixDataRepository<Client, UUID> {
}