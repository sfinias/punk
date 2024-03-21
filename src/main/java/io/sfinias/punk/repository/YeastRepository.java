package io.sfinias.punk.repository;

import io.sfinias.punk.entity.Yeast;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface YeastRepository extends JpaRepository<Yeast, Long> {

    Optional<Yeast> findByName(String name);
}
