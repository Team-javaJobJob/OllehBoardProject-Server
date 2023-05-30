
package com.example.ollethboardproject.domain.dto;

import com.example.ollethboardproject.domain.entity.Reply;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReplyDTO {
    private Long id;
    private String content;
    private String nickname;

    public static ReplyDTO fromEntity(Reply reply) {
        String nickname = MemberDTO.fromEntity(reply.getMember()).getNickName();
        return new ReplyDTO(reply.getId(), reply.getContent(), nickname);
    }

    public static List<ReplyDTO> fromEntityList(List<Reply> replies) {
        List<ReplyDTO> replyDTOS = new ArrayList<>();
        for (Reply reply : replies) {
            replyDTOS.add(fromEntity(reply));
        }
        return replyDTOS;
    }
}