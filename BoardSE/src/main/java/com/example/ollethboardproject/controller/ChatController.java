package com.example.ollethboardproject.controller;

import com.example.ollethboardproject.controller.request.chat.ChatRoomRequest;
import com.example.ollethboardproject.domain.dto.ChatRoomDTO;
import com.example.ollethboardproject.service.ChatService;
import com.example.ollethboardproject.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.awt.print.Pageable;
import java.util.List;

@RestController
@RequestMapping("/api/v1/chats")
@RequiredArgsConstructor
@Log4j2
public class ChatController {
    private final MemberService memberService;
    private final ChatService chatService;



    //채팅방 리스트 조회
    @GetMapping("/")
    public ResponseEntity<List<ChatRoomDTO>> findAllChatRoom(@PageableDefault(size = 10)Pageable pageable){
        log.info("GET /api/v1/chats");
        List<ChatRoomDTO> chatRoomDTOList = chatService.findAllRooms();
        return new ResponseEntity<>(chatRoomDTOList, HttpStatus.OK);
    }
//    @GetMapping("/list")
//    public String myMentoring(@PathVariable ("memberId") Long memberId, Model model){
//
//        //채팅방 목록 불러오기
//        model.addAttribute("rooms", chatService.findAllRooms());
//
//        return "/mentoring/myMentoring";
//    }
    //채팅방 개설
    @PostMapping("/create")
    public ResponseEntity<ChatRoomDTO> createRoom(@RequestBody ChatRoomRequest chatRoomRequest, Authentication authentication){

        ChatRoomDTO createdChatRoomDTO = chatService.createChatRoomDTO(chatRoomRequest.getRoomName(), authentication);

        log.info("GET /api/v1/chats/create"  );
        log.info("# Create Chat Room , name: "  );

//        chatService.createChatRoomDTO(createdChatRoomDTO.getName(), authentication);

        return new ResponseEntity<>(createdChatRoomDTO, HttpStatus.CREATED);
    }

    //채팅방 조회
//    @GetMapping("/room")
//    public void getRoom(@RequestParam String roomId, Model model,HttpSession session){
//        Long memberId = (Long) session.getAttribute(LOGIN_ID);
//        String memberProfileName = memberService.findById(memberId).getMemberProfileName();
//        model.addAttribute("memberProfileName",memberProfileName);
//
//
//        log.info("# get Chat Room, roomID : " + roomId);
//
//        model.addAttribute("room", chatService.findRoomById(roomId));
//    }







}