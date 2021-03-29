package com.docholder.controllers;

import com.docholder.model.User;
import com.docholder.model.UserRole;
import com.docholder.service.UserService;
import com.docholder.utilities.Encrypt;
import com.docholder.utilities.GenerateJwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value ="/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody User user) {
        user.setPassword(new Encrypt().sha256(user.getPassword())); // need for refactoring
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

//    WHY POST ???
    @PostMapping(value ="/auth")
    public ResponseEntity<?> authorization(@RequestBody User user) {
        user.setPassword(new Encrypt().sha256(user.getPassword()));  // need for refactoring

        // search for user in DB by email and password
        User person = userService.authorization(user.getEmail(), user.getPassword());

        // if user doesn't exist return code 404
        if(person == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        // generate JWT token
        String token = new GenerateJwt(person).getToken();

        // return response
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PutMapping(value = "/{uid}")
    public ResponseEntity<?> update(@PathVariable(name = "uid") UUID uid, @RequestBody User user) {
        final boolean updated = userService.update(user, uid);

        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/{uid}")
    public ResponseEntity<?> delete(@PathVariable(name = "uid") UUID uid) {
        final boolean deleted = userService.delete(uid);

        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}