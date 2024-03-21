package io.sfinias.punk.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;

public record BeerDTO(

        @JsonProperty("id")
        long id,

        @JsonProperty("name")
        String name,

        @JsonProperty("tagline")
        String tagline,

        @JsonProperty("first_brewed")
        String firstBrewed,

        @JsonProperty("description")
        String description,

        @JsonProperty("image_url")
        String imageUrl,

        @JsonProperty("abv")
        BigDecimal abv,

        @JsonProperty("ibu")
        BigDecimal ibu,

        @JsonProperty("target_fg")
        BigDecimal targetFg,

        @JsonProperty("target_og")
        BigDecimal targetOg,

        @JsonProperty("ebc")
        BigDecimal ebc,

        @JsonProperty("srm")
        BigDecimal srm,

        @JsonProperty("ph")
        BigDecimal ph,

        @JsonProperty("attenuation_level")
        BigDecimal attenuationLevel,

        @JsonProperty("volume")
        Volume volume,

        @JsonProperty("boil_volume")
        BoilVolume boilVolume,

        @JsonProperty("method")
        Method method,

        @JsonProperty("ingredients")
        Ingredients ingredients,

        @JsonProperty("food_pairing")
        List<String> foodPairing,

        @JsonProperty("brewers_tips")
        String brewersTips,

        @JsonProperty("contributed_by")
        String contributedBy

) {

}