package io.sfinias.punk.service;

import io.sfinias.punk.entity.Hop;
import io.sfinias.punk.repository.HopRepository;
import org.springframework.stereotype.Service;

@Service
public class HopService {

    private final HopRepository hopRepository;

    public HopService(HopRepository hopRepository) {

        this.hopRepository = hopRepository;
    }

    public Hop findOrCreate(String name) {

        return hopRepository.findByName(name).orElseGet(() -> hopRepository.save(new Hop(name)));
    }
}
