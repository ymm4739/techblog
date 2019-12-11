package com.zhumingbei.techblog.util;

import com.zhumingbei.techblog.constant.JWTConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class JWTUtil {
    public String create(String email) {
        Date now = new Date();
        JwtBuilder builder = Jwts.builder()
                .setId(email)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, JWTConstant.KEY)
                .setExpiration(new Date(now.getTime() + JWTConstant.TTL));
        return builder.compact();
    }

    private Claims parseJWT(String jwt) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(JWTConstant.KEY)
                    .parseClaimsJws(jwt)
                    .getBody();
            return claims;
        }catch (Exception e){
            return null;
        }
    }

    public String getIDFromJWT(String jwt) {
        Claims claims = parseJWT(jwt);
        if (claims == null) {
            return null;
        }
        return claims.getId();
    }

}
