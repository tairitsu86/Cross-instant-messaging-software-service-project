package com.cimss.project.security;

import com.cimss.project.database.entity.MemberId;
import com.cimss.project.database.entity.User;
import com.cimss.project.database.entity.UserId;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.function.Function;

@Slf4j
@Component
public class JwtUtilities{

    private Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private Long jwtExpiration = (long)0;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String email = extractUsername(token);
        return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(User user) {
        return Jwts.builder().setSubject(user.getUserId().toJsonString()).claim("role",user.getLoginRole()).setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(Date.from(Instant.now().plus(jwtExpiration, ChronoUnit.MILLIS)))
                .setExpiration(Date.from(Instant.ofEpochMilli(Long.MAX_VALUE)))
                .signWith(key).compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException e) {
            log.info("Invalid JWT token.");
            log.trace("Invalid JWT token trace: {}", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
            log.trace("Expired JWT token trace: {}", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token.");
            log.trace("Unsupported JWT token trace: {}", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid.");
            log.trace("JWT token compact of handler are invalid trace: {}", e);
        } catch (Exception e){
            log.info(String.format("JWT token Exception. With %s",e.getMessage()));
            log.trace("JWT token compact of handler are invalid trace: {}", e);
        }
        return false;
    }

    public String getToken (HttpServletRequest httpServletRequest) {
        final String bearerToken = httpServletRequest.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer "))
            return bearerToken.substring(7); // The part after "Bearer "
        return null;
    }
    public UserId jwtDecode(String accessToken){
        String jwt = accessToken.substring(7);
        String jsonUserId = extractUsername(jwt);
        return UserId.MappingFromJson(jsonUserId);
    }

}
