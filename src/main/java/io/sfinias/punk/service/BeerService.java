package io.sfinias.punk.service;

import io.sfinias.punk.dto.BeerDTO;
import io.sfinias.punk.dto.BeerFilter;
import io.sfinias.punk.entity.Beer;
import io.sfinias.punk.exceptions.NoEntitiesPresent;
import io.sfinias.punk.mapper.BeerMapper;
import io.sfinias.punk.repository.BeerRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class BeerService {

    private final YeastService yeastService;
    private final HopService hopService;
    private final MaltService maltService;
    private final BeerRepository beerRepository;

    private final EntityManager entityManager;

    private final Random random = new Random();

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


    public Page<BeerDTO> getBeers(Pageable pageable, BeerFilter beerFilter) {

        Specification<Beer> spec = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();

            if (beerFilter.name().isPresent()) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("name")), "%" + beerFilter.name().get().toLowerCase() + "%"));
            }
            if (beerFilter.year().isPresent()) {
                LocalDate start = beerFilter.year().get().atDay(1);
                LocalDate end = start.plusYears(1);
                predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("firstBrewed"), start), cb.lessThan(root.get("firstBrewed"), end));
            }
            if (beerFilter.food().isPresent()) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("foodPairings").get("name")), "%" + beerFilter.food().get().toLowerCase() + "%"));
            }
            if (beerFilter.yeast().isPresent()) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("yeast").get("name")), "%" + beerFilter.yeast().get().toLowerCase() + "%"));
            }
            if (beerFilter.malt().isPresent()) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("malts").get("malt").get("name")), "%" + beerFilter.malt().get().toLowerCase() + "%"));
            }
            if (beerFilter.hop().isPresent()) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("hops").get("hop").get("name")), "%" + beerFilter.hop().get().toLowerCase() + "%"));
            }
            return predicate;
        };
        Page<Beer> page = beerRepository.findAll(spec, pageable);
        BeerMapper mapper = new BeerMapper();
        return page.map(mapper::toDTO);
    }
    public BeerDTO getRandomBeer() {

        long total = beerRepository.count();
        if (total == 0) {
            throw new NoEntitiesPresent();
        }
        int index = random.nextInt((int) total);
        Page<BeerDTO> page = getBeers(PageRequest.of(index, 1));
        return page.getContent().getFirst();
    }
}
