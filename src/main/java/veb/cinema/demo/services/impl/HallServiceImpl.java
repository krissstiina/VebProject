package veb.cinema.demo.services.impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import veb.cinema.demo.models.Hall;
import veb.cinema.demo.dto.HallDto;
import veb.cinema.demo.repositories.HallRepository;
import veb.cinema.demo.services.HallService;

@Service
@EnableCaching
public class HallServiceImpl implements HallService {
    private static final String CACHE_KEY = "hall";

    @Autowired
    private final HallRepository hallRepository;

    @Autowired
    private final ModelMapper modelMapper;

    public HallServiceImpl(HallRepository hallRepository, ModelMapper modelMapper) {
        this.hallRepository = hallRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Cacheable(CACHE_KEY)
    public List<HallDto> findAll() {
        return hallRepository.findAll().stream()
                .map(hall -> modelMapper.map(hall, HallDto.class))
                .toList();
    }

    @Override
    public HallDto findById(int id) {
        Optional<Hall> hall = hallRepository.findById(id);
        return hall.map(value -> modelMapper.map(value, HallDto.class)).orElse(null);
    }

    @Override
    public int getHallNumberById(int hallId) {
        Optional<Hall> hallOptional = hallRepository.findById(hallId);
        return hallOptional.map(Hall::getHallNumber).orElse(0);
    }

    @Override
    @CacheEvict(value = CACHE_KEY, allEntries = true)
    public int create(int capacity, int hallNumber) {
        Hall hall = new Hall(capacity, hallNumber);
        hallRepository.create(hall);
        return hall.getId();
    }
}
