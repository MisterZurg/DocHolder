package com.docholder.utilities;

import com.docholder.model.User;
import com.docholder.model.UserDto;
import io.jsonwebtoken.*;
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
public class JwtImpl implements Jwt{

    @Override
    public String generateTokenByUser(UserDto userDto){
        String token = null;
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
    private Map<String, Object> setData(UserDto userDto){
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

    @Override
    public Map<String, Object> getData(String token){
        try {
            Jws<Claims> jws = Jwts.parserBuilder()
                    .setSigningKey(getSecureKey())
                    .build()
                    .parseClaimsJws(token);
            Claims body = jws.getBody();

            Map<String, Object> data = new HashMap<>();
            data.put("id", body.get("id"));
            data.put("surname", body.get("surname"));
            data.put("name", body.get("name"));
            data.put("email", body.get("email"));
            data.put("phone", body.get("phone"));
            data.put("role", body.get("role"));
            data.put("gender", body.get("gender"));
            data.put("company_id", body.get("company_id"));

            return data;
        } catch (Exception e){
            return null;
        }
    }

    @Override
    public boolean isValid(String token){
        try {
            Jws<Claims> jws = Jwts.parserBuilder()
                    .setSigningKey(getSecureKey())
                    .build()
                    .parseClaimsJws(token);
        } catch (Exception e){
            return false;
        }

        return true;
    }
}