package com.docholder.utilities;

import com.docholder.model.User;
import com.docholder.model.UserDto;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class GenerateJwtImpl implements GenerateJwt{
    String token = null;

    public String generateTokenByUser(UserDto userDto){
        Map<String, Object> data = setData(userDto);

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

        return token;
    }

    // return key for encrypt JWS
    private Key getSecureKey(){
        String secret = "somesecretwordsomesecretwordsomesecretwordsomesecretword";
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // put user data to Map
    private Map setData(UserDto userDto){
        Map<String, Object> data = new HashMap<>();
        data.put("id", userDto.getId());
        data.put("surname", userDto.getSurname());
        data.put("name", userDto.getName());
        data.put("email", userDto.getEmail());
        data.put("phone", userDto.getPhone());
        data.put("role", userDto.getRole());
        data.put("gender", userDto.getGender());
        data.put("company_id", userDto.getCompany_id());
        return data;
    }
}