package com.my.im.study.database.serviceImpl;

import com.my.im.study.database.entity.Manage;
import com.my.im.study.database.entity.Member;
import com.my.im.study.database.entity.UserId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.im.study.database.UserService;
import com.my.im.study.database.entity.User;
import com.my.im.study.database.repository.UserRepository;

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
    public User getUserById(String instantMessagingSoftware,String instantMessagingSoftwareUserId) {
        Optional<User> optionalUser = userRepository.findById(new UserId(instantMessagingSoftware, instantMessagingSoftwareUserId));
        return optionalUser.get();
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(String instantMessagingSoftware,String instantMessagingSoftwareUserId) {
        userRepository.deleteById(new UserId(instantMessagingSoftware, instantMessagingSoftwareUserId));
    }

    @Override
    public boolean checkMember(Member member, UserId userId) {
        return userId.equals(new UserId(member.getInstantMessagingSoftwareForeignKey(), member.getInstantMessagingSoftwareUserIdForeignKey()));
    }

    @Override
    public boolean checkManage(Manage manage, UserId userId) {
        return userId.equals(new UserId(manage.getInstantMessagingSoftwareForeignKey(), manage.getInstantMessagingSoftwareUserIdForeignKey()));
    }
}