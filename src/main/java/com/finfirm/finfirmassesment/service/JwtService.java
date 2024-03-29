package com.finfirm.finfirmassesment.service;

import java.util.Date;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.swagger.v3.oas.annotations.servers.Server;

@Service
public interface JwtService {
	

    public String generateTokan (String username);
    public String extactusername (String tokan);
    public Date extractExpirationdate(String tokan);
    public Boolean validateTokan (String tokan, UserDetails userDetails);

}
