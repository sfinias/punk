package io.sfinias.punk.controllers;

import io.sfinias.punk.dto.BeerDTO;
import io.sfinias.punk.exceptions.NoEntitiesPresent;
import io.sfinias.punk.service.BeerService;
import java.util.List;
import java.util.logging.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api")
public class BeerController {

    private static final String VERSION1 = "v1";
    private static final String VERSION2 = "v2";
    private static final String BEER = "beer";
    private static final String BEERS = "beers";

    public static final Logger logger = Logger.getLogger(BeerController.class.getName());

    private final BeerService beerService;

    public BeerController(BeerService beerService) {

        this.beerService = beerService;
    }

    @PutMapping("/" + VERSION1 + "/" + BEER)
    public BeerDTO persistBeer(@RequestBody BeerDTO request) {

        logger.info(() -> "Received PUT request " + request);
        return beerService.save(request);
    }

    @GetMapping("/" + VERSION1 + "/" + BEERS)
    public List<BeerDTO> getAllBeers() {
        logger.info(() -> "Received GET request for all beers");
        try {
            return beerService.getAllBeers();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @PutMapping("/" + VERSION2 + "/" + BEER)
    public BeerDTO persistBeerV2(@RequestBody BeerDTO request) {

        return persistBeer(request);
    }

    @GetMapping("/" + VERSION2 + "/" + BEERS)
    public Page<BeerDTO> getBeers(Pageable pageable) {
        logger.info(() -> "Received GET request for beers " + pageable);
        try {
            return beerService.getBeers(pageable);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @GetMapping("/" + VERSION2 + "/" + BEER + "/random")
    public BeerDTO getRandomBeer() {
        logger.info(() -> "Received GET request for random beer");
        try {
            return beerService.getRandomBeer();
        } catch (NoEntitiesPresent e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There are no beers stored");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

}
