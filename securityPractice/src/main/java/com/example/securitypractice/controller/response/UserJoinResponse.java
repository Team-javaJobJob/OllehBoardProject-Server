package com.example.securitypractice.controller.response;

import com.example.securitypractice.domain.dto.User;
import com.example.securitypractice.domain.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserJoinResponse {

    private Long id;
    private String userName;
    private UserRole userRole;

    public static UserJoinResponse fromUser(User user) {
        return new UserJoinResponse(
                user.getId(),
                user.getUsername(),
                user.getUserRole()
        );
    }

}
