package com.example.MatchUp.MatchUp_Event.Repository;

import com.example.MatchUp.MatchUp_Event.Model.Sport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface SportRepository extends JpaRepository<Sport, Integer> {

    @Query(value = "SELECT * FROM sports WHERE name = :name", nativeQuery = true)
    Optional<Sport> findByName(String name);

    @Query(value = "SELECT * FROM sports", nativeQuery = true)
    List<Sport> findAllSports();
}
