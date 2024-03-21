package io.sfinias.punk.repository;

import io.sfinias.punk.entity.Beer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BeerRepository extends JpaRepository<Beer, Long> {

}

