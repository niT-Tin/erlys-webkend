package com.example.erlysflexq.utils;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class JwtUtil {

    //密钥
    @Value("${flexq.jwt.secret}")
    private String secret;
    //过期时间
    @Value("${flexq.jwt.expire}")
    private Integer expire;

    public String generateToken(Long userId){
        Date date = DateUtil.offset(new Date(), DateField.DAY_OF_YEAR, expire);
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTCreator.Builder builder = JWT.create();
        return builder.withClaim("userId", userId).withExpiresAt(date).sign(algorithm);
    }

    public int getUserId(String token){
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getClaim("userId").asInt();
    }

    public void verifyToken(String token){
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm).build();
        verifier.verify(token);
    }


}
