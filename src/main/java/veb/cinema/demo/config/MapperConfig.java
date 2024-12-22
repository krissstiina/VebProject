package veb.cinema.demo.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MapperConfig {
    //private final MapperConfig mapper;

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}

    // public MapperConfig(){
    //     mapper = new MapperConfig();

    //     mapper
    //         .typeMap(Genre.class, GenreDto.class)
    //         .addMappings(map -> {
    //             map(genre -> genre.getId(), GenreDto::setId);
    //             map.map(genre -> genre.getFilm().getId(), GenreDto::setFilm);
    //             map.map(genre -> genre.getName(), GenreDto::setName);
    //         }
    //     );

    //     mapper
    //         .typeMap(Place.class, PlaceDto.class)
    //         .addMappings(map -> {
    //             map.map(place -> place.getId(), PlaceDto::setId);
    //             map.map(place -> place.getHallNumber().getId(), PlaceDto::setHallNumber);
    //             map.map(place -> place.getSeatNumber().getId(), PlaceDto::setSeatNumber);
    //             map.map(place -> place.getStatus(), PlaceDto::setStatus);
    //         }
    //     );

    //     mapper
    //         .typeMap(Reviews.class, ReviewsDto.class)
    //         .addMappings(map -> {
    //             map.map(reviewId -> reviewId.getId(), ReviewsDto::setId);
    //             map.map(reviewId -> reviewId.getFilm().getId(), ReviewsDto::setFilmName);
    //             map.map(reviewId -> reviewId.getUser().getId(), ReviewsDto::setUserId);
    //             map.map(reviewId -> reviewId.reviewId(), ReviewsDto::setReview);
    //         }
    //     );

    //     mapper
    //         .typeMap(Ticket.class, TicketDto.class)
    //         .addMappings(map -> {
    //             map.map(ticket -> ticket.getId(), TicketDto::setId);
    //             map.map(ticket -> ticket.getPrice(), TicketDto::setPrice);
    //             map.map(ticket -> ticket.getSession().getId(), TicketDto::setSessionId);
    //             map.map(ticket -> ticket.getUser().getId(), TicketDto::setUserId);
    //             map.map(ticket -> ticket.getPlace().getId(), TicketDto::setPlaceId);
    //         }
    //     );
    // }

