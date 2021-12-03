package com.doghandeveloper.doggu.common.security;

import com.doghandeveloper.doggu.account.domain.UserAccount;
import com.doghandeveloper.doggu.common.config.AppProperties;
import com.doghandeveloper.doggu.common.util.RedisUtil;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Service
public class TokenProvider {

    private final AppProperties appProperties;
    private final RedisUtil redisUtil;

    public String createAccessToken(Authentication authentication) {
        UserAccount userAccount = (UserAccount) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + appProperties.getAuth().getAccessTokenExpirationMsec());
        log.debug("expiryDate: " + expiryDate.toString());

        return Jwts.builder()
                .setSubject(userAccount.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, appProperties.getAuth().getTokenSecret())
                .compact();
    }

    public String createRefreshToken(Authentication authentication) {
        UserAccount userAccount = (UserAccount) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date((now.getTime() + appProperties.getAuth().getRefreshTokenExpirationMsec()));
        log.debug("expiryDate: " + expiryDate.toString());

        String refreshToken = Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, appProperties.getAuth().getTokenSecret())
                .compact();

        redisUtil.setDataExpire(userAccount.getUsername(), refreshToken, appProperties.getAuth().getRefreshTokenExpirationMsec() / 1000);

        return refreshToken;
    }

    public String getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(appProperties.getAuth().getTokenSecret())
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(appProperties.getAuth().getTokenSecret()).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature");
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT");
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT");
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT");
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty");
        }
        return false;
    }

    public boolean validateRefreshToken(String refreshToken, String userId) {
        return refreshToken.equals(redisUtil.getData(userId));
    }
}
