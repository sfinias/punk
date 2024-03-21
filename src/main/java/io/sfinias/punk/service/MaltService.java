package io.sfinias.punk.service;

import io.sfinias.punk.entity.Malt;
import io.sfinias.punk.repository.MaltRepository;
import org.springframework.stereotype.Service;

@Service
public class MaltService {

    private final MaltRepository maltRepository;

    public MaltService(MaltRepository maltRepository) {

        this.maltRepository = maltRepository;
    }

    public Malt findOrCreate(String name) {

        return maltRepository.findByName(name).orElseGet(() -> maltRepository.save(new Malt(name)));
    }
}
