package io.sfinias.punk.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Fermentation(

        @JsonProperty("temp")
        Temp temp
) {

}