package com.docholder.controllers;

import com.docholder.model.User;
import com.docholder.model.UserDto;
import com.docholder.model.UserMapper;
import com.docholder.model.UserRole;
import com.docholder.service.UserService;
import com.docholder.utilities.Encrypt;
import com.docholder.utilities.GenerateJwt;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value ="/user")
@RequiredArgsConstructor
public class UserController {
    private final UserMapper userMapper;
    private final UserService userService;
    private final GenerateJwt generateJwt;
    private final Encrypt encrypt;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody UserDto userDto) {
        User user = userMapper.dtoToEntity(userDto);
        user.setPassword(encrypt.sha256(user.getPassword()));
        user.setRole(UserRole.REGULAR);

        if(userService.readByEmail(user.getEmail()) != null){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        userService.create(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<User>> read() {
        final List<User> users = userService.readAll();

//        How To convert List<User> to List<UserDto>????
//                  shit code
//        List<UserDto> usersDto = null;
//        users.stream().forEach(user -> {
//            usersDto.add( userMapper.entityToDto(user) );
//        });

        return users != null &&  !users.isEmpty()
                ? new ResponseEntity<>(users, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

//    @GetMapping(value ="/{uid}")
//    public ResponseEntity<User> read(@PathVariable(name = "uid") UUID uid) {
//
//        System.out.println();
//
//        final User user = userService.read(uid);
//
//        return user != null
//                ? new ResponseEntity<>(user, HttpStatus.OK)
//                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }

    @PostMapping(value ="/auth")
    public ResponseEntity<?> authorization(@RequestBody UserDto userDto) {
        User user = userMapper.dtoToEntity(userDto);

        user.setPassword(encrypt.sha256(user.getPassword()));

        // search for user in DB by email and password
        User person = userService.authorization(user.getEmail(), user.getPassword());

        // if user doesn't exist return code 404
        if(person == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        // generate JWT token
        String token = generateJwt.generateTokenByUser( userMapper.entityToDto(person) );

        // return response
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> update(@RequestBody UserDto userDto) {
        User user = userMapper.dtoToEntity(userDto);
        final boolean updated = userService.update(user, user.getId());

        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") UUID id) {
        final boolean deleted = userService.delete(id);

        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }





//  for testing spring security's role access
    @PreAuthorize("hasPermission(#user, 'write')")
    @PostMapping(value ="/securetest")
    public ResponseEntity<?> securetest(@RequestBody UserDto userDto) {
        return authorization(userDto);
    }

}