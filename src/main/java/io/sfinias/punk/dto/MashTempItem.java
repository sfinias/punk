package io.sfinias.punk.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record MashTempItem(

        @JsonProperty("temp")
        Temp temp,
        @JsonProperty("duration")
        Integer duration
) {

}