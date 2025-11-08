package com.example.MatchUp.MatchUp_Event.Repository;

import com.example.MatchUp.MatchUp_Event.Model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface CityRepository extends JpaRepository<City,Integer> {

    @Query(value = "SELECT * FROM cities WHERE name = :name", nativeQuery = true)
    Optional<City> findByName(String name);

    @Query(value = "SELECT * FROM cities", nativeQuery = true)
    List<City> findAllCities();
}
