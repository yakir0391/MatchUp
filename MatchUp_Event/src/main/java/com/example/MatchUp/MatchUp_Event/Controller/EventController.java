package com.example.MatchUp.MatchUp_Event.Controller;

import com.example.MatchUp.MatchUp_Event.JWT.JwtUtil;
import com.example.MatchUp.MatchUp_Event.Model.*;
import com.example.MatchUp.MatchUp_Event.Service.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;
    private final JwtUtil jwtUtil;

    public EventController(EventService eventService, JwtUtil jwtUtil) {
        this.eventService = eventService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/create")
    public ResponseEntity<Event> createEvent(@RequestHeader("Authorization") String authHeader,
                                             @RequestBody EventCreateUpdate eventCreate) {

        if(!authHeader.startsWith("Bearer ") || authHeader == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = authHeader.substring(7);
        String email;
        try {
           email = jwtUtil.extractEmail(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Event event = eventService.createEvent(eventCreate.getTitle(), eventCreate.getSportName(), eventCreate.getCityName(),
                eventCreate.getLocation(),eventCreate.getEventDate(),eventCreate.getEventTime(),eventCreate.getPlayersNeeded(),
                eventCreate.getPlayersJoined(), email);

        return ResponseEntity.ok(event);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@RequestHeader("Authorization") String authHeader,
                                             @PathVariable Long id,
                                             @RequestBody EventCreateUpdate eventupdate){
        if(!authHeader.startsWith("Bearer ") || authHeader == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = authHeader.substring(7);
        String email;
        try {
            email = jwtUtil.extractEmail(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Event event = eventService.updateEvent(eventupdate.getTitle(), eventupdate.getSportName(), eventupdate.getCityName(),
                eventupdate.getLocation(),eventupdate.getEventDate(),eventupdate.getEventTime(),eventupdate.getPlayersNeeded(),
                eventupdate.getPlayersJoined(), email, id);

        return ResponseEntity.ok(event);

    }

    @GetMapping("/bySportAndCity")
    public ResponseEntity<List<Event>> getEventsByCityAndSport(@RequestParam String cityName,
                                                 @RequestParam String sportName) {
        return ResponseEntity.ok(eventService.getEventsByCityAndSport(cityName, sportName));
    }

    @GetMapping("/my-events")
    public ResponseEntity<List<EventDetails>> getMyEvents(@RequestHeader("Authorization") String authHeader) {

        if(!authHeader.startsWith("Bearer ") || authHeader == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = authHeader.substring(7);
        String email;
        try {
            email = jwtUtil.extractEmail(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<EventDetails> events = eventService.getEventsByOrganizer(email);
        return ResponseEntity.ok(events);
    }

    @GetMapping
    public ResponseEntity<List<EventDetails>> getEvents() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Event> deleteEvent(@RequestHeader("Authorization") String authHeader, @PathVariable Long id) {

        if(!authHeader.startsWith("Bearer ") || authHeader == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String token = authHeader.substring(7);
        String email;

        try {
            email = jwtUtil.extractEmail(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(eventService.deleteEvent(id));
    }

    @GetMapping("/sports")
    public ResponseEntity<List<Sport>> getAllSports() {
        return ResponseEntity.ok(eventService.getAllSports());
    }

    @GetMapping("/cities")
    public ResponseEntity<List<City>> getAllCities() {
        return ResponseEntity.ok(eventService.getAllCities());
    }
}
