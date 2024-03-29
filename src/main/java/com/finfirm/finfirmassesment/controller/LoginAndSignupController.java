package com.finfirm.finfirmassesment.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import com.finfirm.finfirmassesment.entities.User;
import com.finfirm.finfirmassesment.exceptions.ApiException;
import com.finfirm.finfirmassesment.payload.JwtAuthRequest;
import com.finfirm.finfirmassesment.payload.JwtAuthResponse;
import com.finfirm.finfirmassesment.payload.UserDto;
import com.finfirm.finfirmassesment.repo.UserRepository;
import com.finfirm.finfirmassesment.security.CustomUserDetailService;
import com.finfirm.finfirmassesment.security.JwtTokenHelper;
import com.finfirm.finfirmassesment.service.UserService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/auth")
@Slf4j
public class LoginAndSignupController {

    @Autowired
    private CustomUserDetailService customUserDetailService;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtTokenHelper jwtTokenHelper;
    @Autowired
    private ModelMapper modelmapper;
    	

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    
	@GetMapping("/data")
	public String getMethodName() {
		return "Hello World";
	}

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception {
        log.info("loging api triggere");
        log.info(request.getUsername());
        log.info(request.getPassword());
        this.authenticate(request.getUsername(), request.getPassword());
        log.info("loging api triggered, user authenticated");
        UserDetails userDetails = this.customUserDetailService.loadUserByUsername(request.getUsername());
        log.info("loging api triggered, user details retrived");
        log.info(userDetails.getUsername(),userDetails.getPassword(),userDetails.getAuthorities());
        String token = this.jwtTokenHelper.generateToken(userDetails);
        log.info("loging api triggered, tokan genertaed");
        JwtAuthResponse response = new JwtAuthResponse();
        response.setToken(token);
        log.info("loging api triggered, tokan added to responce");
        log.info("loging api triggered,returned");
        return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);
    }

    private void authenticate(String username, String password) throws Exception {
        log.info("in authenicate method before uthintication tokan");
        log.info(username);
        log.info(password);
//        String  encodedpassword = passwordEncoder.encode(password);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
                password);
        if (password != null) {

            log.info("authe tokan password mising");
        }
        if (authenticationToken.getCredentials()!=null) {
            try {
                log.info("in authenicate method Try block");
                log.info((String) authenticationToken.getCredentials());
                log.info(String.valueOf(authenticationManager.getClass()));
                this.authenticationManager.authenticate(authenticationToken);
                log.info("in authenicate method end of Try block");

            } catch (BadCredentialsException e) {
                log.info("in authenicate method catch block");
                System.out.println("Invalid Detials !!");
                throw new ApiException("Invalid username or password !!");
            }
        }else{
            log.info("password was null ");
        }

    }

    // register new user api

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@Valid @RequestBody UserDto userDto) {
        UserDto registeredUser = this.userService.createUser(userDto);
        return new ResponseEntity<UserDto>(registeredUser, HttpStatus.CREATED);
    }

    @GetMapping("/current-student/")
    public ResponseEntity<UserDto> getUser(Principal principal) {
        User user = this.userRepo.findByEmail(principal.getName()).get();
        return new ResponseEntity<>(this.modelmapper.map(user, UserDto.class), HttpStatus.OK);
    }
}
		


