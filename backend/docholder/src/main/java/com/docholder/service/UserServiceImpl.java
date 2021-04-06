package com.docholder.service;

import com.docholder.model.User;
import com.docholder.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Override
    public void create(User user) {
        userRepository.save(user);
    }

    @Override
    public List<User> readAll() {
        return userRepository.findAll();
    }

    // @Override
    public User read(UUID id) {
        return userRepository.getOne(id);
    }

    public User readByEmail(String email){
        return userRepository.findUserByEmail(email);
    }

    public User authorization(String email, String password) {
        return userRepository.findUser(email, password);
    }

    // @Override
    public boolean update(User user, UUID id) {
        if (userRepository.existsById(id)){
            user.setId(id);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    // @Override
    public boolean delete(UUID id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
