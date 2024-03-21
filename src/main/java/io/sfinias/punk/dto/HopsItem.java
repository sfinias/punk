package io.sfinias.punk.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record HopsItem(

        @JsonProperty("name")
        String name,

        @JsonProperty("amount")
        Amount amount,

        @JsonProperty("add")
        String add,

        @JsonProperty("attribute")
        String attribute
) {

}