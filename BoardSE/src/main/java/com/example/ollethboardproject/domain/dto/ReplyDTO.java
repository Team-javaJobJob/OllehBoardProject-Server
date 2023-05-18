<<<<<<< HEAD
package com.example.ollethboardproject.domain.dto;

import com.example.ollethboardproject.domain.entity.Reply;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ReplyDTO {
    private Long id;
    private String content;

    public static ReplyDTO fromEntity(Reply reply) {
        ReplyDTO replyDTO = new ReplyDTO();
        replyDTO.setId(reply.getId());
        replyDTO.setContent(reply.getContent());
        return replyDTO;
    }

    public static List<ReplyDTO> toList(List<Reply> replies) {
        List<ReplyDTO> replyDTOs = new ArrayList<>();
        for (Reply reply : replies) {
            replyDTOs.add(fromEntity(reply));
        }
        return replyDTOs;
    }

    public static List<ReplyDTO> fromEntityList(List<Reply> replies) {
        List<ReplyDTO> replyDTOS = new ArrayList<>();
        for (Reply reply : replies) {
            replyDTOS.add(fromEntity(reply));
        }
        return replyDTOS;
    }
=======
package com.example.ollethboardproject.domain.dto;

import com.example.ollethboardproject.domain.entity.Reply;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class ReplyDTO {
    private Long id;
    private String content;

    public static ReplyDTO fromEntity(Reply reply) {
        ReplyDTO replyDTO = new ReplyDTO();
        replyDTO.id = reply.getId();
        replyDTO.content = reply.getContent();
        return replyDTO;
    }

    public static List<ReplyDTO> toList(List<Reply> replies) {
        List<ReplyDTO> replyDTOs = new ArrayList<>();
        for (Reply reply : replies) {
            replyDTOs.add(fromEntity(reply));
        }
        return replyDTOs;
    }

    public static List<ReplyDTO> fromEntityList(List<Reply> replies) {
        List<ReplyDTO> replyDTOS = new ArrayList<>();
        for (Reply reply : replies) {
            replyDTOS.add(fromEntity(reply));
        }
        return replyDTOS;
    }
>>>>>>> main
}