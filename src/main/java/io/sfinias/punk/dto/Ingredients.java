package io.sfinias.punk.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record Ingredients(

        @JsonProperty("malt")
        List<MaltItem> malt,

        @JsonProperty("hops")
        List<HopsItem> hops,

        @JsonProperty("yeast")
        String yeast
) {

}