package com.docholder.config;

import com.docholder.model.User;
import com.docholder.model.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

// PreAuthorizate/PostAuthorizate verification
@RequiredArgsConstructor
@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {

    @Override
    public boolean hasPermission(
            Authentication auth, Object targetDomainObject, Object permission) {


        System.out.println("auth: "+auth);
        System.out.println("targetDomainObject: "+targetDomainObject);
//        ResponseEntity<?> token = (ResponseEntity<?>) targetDomainObject;
        System.out.println("permission: "+permission);

        if ((auth == null) || (targetDomainObject == null) || !(permission instanceof String)){
            return false;
        }

        User user = (User) targetDomainObject;
        if (user.getRole() != UserRole.ADMINISTRATOR) return false;

        return true;
    }

//    I don't sure for what this method, therefore always return false
    @Override
    public boolean hasPermission(
            Authentication auth, Serializable targetId, String targetType, Object permission) {
        System.out.println("second hasPermission");
        return false;
    }

//    private boolean hasPrivilege(String token, String action){
//
////        String secret = "somesecretwordsomesecretwordsomesecretwordsomesecretword";
////        byte[] keyBytes = Decoders.BASE64.decode(secret);
////        SecretKey key = Keys.hmacShaKeyFor(keyBytes);
//
//        System.out.println();
//        System.out.println("token: "+token);
//        System.out.println("action: "+action);
//
////        Jws<Claims> jws;
////        jws = Jwts.parserBuilder()
//////                .setSigningKey(key)
////                .build()
////                .parseClaimsJws(token);
//
//        return false;
//    }
}