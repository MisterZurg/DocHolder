package com.docholder.repository;

import com.docholder.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

// "Волшебным образом" взаимодействует с нашими базами данных и таблицами.
public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("SELECT u FROM User u where (u.email = :email and u.password = :password)")
    User findUser(@Param("email") String email, @Param("password") String password);

    @Query("SELECT u FROM User u WHERE (u.email = :email)")
    User findUserByEmail(@Param("email") String email);
}