package com.docholder.service;

import com.docholder.model.Company;
import com.docholder.model.User;
import com.docholder.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    @Override
    public User read(UUID id) {
        return userRepository.getOne(id);
    }

    @Override
    public User readByEmail(String email){
        return userRepository.findUserByEmail(email);
    }

    @Override
    public List<User> readByCompany(UUID id){
        return userRepository.findUserByCompany(id);
    }

    @Override
    public User authorization(String email, String password) {
        return userRepository.findUser(email, password);
    }

    @Override
    public boolean update(User user, UUID id) {
        if (userRepository.existsById(id)){
            user.setId(id);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(UUID id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateAvatar(UUID id, MultipartFile avatar){
        User user = userRepository.getOne(id);

        try {
            user.setAvatar(avatar.getBytes());
            userRepository.save(user);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
