package com.example.MatchUp.Repository;

import com.example.MatchUp.Model.User;
import com.example.MatchUp.Model.UserIdAndPhone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT * FROM users WHERE phone = :phoneNumber", nativeQuery = true)
    Optional<User> findByPhone(String phoneNumber);

    @Query(value = "SELECT * FROM users WHERE email = :email", nativeQuery = true)
    Optional<User> findByEmail(String email);

    @Query(value = "SELECT id::BIGINT, phone FROM users", nativeQuery = true)
    List<UserIdAndPhone> findAllIdAndPhone();

    @Query(value = "SELECT id::BIGINT FROM users WHERE email = :email", nativeQuery = true)
    Long findIdByEmail(String email);
}
