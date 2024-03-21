package io.sfinias.punk.controllers;

import io.sfinias.punk.dto.BeerDTO;
import io.sfinias.punk.service.BeerService;
import java.util.logging.Logger;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BeerController {

    public static final Logger logger = Logger.getLogger(BeerController.class.getName());

    private final BeerService beerService;

    public BeerController(BeerService beerService) {

        this.beerService = beerService;
    }

    @PutMapping("/beer")
    public BeerDTO persistBeer(@RequestBody BeerDTO request) {

        logger.info(() -> "Received PUT request " + request);
        return beerService.save(request);
    }
}
