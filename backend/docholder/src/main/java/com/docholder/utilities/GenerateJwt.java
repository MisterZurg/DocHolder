package com.docholder.utilities;

import com.docholder.model.User;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class GenerateJwt {
    @Getter
    String token = null;

    public GenerateJwt(User user){
        Map<String, Object> data = setData(user);

        // now and expire time
        Date now = new Date();
        Date exp = Date.from(LocalDateTime.now().plusDays(2)
                .atZone(ZoneId.systemDefault()).toInstant());

        // build JWT
        try {
            token = Jwts.builder()
                .setClaims(data)
                .setIssuedAt(now)
                .setNotBefore(now)
                .setExpiration(exp)
                .signWith(getSecureKey(), SignatureAlgorithm.HS256)
                .compact();
        } catch (JwtException e) {
            e.printStackTrace();
        }
    }

    // return key for encrypt JWS
    private Key getSecureKey(){
        String secret = "somesecretwordsomesecretwordsomesecretwordsomesecretword";
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // put user data to Map
    private Map setData(User user){
        Map<String, Object> data = new HashMap<>();
        data.put("id", user.getId());
        data.put("surname", user.getSurname());
        data.put("name", user.getName());
        data.put("email", user.getEmail());
        data.put("phone", user.getPhone());
        data.put("role", user.getRole());
        data.put("gender", user.getGender());
        data.put("company_id", user.getCompany_id());
        return data;
    }
}