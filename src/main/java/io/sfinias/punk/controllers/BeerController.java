package io.sfinias.punk.controllers;

import io.sfinias.punk.dto.BeerDTO;
import io.sfinias.punk.service.BeerService;
import java.util.List;
import java.util.logging.Logger;
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
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something has gone wrong", e);
        }
    }
}
