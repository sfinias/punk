package io.sfinias.punk.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record Method(

        @JsonProperty("mash_temp")
        List<MashTempItem> mashTemp,

        @JsonProperty("fermentation")
        Fermentation fermentation,

        @JsonProperty("twist")
        String twist
) {

}