package com.showstream.userservice.service.impl;

import com.showstream.userservice.dto.UserDetailsPrincipal;
import com.showstream.userservice.entity.User;
import com.showstream.userservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepo ;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<User> user = userRepo.findByEmail(email);

        if(!user.isPresent() )
             throw new UsernameNotFoundException("User not exist");

        return new UserDetailsPrincipal(user.get());
    }
}
