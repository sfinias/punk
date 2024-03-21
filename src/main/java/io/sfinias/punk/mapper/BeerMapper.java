package io.sfinias.punk.mapper;

import io.sfinias.punk.dto.Amount;
import io.sfinias.punk.dto.BeerDTO;
import io.sfinias.punk.dto.BoilVolume;
import io.sfinias.punk.dto.Fermentation;
import io.sfinias.punk.dto.HopsItem;
import io.sfinias.punk.dto.Ingredients;
import io.sfinias.punk.dto.MaltItem;
import io.sfinias.punk.dto.MashTempItem;
import io.sfinias.punk.dto.Method;
import io.sfinias.punk.dto.Temp;
import io.sfinias.punk.dto.Volume;
import io.sfinias.punk.entity.Beer;
import io.sfinias.punk.entity.BeerHop;
import io.sfinias.punk.entity.BeerMalt;
import io.sfinias.punk.entity.FoodPairing;
import io.sfinias.punk.entity.Hop;
import io.sfinias.punk.entity.Malt;
import io.sfinias.punk.entity.Yeast;
import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.LongFunction;

public class BeerMapper {

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/yyyy");

    private final Function<String, LocalDate> formatMonthYearToDate = dateString ->
            dateString.length() == 4
                    ? Year.parse(dateString).atDay(1)
                    : YearMonth.parse(dateString, dateFormatter).atDay(1);

    private final Function<LocalDate, String> formatDateToMonthYear = dateFormatter::format;

    public BeerDTO toDTO(Beer beer) {

        return new BeerDTO(
                beer.getId(),
                beer.getName(),
                beer.getTagline(),
                formatDateToMonthYear.apply(beer.getFirstBrewed()),
                beer.getDescription(),
                beer.getImageUrl(),
                beer.getAbv(),
                beer.getIbu(),
                beer.getTargetFg(),
                beer.getTargetOg(),
                beer.getEbc(),
                beer.getSrm(),
                beer.getPh(),
                beer.getAttenuationLevel(),
                new Volume(beer.getBoilVolumeUnit(), beer.getVolumeValue()),
                new BoilVolume(beer.getBoilVolumeUnit(), beer.getVolumeValue()),
                new Method(
                        List.of(new MashTempItem(new Temp(beer.getMashTempUnit(), beer.getMashTempValue()), beer.getMashTempDuration())),
                        new Fermentation(new Temp(beer.getFermentationUnit(), beer.getFermentationValue())),
                        beer.getTwist()
                ),
                new Ingredients(
                        beer.getMalts().stream().map(malt ->
                                new MaltItem(malt.getMalt().getName(), new Amount(malt.getAmountUnit(), malt.getAmountValue()))
                        ).toList(),
                        beer.getHops().stream().map(hop ->
                                new HopsItem(hop.getHop().getName(), new Amount(hop.getAmountUnit(), hop.getAmountValue()), hop.getAdd(), hop.getAttribute())
                        ).toList(),
                        beer.getYeast() != null ? beer.getYeast().getName() : null

                ),
                beer.getFoodPairings().stream().map(FoodPairing::getName).toList(),
                beer.getBrewersTips(),
                beer.getContributedBy()
        );
    }

    public Beer toEntity(BeerDTO beerDTO, LongFunction<Beer> beerFunction, Function<String, Hop> hopFunction, Function<String, Malt> maltFunction, Function<String, Yeast> yeastFunction) {

        var yeast = Optional.ofNullable(beerDTO.ingredients().yeast())
                .map(yeastFunction)
                .orElse(null);

        var beer = beerFunction.apply(beerDTO.id());
        beer.setId(beerDTO.id());
        beer.setName(beerDTO.name());
        beer.setTagline(beerDTO.tagline());
        beer.setFirstBrewed(formatMonthYearToDate.apply(beerDTO.firstBrewed()));
        beer.setDescription(beerDTO.description());
        beer.setImageUrl(beerDTO.imageUrl());
        beer.setAbv(beerDTO.abv());
        beer.setIbu(beerDTO.ibu());
        beer.setTargetFg(beerDTO.targetFg());
        beer.setTargetOg(beerDTO.targetOg());
        beer.setEbc(beerDTO.ebc());
        beer.setSrm(beerDTO.srm());
        beer.setPh(beerDTO.ph());
        beer.setAttenuationLevel(beerDTO.attenuationLevel());
        beer.setVolumeValue(beerDTO.volume().value());
        beer.setVolumeUnit(beerDTO.volume().unit());
        beer.setBoilVolumeValue(beerDTO.boilVolume().value());
        beer.setBoilVolumeUnit(beerDTO.boilVolume().unit());
        beer.setMashTempValue(beerDTO.method().mashTemp().getFirst().temp().value());
        beer.setMashTempUnit(beerDTO.method().mashTemp().getFirst().temp().unit());
        beer.setMashTempDuration(beerDTO.method().mashTemp().getFirst().duration());
        beer.setFermentationValue(beerDTO.method().fermentation().temp().value());
        beer.setFermentationUnit(beerDTO.method().fermentation().temp().unit());
        beer.setTwist(beerDTO.method().twist());
        beer.setBrewersTips(beerDTO.brewersTips());
        beer.setContributedBy(beerDTO.contributedBy());
        beer.setYeast(yeast);

        beerDTO.ingredients().hops()
                .forEach(hopDTO -> {
                    var hop = hopFunction.apply(hopDTO.name());
                    var beerHop = new BeerHop(beer, hop);
                    beerHop.setAdd(hopDTO.add());
                    beerHop.setAmountValue(hopDTO.amount().value());
                    beerHop.setAmountUnit(hopDTO.amount().unit());
                    beerHop.setAttribute(hopDTO.attribute());
                    beer.getHops().add(beerHop);
                });

        beerDTO.ingredients().malt()
                .forEach(maltDTO -> {
                    var malt = maltFunction.apply(maltDTO.name());
                    var beerMalt = new BeerMalt(beer, malt);
                    beerMalt.setAmountValue(maltDTO.amount().value());
                    beerMalt.setAmountUnit(maltDTO.amount().unit());
                    beer.getMalts().add(beerMalt);
                });

        beerDTO.foodPairing().stream()
                .map(FoodPairing::new)
                .forEach(beer::addFoodPairing);
        return beer;
    }
}
