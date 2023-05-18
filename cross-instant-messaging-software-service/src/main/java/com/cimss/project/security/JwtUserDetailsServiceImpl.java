package com.cimss.project.security;

import com.cimss.project.database.DatabaseService;
import com.cimss.project.database.entity.User;
import com.cimss.project.database.entity.UserId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private DatabaseService databaseService;
    @Override
    public UserDetails loadUserByUsername(String jsonUserId) throws UsernameNotFoundException {
        User user = databaseService.getUserById(UserId.MappingFromJson(jsonUserId));
        if (user == null) throw new UsernameNotFoundException(String.format("No user found with user id '%s'.", jsonUserId));
        return user;
    }
}
