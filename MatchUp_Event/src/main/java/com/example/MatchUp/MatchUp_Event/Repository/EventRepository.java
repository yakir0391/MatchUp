package com.example.MatchUp.MatchUp_Event.Repository;

import com.example.MatchUp.MatchUp_Event.Model.Event;
import com.example.MatchUp.MatchUp_Event.Model.EventDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    @Query(value = "SELECT * FROM events WHERE city_id = :cityId AND sport_id = :sportId", nativeQuery = true)
    List<Event> findBySportIdAndCityId(Integer sportId, Integer cityId);

    @Query(value = "SELECT * FROM events WHERE city_id = :cityId", nativeQuery = true)
    List<Event> findByCityId(Integer cityId);

    @Query(value = "SELECT * FROM events" ,nativeQuery = true)
    List<Event> findAllEvents();

    @Query(value = "SELECT e.id::BIGINT, e.title, e.location, e.event_date, e.event_time, " +
            " e.players_joined AS playersJoined, e.players_needed AS playersNeeded, " +
            "  null, " + /*u.phone AS organizerPhone*/
            " s.name AS sportName, c.name AS cityName " +
            " FROM events e " +
            " JOIN sports s ON e.sport_id = s.id " +
            " JOIN cities c ON e.city_id = c.id " +
            " " + /*JOIN users u ON e.organizer_id = u.id*/
            " WHERE e.organizer_id = :organizerId ",
            nativeQuery = true)
    List<EventDetails> findAllByOrganizerId(long organizerId);

    @Query(value = "DELETE FROM events WHERE organizer_id = :organizerId", nativeQuery = true)
    void deleteByOrganizerId(long organizerId);
}
