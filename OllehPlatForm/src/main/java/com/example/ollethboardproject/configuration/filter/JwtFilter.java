package com.example.ollethboardproject.configuration.filter;

import com.example.ollethboardproject.controller.response.Response;
import com.example.ollethboardproject.domain.entity.Member;
import com.example.ollethboardproject.exception.ErrorCode;
import com.example.ollethboardproject.repository.TokenCacheRepository;
import com.example.ollethboardproject.service.MemberService;
import com.example.ollethboardproject.utils.ClassUtil;
import com.example.ollethboardproject.utils.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    public static final String BEARER = "Bearer ";
    private final MemberService memberService;
    private final String secretKey;
    private final TokenCacheRepository tokenCacheRepository;
    @Value("${jwt.access-expired}")
    private Long accessExpiredTimeMs;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //토큰 정보 가져오기(Header의 Authorization)
        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.info("authorization : {}", authorization);

        // 토큰이 null 또는 Bearer이 아닐 경우 메서드 종료
        if (authorization == null || !authorization.startsWith(BEARER)) {
            filterChain.doFilter(request, response);
            return;
        }
        //토큰 추출
        String accessToken = authorization.split(" ")[1];

        try {
            //Access 토큰 만료기간 확인
            if (JwtTokenUtil.isExpired(accessToken, secretKey)) {
                log.error("accessToken is invalid");
                //redis 에서 Access 토큰으로(key) refresh 토큰 추출(value)
                String refreshToken = tokenCacheRepository.getToken(accessToken).get();

                //Refresh 토큰 만료기간 확인
                if (JwtTokenUtil.isExpired(refreshToken, secretKey)) {
                    log.error("refreshToken is invalid");
                    filterChain.doFilter(request, response);
                    return;
                }
                //refresh 토큰이 유효하기에 access 토큰 재발급(갱신)
                String updatedAccessToken = JwtTokenUtil.createAccessToken(JwtTokenUtil.getUserName(refreshToken, secretKey), secretKey, accessExpiredTimeMs);
                //재발급된 토큰을 redis에 update
                tokenCacheRepository.setToken(updatedAccessToken, refreshToken);
                //재발급된 access 토큰을 Header 에 Authorization 을 통해 전달
                response(response, updatedAccessToken);
                filterChain.doFilter(request, response);
                return;
            }

            //get userName(토큰으로 부터 파싱 )
            String userName = JwtTokenUtil.getUserName(accessToken, secretKey);
            log.info("username : {}", userName);

            // userName 검증 후 User 객체 생성
            Member member = ClassUtil.castingInstance(memberService.loadUserByUsername(userName), Member.class).get();
            JwtFilter.saveAuthentication(request, member);

        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.error("Invalid JWT Token", e);
            filterChain.doFilter(request, response);
            return;
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT Token", e);
            filterChain.doFilter(request, response);
            return;
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT Token", e);
            filterChain.doFilter(request, response);
            return;
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty.", e);
            filterChain.doFilter(request, response);
            return;
        }
        filterChain.doFilter(request, response);
    }

    private static void response(HttpServletResponse response, String updatedAccessToken) throws IOException {
        response.setHeader("Authorization", updatedAccessToken);
        response.getWriter().write(Response.error(ErrorCode.INVALID_TOKEN.name()).toStream());
    }

    public static void saveAuthentication(HttpServletRequest request, Member member) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(member,
                null, member.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}
