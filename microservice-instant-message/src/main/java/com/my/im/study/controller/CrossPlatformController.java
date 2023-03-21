package com.my.im.study.controller;

import com.my.im.study.APIBody.ManageBody;
import com.my.im.study.database.entity.User;
import com.my.im.study.database.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/manage")
public class CrossPlatformController {

    @Autowired
    private UserRepository userRepository;

//    @PostMapping("/adduser")
//    public ManageBody test(@RequestHeader(name = "Authorization") String accessToken,
//                           @RequestHeader(name = "Platform") String platform,
//                           @RequestBody ManageBody manageBody){
//        userRepository.save(new User(manageBody.));
//        return manageBody;
//    }


    @PostMapping("/users")
    public ManageBody getUsers(@RequestHeader(name = "Authorization") String accessToken,
                     @RequestHeader(name = "Platform") String platform,
                     @RequestBody ManageBody manageBody){
        manageBody.setUser_list(userRepository.findAll());
        return manageBody;
    }
}
