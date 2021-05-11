package com.docholder.repository;

import com.docholder.model.JobOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface JobOfferRepository extends JpaRepository<JobOffer, UUID> {

    @Query("SELECT j FROM JobOffer j WHERE (j.userId = :userId)")
    public List<JobOffer> findAllByUser_id(UUID userId);

    public List<JobOffer> findAllByUserIdIn(List<UUID> users);
}
