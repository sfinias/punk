package io.sfinias.punk.validator;

import io.sfinias.punk.dto.BeerFilter;
import java.util.Arrays;
import java.util.function.Predicate;

public enum BeerFilterValidation {
    NAME_NOT_EMPTY(beerFilter -> beerFilter.name().isPresent() && beerFilter.name().get().trim().isEmpty(), "Name cannot be empty"),
    FOOD_NOT_EMPTY(beerFilter -> beerFilter.food().isPresent() && beerFilter.food().get().trim().isEmpty(), "Food cannot be empty"),
    HOP_NOT_EMPTY(beerFilter -> beerFilter.hop().isPresent() && beerFilter.hop().get().trim().isEmpty(), "Hop cannot be empty"),
    MALT_NOT_EMPTY(beerFilter -> beerFilter.malt().isPresent() && beerFilter.malt().get().trim().isEmpty(), "Malt cannot be empty"),
    YEAST_NOT_EMPTY(beerFilter -> beerFilter.yeast().isPresent() && beerFilter.yeast().get().trim().isEmpty(), "Yeast cannot be empty");

    private final Predicate<BeerFilter> filter;
    private final String errorMessage;

    BeerFilterValidation(Predicate<BeerFilter> filter, String errorMessage) {

        this.filter = filter;
        this.errorMessage = errorMessage;
    }

    public static void validateInput(BeerFilter beerFilter) {

        Arrays.stream(BeerFilterValidation.values())
                .filter(val -> val.filter.test(beerFilter))
                .findFirst()
                .ifPresent(error -> {
                    throw new IllegalArgumentException(error.errorMessage);
                });
    }
}
