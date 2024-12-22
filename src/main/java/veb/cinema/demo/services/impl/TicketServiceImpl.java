package veb.cinema.demo.services.impl;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import veb.cinema.demo.dto.SessionDto;
import veb.cinema.demo.dto.SessionWithTicketCountDto;
import veb.cinema.demo.models.*;
import veb.cinema.demo.dto.TicketDto;
import veb.cinema.demo.repositories.*;
import veb.cinema.demo.services.HallService;
import veb.cinema.demo.services.SessionService;
import veb.cinema.demo.services.TicketService;

@Service
@EnableCaching
public class TicketServiceImpl implements TicketService {
    private static final String CACHE_KEY = "ticket";

    @Autowired
    private final TicketRepository ticketRepository;

    @Autowired
    private final ModelMapper modelMapper;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final SessionRepository sessionRepository;

    @Autowired
    private final SessionService sessionService;

    @Autowired
    private final HallService hallService;

    public TicketServiceImpl(TicketRepository ticketRepository, ModelMapper modelMapper, SessionService sessionService, HallService hallService, SessionRepository sessionRepository, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.ticketRepository = ticketRepository;
        this.hallService = hallService;
        this.sessionService = sessionService;
        this.sessionRepository = sessionRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Cacheable(value = CACHE_KEY)
    public List<TicketDto> findAll() {
        return ticketRepository.findAll().stream()
                .map(ticket -> modelMapper.map(ticket, TicketDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<Ticket> findByCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return Collections.emptyList();
        }

        Object principal = authentication.getPrincipal();
        User user = null;
        if (principal instanceof User) {
            user = (User) principal;
        } else if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            user = userRepository.findByEmail(userDetails.getUsername())
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
        } else {
            throw new IllegalStateException("Unexpected principal type: " + principal.getClass().getName());
        }

        return ticketRepository.findAllByUserId(user.getId());
    }

    @Override
    public TicketDto findById(int id) {
        Optional<Ticket> ticket = ticketRepository.findById(id);
        return ticket.map(value -> modelMapper.map(value, TicketDto.class)).orElse(null);
    }

    @Override
    @CachePut(value = CACHE_KEY)
    public int create(int sessionId, int userId) {
        MySession session = new MySession();
        session.setId(sessionId);

        User user = new User();
        user.setId(userId);

        Ticket ticket = new Ticket(session, user);
        Ticket createdTicket = ticketRepository.create(ticket);

        return createdTicket.getId();
    }

    @Override
    public SessionWithTicketCountDto countBySession(SessionDto sessionDto) {
        // Assuming you have a way to retrieve the full session entity from the SessionDto.
        MySession session = sessionRepository.findById(sessionDto.getId())
                .orElseThrow(() -> new RuntimeException("Session not found"));

        // Get the ticket count for the given session
        long ticketCount = ticketRepository.countBySession(session);

        // Format the session details (assuming session.getDate() is LocalDate and session.getTime() is LocalTime)
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        String formattedDate = session.getDate().format(dateFormatter);  // Format the date
        String formattedTime = session.getTime().format(timeFormatter);  // Format the time

        // Create and return the DTO with all the session details and ticket count
        return new SessionWithTicketCountDto(
                session.getId(),                        // sessionId
                session.getFilm().getName(),            // filmName (assuming session.getFilm() returns a Film object)
                formattedDate,                          // sessionDate (formatted)
                formattedTime,                          // sessionTime (formatted)
                session.getHall().getHallNumber(),      // hallNumber (assuming session.getHall() returns a Hall object)
                session.getPrice(),                     // price
                session.getAvailableSeats(),            // availableSeats
                ticketCount                             // ticketCount
        );
    }


}
