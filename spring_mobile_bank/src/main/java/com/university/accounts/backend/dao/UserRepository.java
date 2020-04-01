package com.university.accounts.backend.dao;

import com.university.accounts.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsUserByLogin(String login);
    Boolean existsUserByPhone(String phone);

    @Query("SELECT e FROM User e WHERE e.login = :login")
    Optional<User> findByLogin(@Param("login") String login);

    @Query("SELECT e FROM User e WHERE e.phone = :phone")
    Optional<User> findByPhone(@Param("phone") String phone);

    @Query("SELECT e FROM User e WHERE e.login = :name OR e.phone = :name")
    Optional<User> findByLoginOrPhone(String name);
}
