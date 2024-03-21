package io.sfinias.punk.repository;

import io.sfinias.punk.entity.Malt;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaltRepository extends JpaRepository<Malt, Long> {

    Optional<Malt> findByName(String name);
}
