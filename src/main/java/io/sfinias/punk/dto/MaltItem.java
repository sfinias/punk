package io.sfinias.punk.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record MaltItem(

        @JsonProperty("name")
        String name,

        @JsonProperty("amount")
        Amount amount

) {

}