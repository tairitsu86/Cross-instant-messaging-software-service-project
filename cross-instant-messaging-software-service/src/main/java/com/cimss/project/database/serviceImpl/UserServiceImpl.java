package com.cimss.project.database.serviceImpl;

import com.cimss.project.database.UserService;
import com.cimss.project.database.entity.User;
import com.cimss.project.database.repository.UserRepository;
import com.cimss.project.database.entity.UserId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void alterUserDisplayName(UserId userId, String newName) {
        User user = getUserById(userId);
        if(user==null)
            return;
        user.setUserDisplayName(newName);
        userRepository.save(user);
    }

    @Override
    public User getUserById(UserId userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        return optionalUser.get();
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    @Override
    public boolean isUserExist(UserId userId) {
        return userRepository.existsById(userId);
    }
}