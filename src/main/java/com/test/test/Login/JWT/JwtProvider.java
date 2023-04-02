package com.test.test.Login.JWT;

import com.test.test.Exception.BadRequestException;
import com.test.test.Login.RefreshToken.RefreshToken;
import com.test.test.Login.RefreshToken.RefreshTokenRepository;
import com.test.test.Member.Member;
import com.test.test.Redis.RedisService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Duration;
import java.util.Date;

@Slf4j
@Component
public class JwtProvider {

    private final Key key;

    private final RefreshTokenRepository refreshTokenRepository;

    private final UserDetailsService userDetailsService;

    private final RedisService redisService;

    @Value("${jwt.blacklist.access-token}")
    private String blackListATPrefix;


    public JwtProvider(@Value("${jwt.secret-key}") String SECRET_KEY,
                       RefreshTokenRepository refreshTokenRepository,
                       UserDetailsService userDetailsService,
                       RedisService redisService) {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        this.redisService = redisService;//redis 추가
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.refreshTokenRepository = refreshTokenRepository;
        this.userDetailsService = userDetailsService;
    }

    public String createRefreshToken(Member member, String roles) {
        long tokenInvalidedTime = 10000L*60*60*24;
        String refreshToken = this.createToken(member.getEmail(), roles, tokenInvalidedTime);

        RefreshToken refreshTokenObject = refreshTokenRepository.findByMember(member)
                .orElse(RefreshToken.builder()
                        .member(member)
                        .build());
        refreshTokenObject.updateTokenValue(refreshToken);
        refreshTokenRepository.save(refreshTokenObject);
        redisService.setValues(member.getEmail(), member.getRole(), Duration.ofMillis(tokenInvalidedTime));

        return refreshToken;
    }

    public void logout(String email, String accessToken) {
        long expireAccessTokenTime = getExpiredTime(accessToken).getTime() - new Date().getTime();
        redisService.setValues(blackListATPrefix + accessToken, email, Duration.ofMillis(expireAccessTokenTime));
        redisService.deleteValues(email);
    }
    public void checkRefreshToken(String email, String refreshToken) throws BadRequestException {
        String redisRT = redisService.getValues(email);
        if(!refreshToken.equals(redisRT)) {
            throw new BadRequestException("토큰이 만료 되었습니다.");
        }
    }

    public String createToken(String MemberEmail, String roles, Long TokenInvalidedTime) {
        Claims claims = Jwts.claims().setSubject(MemberEmail);
        claims.put("roles", roles);
        Date date = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(date)
                .setExpiration(new Date(date.getTime() + TokenInvalidedTime))
                .signWith(key, SignatureAlgorithm.HS256)/*해싱 알고리즘 및 키 설정*/
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch ( SecurityException | MalformedJwtException e) {
            log.info("Jwt claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return false;
    }

    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken);

        String memberEmail = claims.getSubject();

        UserDetails principal = userDetailsService.loadUserByUsername(memberEmail);

        return new UsernamePasswordAuthenticationToken(principal,"", null);
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
    /*Authorization 생성*/
    public String createAuthorizationToken(String memberEmail, String roles) {
        Long tokenInvalidedTime = 10000L*60*60;
        return this.createToken(memberEmail, roles, tokenInvalidedTime);
    }

    private Date getExpiredTime(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getExpiration();
    }

}
