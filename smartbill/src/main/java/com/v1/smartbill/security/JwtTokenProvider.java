package com.v1.smartbill.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;

@Component
public class JwtTokenProvider {

    //metodo para crear token
        private static final String JWT_SECRET_KEY = "TExBVkVfTVVZX1NFQ1JFVEzE3Zmxu/BSGSJx72BSBXM";

        private static final long JWT_TIME_VALIDITY = 1000 * 60 * 15;

        private SecretKey getKey() {
            byte[] keyBytes= Decoders.BASE64.decode(JWT_SECRET_KEY);
            return Keys.hmacShaKeyFor(keyBytes);
        }

        public String generateToken(Authentication authentication) {
            var claims = new HashMap<String, Object>();

            String token = Jwts.builder()
                    .claims(claims)
                    .subject(authentication.getName())
                    .issuedAt(new Date(System.currentTimeMillis()))
                    .expiration(new Date(System.currentTimeMillis() + JWT_TIME_VALIDITY))
                    .signWith(getKey())
                    .compact();
            return token;
        }

        public String extractClaim(String token) {
            try {
                Claims claims =
                        Jwts.parser()
                                .verifyWith(getKey())
                                .build()
                                .parseSignedClaims(token)
                                .getPayload();
                System.out.println("Claims extraídos: " + claims);
                return claims.getSubject();

            } catch (Exception e) {
                throw new RuntimeException("Token no válido");
            }
        }

        public boolean validateToken(String token) {
            try {
                Jwts.parser()
                        .setSigningKey(JWT_SECRET_KEY)
                        .build()
                        .parseClaimsJws(token);
                        return true;
            } catch (Exception e){
                throw new AuthenticationCredentialsNotFoundException("");
            }
        }

    /*
    public boolean validateToken(String token, UserDetails userDetails) {
        return extractClaim(token, Claims::getSubject).equals(userDetails.getUsername())
                && !extractClaim(token,Claims::getExpiration).before(new Date());
    }
    */

}
