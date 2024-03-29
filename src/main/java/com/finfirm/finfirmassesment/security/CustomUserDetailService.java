package com.finfirm.finfirmassesment.security;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.finfirm.finfirmassesment.entities.User;
import com.finfirm.finfirmassesment.repo.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomUserDetailService  implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private ModelMapper modelMapper;


    @Override
    public UserDetails loadUserByUsername(String username)  {
        Optional<User> byUsername = this.userRepo.findByUsername(username);
        if (byUsername.isPresent()) {
            User user = byUsername.get();
            UserInfoDetails mapped = this.modelMapper.map(user, UserInfoDetails.class);
            log.info("loging from custm info details servvice load by username method");
            log.info(String.valueOf(mapped));
            return mapped;

        } else {
            throw new UsernameNotFoundException("user not found with username " + username);

        }
    }}
