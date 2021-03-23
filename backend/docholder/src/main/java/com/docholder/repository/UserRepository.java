package com.docholder.repository;

import com.docholder.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

// "Волшебным образом" взаимодействует с нашими базами данных и таблицами.
public interface UserRepository extends JpaRepository<User, UUID> { }