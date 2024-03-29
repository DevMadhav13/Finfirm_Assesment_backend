package com.finfirm.finfirmassesment.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.finfirm.finfirmassesment.entities.User;
import com.finfirm.finfirmassesment.payload.UserDto;
import com.finfirm.finfirmassesment.repo.UserRepository;
import com.finfirm.finfirmassesment.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService{
	

	@Autowired
	private UserRepository userRepo;

	@Autowired
	public ModelMapper modelMapper;

	@Autowired
	public PasswordEncoder passwordEncoder;

	@Override
	public UserDto createUser(UserDto userDto) {
		User user = this.modelMapper.map(userDto, User.class);
		log.info("initiating Repo call for save user from UserserviceIMPL");
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRole("user");
		User savedUser = this.userRepo.save(user);
		log.info("completed Repo call for save user from UserserviceIMPL");
		return this.modelMapper.map(savedUser, UserDto.class);
	}

}
