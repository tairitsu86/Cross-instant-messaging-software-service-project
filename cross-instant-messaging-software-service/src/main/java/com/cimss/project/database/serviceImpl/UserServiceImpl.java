package com.cimss.project.database.serviceImpl;

import com.cimss.project.database.UserService;
import com.cimss.project.database.entity.Member;
import com.cimss.project.database.entity.User;
import com.cimss.project.database.repository.UserRepository;
import com.cimss.project.database.entity.UserId;
import com.cimss.project.service.token.InstantMessagingSoftwareList;
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
        if(InstantMessagingSoftwareList.getInstantMessagingSoftwareToken(user.getInstantMessagingSoftware())==null)
            return null;
        return userRepository.save(user);
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