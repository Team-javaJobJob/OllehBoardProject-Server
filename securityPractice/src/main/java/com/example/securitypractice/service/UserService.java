package com.example.securitypractice.service;

import com.example.securitypractice.domain.dto.User;
import com.example.securitypractice.domain.UserRole;
import com.example.securitypractice.domain.entity.UserEntity;
import com.example.securitypractice.exception.AppException;
import com.example.securitypractice.exception.ErrorCode;
import com.example.securitypractice.repository.UserRepository;
import com.example.securitypractice.utils.JwtTokenUtil;
import com.example.securitypractice.utils.TokenInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Value("${jwt.token.secret}")
    private String key;
    @Value("${jwt.access-expired}")
    private Long accessExpiredTimeMs;
    @Value("${jwt.refresh-expired}")
    private Long refreshExpiredTimeMs;

    public User loadUserByUserName(String userName) {
        return userRepository.findByUserName(userName).map(User::fromEntity).orElseThrow(() ->
                new AppException(ErrorCode.USERNAME_NOT_FOUND, String.format("%s not found", userName)));

    }
    @Transactional
    public User join(String userName, String password) {


        userRepository.findByUserName(userName)
                .ifPresent(user -> {
                    throw new AppException(ErrorCode.USERNAME_DUPLICATED, String.format("%s is duplicated", userName));
                });

        UserEntity userEntity = UserEntity.builder()
                .userName(userName)
                .password(encoder.encode(password))
                .role(UserRole.USER)
                .build();

        UserEntity savedEntity = userRepository.save(userEntity);




        return User.fromEntity(savedEntity);
    }

    @Transactional
    public TokenInfo login(String userName, String password) {
        //아이디 확인
        UserEntity userEntity = userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND, String.format("%s is not found", userName)));

        //패스워드 확인
        if (!encoder.matches(password, userEntity.getPassword())) {
            throw new AppException(ErrorCode.INVALID_PASSWORD, String.format("password is invalid"));
        }

        String accessToken = JwtTokenUtil.createAccessToken(userEntity.getUserName(), key, accessExpiredTimeMs);
        String refreshToken = JwtTokenUtil.createRefreshToken(userEntity.getUserName(), key, refreshExpiredTimeMs);
        return TokenInfo.createOf(accessToken, refreshToken);
    }
}
