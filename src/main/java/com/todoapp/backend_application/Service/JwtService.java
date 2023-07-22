package com.todoapp.backend_application.Service;



import com.todoapp.backend_application.ExceptionHandling.GlobalException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtService {

    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

    Logger log = LoggerFactory.getLogger(GlobalException.class);

    public String extractUsername(String token) {
        log.debug("from extract username...................");
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        log.debug("from extract expiration...................");
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        log.debug("from extract claims...................");
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {//call from this class
        log.debug("from ..extractAllClaims.................");
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        log.debug("from is token expired...................");
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        log.debug("from validate token...................");
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
////////////////////////////////////////////////////////////////////////////////////////////
    public String generateToken(String userName, Authentication auth) throws Exception {
        log.debug("from generate token...................");
        Map<String,Object> claims=new HashMap<>();
        String scope = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        claims.put("scope", scope);
        log.debug("from generate token end........"+userName+"......"+claims+".....");
        return createToken(claims,userName);
    }

    private String createToken(Map<String, Object> claims, String userName) throws Exception {

        String token;
        try {
            log.debug("from create token...................");
            token = Jwts.builder()
                    .setClaims(claims)
                    .setSubject(userName)
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                    .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
            log.debug("from create token..........." + token + "........");
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return token;
    }

    private Key getSignKey() {
        log.debug("from get sign key...................");
        byte[] keyBytes= Decoders.BASE64.decode(SECRET);
        log.debug("from get sign key........."+new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName())+"..........");
        return new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());
//        return Keys.hmacShaKeyFor(keyBytes);
    }
}
