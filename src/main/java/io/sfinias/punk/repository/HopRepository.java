package io.sfinias.punk.repository;

import io.sfinias.punk.entity.Hop;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HopRepository extends JpaRepository<Hop, Long> {

    Optional<Hop> findByName(String name);
}
