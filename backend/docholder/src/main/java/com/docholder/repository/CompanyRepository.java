package com.docholder.repository;

import com.docholder.model.Company;
import com.docholder.model.CompanyStatus;
import com.docholder.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface CompanyRepository extends JpaRepository<Company, UUID> {

//    @Query("SELECT u FROM User u where (u.email = :email and u.password = :password)")
//    User findUser(@Param("email") String email, @Param("password") String password);
//
//    @Query("SELECT u FROM User u WHERE (u.email = :email)")
//    User findUserByEmail(@Param("email") String email);

    Page<Company> findAllByStatusAndNameContains(CompanyStatus status, String name, Pageable pageable);
    long countAllByStatusAndNameContains(CompanyStatus status, String name);
}
