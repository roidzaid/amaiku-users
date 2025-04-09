package com.amaiku.users.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class JwtService {

    public String createToken(String usuario, List<String> roles){

        return JWT.create()
                .withIssuer("Usuarios-token")
                .withIssuedAt(new Date())
                .withNotBefore(new Date())
                //.withExpiresAt(new Date(System.currentTimeMillis() + 360000))
                .withClaim("user", usuario)
                .withArrayClaim("roles", roles.toArray(new String[0]))
                .sign(Algorithm.HMAC256("clave-secreta-usuarios-api"));


    }

    public boolean isBearer(String authorization){
        return authorization != null
                && authorization.startsWith("Bearer ")
                && authorization.split("\\.").length == 3;
    }

    public String user(String authorization) throws Exception{
        return this.verify(authorization).getClaim("user").asString();
    }

    private DecodedJWT verify(String authorization) throws Exception{

        if(!this.isBearer(authorization)){
            throw new Exception("no es bearer");
        }

        try {
            return JWT.require(Algorithm.HMAC256("clave-secreta-usuarios-api"))
                    .withIssuer("Usuarios-token").build()
                    .verify(authorization.substring("Bearer ".length()));
        }catch (Exception e) {
            throw new Exception("JWT es erroneo " + e.getMessage());
        }
    }

    public List<String> roles(String authorization) throws Exception{
        return Arrays.asList(this.verify(authorization).getClaim("roles").asArray(String.class));
    }
}
