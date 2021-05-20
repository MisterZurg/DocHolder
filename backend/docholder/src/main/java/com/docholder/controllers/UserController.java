package com.docholder.controllers;

import com.docholder.model.*;
import com.docholder.repository.CompanyRepository;
import com.docholder.repository.UserRepository;
import com.docholder.service.CompanyService;
import com.docholder.service.UserService;
import com.docholder.utilities.Encrypt;
import com.docholder.utilities.Jwt;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value ="/user")
@RequiredArgsConstructor
public class UserController {
    private final UserMapper userMapper;
    private final UserService userService;
    private final CompanyService companyService;
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final JobOfferMapper jobOfferMapper;
    private final Jwt jwt;
    private final Encrypt encrypt;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody UserDto userDto) {
        User user = userMapper.dtoToEntity(userDto);
        user.setPassword(encrypt.sha256(user.getPassword()));
        user.setRole(UserRole.REGULAR_UNEMPLOYED);

        if(userService.readByEmail(user.getEmail()) != null){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        userService.create(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value ="/{id}")
    public ResponseEntity<User> read(@PathVariable(name = "id") UUID id) {
        final User user = userService.read(id);

        UserDto userDto = userMapper.entityToDto(user);
        return userDto != null
                ? new ResponseEntity(userDto, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value ="/company")
    public ResponseEntity<User> readByCompany(@RequestParam(name = "company_id") UUID id) {
        final List<User> users = userService.readByCompany(id);

        List<UserDto> usersDto = userMapper.entityToDto(users);
        return usersDto != null
                ? new ResponseEntity(usersDto, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value ="/auth")
    public ResponseEntity<?> authorization(@RequestBody UserDto userDto) {
        User user = userMapper.dtoToEntity(userDto);

        user.setPassword(encrypt.sha256(user.getPassword()));

        User person = userService.authorization(user.getEmail(), user.getPassword());

        if(person == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        String token = jwt.generateTokenByUser( userMapper.entityToDto(person) );

        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/avatar", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> updateAvatar(
            @PathVariable("id") UUID id,
            @RequestPart("file") MultipartFile avatar,
            @RequestHeader(value="Authorization", required = false) String token
    ){
        if(avatar.getSize() > 5242880) return new ResponseEntity<>(HttpStatus.PAYLOAD_TOO_LARGE);

        return userService.updateAvatar(id, avatar)
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PreAuthorize("hasPermission(#token, 'updateCompany')")
    @PostMapping(value = "/invite")
    public ResponseEntity<?> invite(
            @RequestBody JobOffer jobOffer,
            @RequestParam String email,
            @RequestHeader(value="Authorization", required = false) String token
    ){
        return companyService.invite(jobOffer, email)
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @GetMapping(value = "/{id}/invite")
    public ResponseEntity<?> getInvitations(@PathVariable(name = "id") UUID id){
        List<JobOffer> jobOffer = companyService.getInvitations(id);

        List<UUID> companyUUIDs = jobOffer.stream().map(JobOffer::getCompanyId).collect(Collectors.toList());
        Map<UUID, String> companyMap = companyRepository.findAllById(companyUUIDs).stream().collect(Collectors.toMap(Company::getId, Company::getName));

        List<UUID> employerUUIDs = jobOffer.stream().map(JobOffer::getEmployerId).collect(Collectors.toList());
        Map<UUID, String> employerMap = userRepository.findAllById(employerUUIDs).stream().collect(Collectors.toMap(User::getId, user -> user.getName()+" "+user.getSurname()));

        List<JobOfferDto> jobOffersDto = jobOfferMapper.entityToDto(jobOffer);
        jobOffersDto.forEach(jobOfferDto -> {
            jobOfferDto.setCompanyName( companyMap.get(jobOfferDto.getCompanyId()) );
            jobOfferDto.setEmployerFullName( employerMap.get(jobOfferDto.getEmployerId()) );
        });

        return new ResponseEntity<>(jobOffersDto, HttpStatus.OK);
    }

    @PreAuthorize("hasPermission(new com.docholder.utilities.JobOfferSecurityTransfer(#token, #id), 'setJobOfferStatus')")
    @PutMapping(value = "/invite/{id}/status")
    public ResponseEntity<?> setInviteStatus(
            @PathVariable UUID id,
            @RequestParam NoticeStatus status,
            @RequestHeader(value="Authorization", required = false) String token
    ){
        String newToken = companyService.setInviteStatus(id, status);
        return new ResponseEntity<>(newToken, HttpStatus.OK);
    }

}