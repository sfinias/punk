package io.sfinias.punk.service;

import io.sfinias.punk.entity.Yeast;
import io.sfinias.punk.repository.YeastRepository;
import org.springframework.stereotype.Service;

@Service
public class YeastService {

    private final YeastRepository yeastRepository;

    public YeastService(YeastRepository yeastRepository) {

        this.yeastRepository = yeastRepository;
    }

    public Yeast findOrCreate(String name) {

        return yeastRepository.findByName(name).orElseGet(() -> yeastRepository.save(new Yeast(name)));
    }
}
