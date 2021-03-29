package com.docholder.service;

import com.docholder.model.User;
import com.docholder.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void create(User user) {
        userRepository.save(user);
    }

    @Override
    public List<User> readAll() {
        return userRepository.findAll();
    }

    // @Override
    public User read(UUID uid) {
        return userRepository.getOne(uid);
    }

    public User readByEmail(String email){
        return userRepository.findUserByEmail(email);
    }

    public User authorization(String email, String passmord) {
        return userRepository.findUser(email, passmord);
    }

    // @Override
    public boolean update(User user, UUID uid) {
        if (userRepository.existsById(uid)){
            user.setId(uid);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    // @Override
    public boolean delete(UUID uid) {
        if (userRepository.existsById(uid)) {
            userRepository.deleteById(uid);
            return true;
        }
        return false;
    }
}
