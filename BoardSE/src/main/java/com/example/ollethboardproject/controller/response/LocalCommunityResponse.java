<<<<<<< HEAD:BoardSE/src/main/java/com/example/ollethboardproject/controller/request/MemberLoginRequest.java
package com.example.ollethboardproject.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberLoginRequest {
    private String userName;
    private String password;
}
=======
package com.example.ollethboardproject.controller.response;

import com.example.ollethboardproject.domain.dto.LocalCommunityDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LocalCommunityResponse {
    private String nickName;

    public static LocalCommunityResponse fromLocalCommunityDTO(LocalCommunityDTO localCommunityDTO){
        return new LocalCommunityResponse(localCommunityDTO.getMember().getNickName());
    }
}
>>>>>>> main:BoardSE/src/main/java/com/example/ollethboardproject/controller/response/LocalCommunityResponse.java
