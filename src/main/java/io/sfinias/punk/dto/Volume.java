package io.sfinias.punk.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public record Volume(

        @JsonProperty("unit")
        String unit,

        @JsonProperty("value")
        BigDecimal value
) {

}