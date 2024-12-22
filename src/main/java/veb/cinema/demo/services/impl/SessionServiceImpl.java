package veb.cinema.demo.services.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import veb.cinema.demo.models.*;
import veb.cinema.demo.dto.SessionDto;
import veb.cinema.demo.repositories.HallRepository;
import veb.cinema.demo.repositories.SessionRepository;
import veb.cinema.demo.repositories.TicketRepository;
import veb.cinema.demo.repositories.UserRepository;
import veb.cinema.demo.services.SessionService;

@Service
@EnableCaching
public class SessionServiceImpl implements SessionService {
    private static final String CACHE_KEY = "session";

    @Autowired
    private final SessionRepository sessionRepository;

    @Autowired
    private final ModelMapper modelMapper;

    @Autowired
    private final HallRepository hallRepository;

    @Autowired
    private final TicketRepository ticketRepository;

    @Autowired
    private final UserRepository userRepository;

    public SessionServiceImpl(SessionRepository sessionRepository, ModelMapper modelMapper, HallRepository hallRepository, TicketRepository ticketRepository, UserRepository userRepository) {
        this.sessionRepository = sessionRepository;
        this.modelMapper = modelMapper;
        this.hallRepository = hallRepository;
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }

    @Override
    @CacheEvict(value = CACHE_KEY, allEntries = true)
    public int create(String date, int filmId, int hallId, int price, int availableSeats, String time) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        LocalDate localDate = LocalDate.parse(date, dateFormatter);
        LocalTime localTime = LocalTime.parse(time, timeFormatter);

        Films film = new Films();
        film.setId(filmId);

        Hall hall = new Hall();
        hall.setId(hallId);

        MySession session = new MySession(localDate, film, hall, price, availableSeats, localTime);

        MySession createdSession = sessionRepository.create(session);

        return createdSession.getId();
    }

    @Override
    @Cacheable(value = CACHE_KEY )
    public List<SessionDto> findAll() {
        System.out.println("Service method was called");

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        return sessionRepository.findAll().stream()
                .map(session -> {
                    String formattedDate = session.getDate().format(dateFormatter);
                    String formattedTime = session.getTime().format(timeFormatter);

                    SessionDto sessionDto = modelMapper.map(session, SessionDto.class);

                    sessionDto.setDate(formattedDate);
                    sessionDto.setTime(formattedTime);

                    return sessionDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public SessionDto findById(int id) {
        Optional<MySession> sessionOptional = sessionRepository.findById(id);
        return sessionOptional.map(session -> {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

            String formattedDate = session.getDate().format(dateFormatter);
            String formattedTime = session.getTime().format(timeFormatter);

            SessionDto sessionDto = modelMapper.map(session, SessionDto.class);

            sessionDto.setDate(formattedDate);
            sessionDto.setTime(formattedTime);

            return sessionDto;
        }).orElse(null);
    }


    @Override
    @Cacheable(value = CACHE_KEY)
    public List<SessionDto> getTop3MostPurchasedSessions() {
        // Получаем все сессии из репозитория
        List<MySession> sessions = sessionRepository.findAll();

        // Карта для подсчета количества билетов по каждой сессии
        Map<MySession, Long> sessionTicketCountMap = new HashMap<>();

        // Подсчитываем количество билетов для каждой сессии
        for (MySession session : sessions) {
            long ticketCount = countBySession(session);
            sessionTicketCountMap.put(session, ticketCount);
        }

        // Форматеры для даты и времени
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        // Сортируем по количеству билетов и преобразуем в DTO
        return sessionTicketCountMap.entrySet().stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue())) // Сортируем по убыванию количества билетов
                .limit(3) // Оставляем только 3 самых популярных сессии
                .map(entry -> {
                    MySession session = entry.getKey();

                    // Форматируем дату и время
                    String formattedDate = session.getDate().format(dateFormatter);
                    String formattedTime = session.getTime().format(timeFormatter);

                    // Возвращаем DTO с нужными данными
                    return new SessionDto(
                            session.getId(),
                            formattedDate,
                            session.getFilm().getId(),
                            session.getHall().getId(),
                            session.getPrice(),
                            session.getAvailableSeats(),
                            formattedTime
                    );
                })
                .collect(Collectors.toList()); // Собираем результат в список
    }

    private long countBySession(MySession session) {
        return ticketRepository.countBySession(session);
    }


    @Transactional
    @Override
    @CacheEvict(value = CACHE_KEY)
    public SessionDto bookSeat(int sessionId) throws Exception {
        Optional<MySession> sessionOptional = sessionRepository.findById(sessionId);

        if (!sessionOptional.isPresent()) {
            throw new IllegalArgumentException("Session not found with ID: " + sessionId);
        }

        MySession session = sessionOptional.get();
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalStateException("User not found"));

        if (session.getAvailableSeats() > 0) {
            session.setAvailableSeats(session.getAvailableSeats() - 1);
            sessionRepository.save(session);

            Ticket ticket = new Ticket();
            ticket.setSession(session);
            ticket.setUser(user);
            ticketRepository.save(ticket);

            return modelMapper.map(session, SessionDto.class);
        } else {
            throw new IllegalArgumentException("No available seats for booking");
        }
    }
    @Transactional
    @Override
    @CacheEvict(value = CACHE_KEY)
    public void cancelBooking(int ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new IllegalArgumentException("Ticket not found with ID: " + ticketId));

        MySession session = ticket.getSession();
        session.setAvailableSeats(session.getAvailableSeats() + 1);
        sessionRepository.save(session);

        ticketRepository.delete(ticket);
    }

}
