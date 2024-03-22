package io.sfinias.punk.dto;

import java.time.Year;
import java.util.Optional;

public record BeerFilter(
        Optional<String> name,

        Optional<String> food,
        Optional<Year> year
) {

}