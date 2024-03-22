package io.sfinias.punk.service;

import io.sfinias.punk.dto.BeerDTO;
import io.sfinias.punk.entity.Beer;
import io.sfinias.punk.mapper.BeerMapper;
import io.sfinias.punk.repository.BeerRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BeerService {

    private final YeastService yeastService;
    private final HopService hopService;
    private final MaltService maltService;
    private final BeerRepository beerRepository;

    private final EntityManager entityManager;

    public BeerService(YeastService yeastService, HopService hopService, MaltService maltService, BeerRepository beerRepository, EntityManager entityManager) {

        this.yeastService = yeastService;
        this.hopService = hopService;
        this.maltService = maltService;
        this.beerRepository = beerRepository;
        this.entityManager = entityManager;
    }

    @Transactional
    public BeerDTO save(BeerDTO beerDTO) {

        BeerMapper mapper = new BeerMapper();
        var beer = mapper.toEntity(beerDTO,
                id -> beerRepository.findById(id).orElseGet(Beer::new),
                hopService::findOrCreate,
                maltService::findOrCreate,
                yeastService::findOrCreate
        );
        Beer persistedBeer = entityManager.merge(beer);
        return mapper.toDTO(persistedBeer);
    }

    public List<BeerDTO> getAllBeers() {

        List<Beer> beers = beerRepository.findAll();
        BeerMapper mapper = new BeerMapper();
        return beers.stream().map(mapper::toDTO).toList();
    }

    public Page<BeerDTO> getBeers(Pageable pageable) {

        Page<Beer> page = beerRepository.findAll(pageable);
        BeerMapper mapper = new BeerMapper();
        return page.map(mapper::toDTO);
    }
}
