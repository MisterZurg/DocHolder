package com.docholder.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table(name = "job_offer")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class JobOffer {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

//    @OneToOne
//    @PrimaryKeyJoinColumn(name = "user_id")
//    private User user;
    private UUID user_id;

//    @OneToOne
//    @PrimaryKeyJoinColumn(name = "company_id")
//    private Company company;
    private UUID company_id;

//    @OneToOne
//    @PrimaryKeyJoinColumn(name = "employer_id")
//    private User employer;
    private UUID employer_id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private UserRole role;

    @Column(name = "message")
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private NoticeStatus status = NoticeStatus.NOT_ANSWERED;

}
