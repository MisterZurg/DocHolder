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

    Page<Company> findAllByStatus(CompanyStatus status, Pageable pageable);
    @Query("SELECT c FROM Company c WHERE c.name LIKE %:name% AND c.status= :status")
    long countAllByStatus(String name, CompanyStatus status); // Search name

    /*
    @Query("SELECT p FROM Pokemon p WHERE fts(:description) = true")
    List<Pokemon> search(@Param("description") String description);
     */
    @Query("SELECT c FROM Company c WHERE c.name LIKE %:name%")
    Page<Company> findCompanyByGivenSearch(String name, Pageable pageable);


}
