package com.example.ollethboardproject.controller.response;

<<<<<<< HEAD:BoardSE/src/main/java/com/example/ollethboardproject/controller/response/LocalCommunityResponse.java
import com.example.ollethboardproject.domain.dto.LocalCommunityDTO;

=======
import com.example.ollethboardproject.domain.dto.CommunityMemberDTO;
>>>>>>> main:BoardSE/src/main/java/com/example/ollethboardproject/controller/response/CommunityMemberResponse.java
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;




@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CommunityMemberResponse {
    private String nickName;

    public static CommunityMemberResponse fromCommunityMemberDTO(CommunityMemberDTO localCommunityDTO){
        return new CommunityMemberResponse(localCommunityDTO.getMember().getNickName());
    }
}
