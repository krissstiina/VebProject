package veb.cinema.demo.services;

import java.util.List;

import veb.cinema.demo.dto.HallDto;
import veb.cinema.demo.models.Hall;

public interface HallService {
    HallDto findById(int id);
    List<HallDto> findAll();
    int create(int capacity, int hallNumber);
    int getHallNumberById(int hallId);
}
