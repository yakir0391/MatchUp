package com.example.MatchUp.MatchUp_Event.Service;

import com.example.MatchUp.MatchUp_Event.Feign.UserClient;
import com.example.MatchUp.MatchUp_Event.Model.*;
import com.example.MatchUp.MatchUp_Event.Repository.CityRepository;
import com.example.MatchUp.MatchUp_Event.Repository.EventRepository;
import com.example.MatchUp.MatchUp_Event.Repository.SportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private SportRepository sportRepository;
    @Autowired
    private UserClient userClient;

    public EventService(EventRepository eventRepository,CityRepository cityRepository,SportRepository sportRepository,UserClient userClient) {
        this.eventRepository = eventRepository;
        this.cityRepository = cityRepository;
        this.sportRepository = sportRepository;
        this.userClient = userClient;
    }

    public Event createEvent(String title,
                             String sportName,
                             String cityName,
                             String location,
                             Date eventDate,
                             String eventTime,
                             int playersNeeded,
                             int playersJoined,
                             String userEmail) {

        int cityId = cityRepository.findByName(cityName)
                .orElseThrow(() -> new RuntimeException("City not found"))
                .getId();

        int sportId = sportRepository.findByName(sportName)
                .orElseThrow(() -> new RuntimeException("Sport not found"))
                .getId();

        Long organizerId = userClient.getUserIdByEmail(userEmail);

        Event event = new Event();
        event.setTitle(title);
        event.setSportId(sportId);
        event.setCityId(cityId);
        event.setLocation(location);
        event.setEventDate(eventDate);
        event.setEventTime(eventTime);
        event.setPlayersNeeded(playersNeeded);
        event.setPlayersJoined(playersJoined);
        event.setOrganizerId(organizerId);

        return eventRepository.save(event);
    }

    public Event updateEvent(String title,
                             String sportName,
                             String cityName,
                             String location,
                             Date eventDate,
                             String eventTime,
                             int playersNeeded,
                             int playersJoined,
                             String userEmail,
                             Long eventId){

        Long organizerId = userClient.getUserIdByEmail(userEmail);

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        if(!event.getOrganizerId().equals(organizerId)){
            throw new RuntimeException("You can only update events you created");
        }

        if (title != null && !title.isEmpty()) {
            event.setTitle(title);
        }

        if (sportName != null && !sportName.isEmpty()) {
            int sportId = sportRepository.findByName(sportName)
                    .orElseThrow(() -> new RuntimeException("Sport not found"))
                    .getId();
            event.setSportId(sportId);
        }

        if (cityName != null && !cityName.isEmpty()) {
            int cityId = cityRepository.findByName(cityName)
                    .orElseThrow(() -> new RuntimeException("City not found"))
                    .getId();
            event.setCityId(cityId);
        }

        if (location != null && !location.isEmpty()) {
            event.setLocation(location);
        }

        if (eventDate != null) {
            event.setEventDate(eventDate);
        }

        if (eventTime != null && !eventTime.isEmpty()) {
            event.setEventTime(eventTime);
        }

        if (playersNeeded > 0) {
            event.setPlayersNeeded(playersNeeded);
        }

        if (playersJoined >= 0) {
            event.setPlayersJoined(playersJoined);
        }

        return eventRepository.save(event);
    }

    public List<Event> getEventsByCityAndSport(String cityName, String sportName) {
        int cityId = cityRepository.findByName(cityName)
                .orElseThrow(() -> new RuntimeException("City not found"))
                .getId().intValue();

        int sportId = sportRepository.findByName(sportName)
                .orElseThrow(() -> new RuntimeException("Sport not found"))
                .getId().intValue();

        System.out.println("Sport id : " + sportId + " Sport name : " + sportName);
        System.out.println("City id : " + cityId + " City name : " + cityName);
        return eventRepository.findBySportIdAndCityId(cityId, sportId);
    }

    public List<EventDetails> getEventsByOrganizer(String email) {

        Long organizerId = userClient.getUserIdByEmail(email);
        String organizerPhone = userClient.getPhoneNumberByEmail(email);

        List<EventDetails> toReturn = eventRepository.findAllByOrganizerId(organizerId);

        for (EventDetails event : toReturn) {
            event.setOrganizerPhone(organizerPhone);
        }

        return toReturn;
    }

    public List<EventDetails> getAllEvents() {

        List<EventDetails> toReturn = new ArrayList<>();
        List<Event> events = eventRepository.findAllEvents();
        List<City> citiesList = cityRepository.findAllCities();
        List<Sport> sportsList = sportRepository.findAllSports();
        List<UserIdAndPhone> userIdAndPhones = userClient.getIdPhone();

        for (UserIdAndPhone userIdAndPhone : userIdAndPhones) {
            System.out.println(userIdAndPhone);
        }

        Map<Long, String> userIdToPhoneMap = userIdAndPhones.stream()
                .collect(Collectors.toMap(
                        UserIdAndPhone::getUserId,
                        UserIdAndPhone::getPhone));

        Map<Integer, String> citiesMap = citiesList.stream()
                .collect(Collectors.toMap(
                        City::getId,
                        City::getName));

        Map<Integer, String> sportsMap = sportsList.stream()
                .collect(Collectors.toMap(
                        Sport::getId,
                        Sport::getName));

        for (Event event : events) {
            String organizerPhone = userIdToPhoneMap.get(event.getOrganizerId());
            String sportName = sportsMap.get(event.getSportId());
            String cityName = citiesMap.get(event.getCityId());

            System.out.println(organizerPhone + " " + sportName + " " + cityName);

            toReturn.add(new EventDetails(
                    event.getId(),
                    event.getTitle(),
                    event.getLocation(),
                    event.getEventDate(),
                    event.getEventTime(),
                    event.getPlayersJoined(),
                    event.getPlayersNeeded(),
                    organizerPhone,
                    sportName,
                    cityName
            ));
        }

        return toReturn;
    }

    public Event deleteEvent(Long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new RuntimeException("Event not found"));
        eventRepository.delete(event);
        return event;
    }

    public List<Sport> getAllSports() {
        return sportRepository.findAllSports();
    }

    public List<City> getAllCities() {
        return cityRepository.findAllCities();
    }
}
