package com.techdemy.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.techdemy.utils.Constants;
import com.techdemy.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

@Slf4j
@Component
public class JwtHelper {

    private final RSAPrivateKey privateKey;
    private final RSAPublicKey publicKey;

    @Value("${jwtExpirationTime}")
    private Integer jwtExpirationAmount;

    private Date tokenExpirationDate;

    public JwtHelper(RSAPrivateKey privateKey, RSAPublicKey publicKey) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

    public String createJwtForClaims(String subject, Map<String,String> claims) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Instant.now().toEpochMilli());
        calendar.add(Calendar.MONTH, jwtExpirationAmount);

        this.tokenExpirationDate = calendar.getTime();

        JWTCreator.Builder jwtBuilder = JWT.create().withSubject(subject);
        claims.forEach(jwtBuilder::withClaim);

        return jwtBuilder
                .withIssuer("techdemy")
                .withNotBefore(new Date())
                .withExpiresAt(calendar.getTime())
                .sign(Algorithm.RSA256(publicKey,privateKey));
    }

    public static String getCurrentLoggedInUsername() {
        Map<String,Object> claims = Utils.getClaims();
        return claims.get(Constants.CLAIMS_USERNAME).toString();
    }

    public static String getCurrentLoggedInUserId() {
        Map<String,Object> claims = Utils.getClaims();
        return claims.get(Constants.CLAIMS_USERID).toString();
    }

    public Date getTokenExpirationDate() {
        return this.tokenExpirationDate;
    }

}
