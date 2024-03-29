package com.finfirm.finfirmassesment.service.impl;


import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.lang.Objects;
import io.jsonwebtoken.security.Keys;

import com.finfirm.finfirmassesment.constants.SecurityConstansts;
import com.finfirm.finfirmassesment.service.JwtService;

@Component
public class JwtServiceImpl implements JwtService {
	    

    public String generateTokan (String username){
        Map<String , Objects> claims  = new HashMap<>();
        return Jwts.builder().setClaims(claims).setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*30))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    private Key getSignKey() {
        byte [] keyBytes = Decoders.BASE64.decode(SecurityConstansts.SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims extractAllClaims(String tokan){
        return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(tokan).getBody();
    }


    private <T> T extractClaims(String tokan, Function<Claims,T> claimresolver) {
        final Claims claims = extractAllClaims(tokan);
        return claimresolver.apply(claims);

    }

    public String extactusername (String tokan){
        return extractClaims(tokan,Claims::getSubject);
    }

    public Date extractExpirationdate(String tokan){
        return extractClaims(tokan, Claims::getExpiration);
    }

    private  Boolean isTokanExpired(String tokan){
        return extractExpirationdate(tokan).before(new Date());
    }

    public Boolean validateTokan (String tokan, UserDetails userDetails){
        final  String extactusername = extactusername(String.valueOf(userDetails));
        return  extactusername.equals(userDetails.getUsername())& !isTokanExpired(tokan);
    }


}
