//package com.example.ollethboardproject.service;
//
//
//import com.example.ollethboardproject.domain.entity.Member;
//import com.example.ollethboardproject.repository.ChatRepository;
//import com.example.ollethboardproject.repository.MemberRepository;
//import com.example.ollethboardproject.utils.ClassUtil;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.Authentication;
//import org.springframework.stereotype.Service;
//
//import java.awt.print.Pageable;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class ChatRoomService {
//
//
////    private final ChatRepository chatRepository;
////    private final MemberRepository memberRepository;
////    private final ChatEntity chatEntity;
//
//
//    //    //     전체 채팅방 조회
////    public List<ChatRoom> findAllRoom(Pageable pageable) {
////
////        List<ChatEntity> ChatRoom = chatRepository.findAll();
////
////        return ChatRoom.stream().map((ChatEntity chatRoomEntity) -> mapToChatRoomDTO(chatRoomEntity)).collect(Collectors.toList());
////    }
//
////    private ChatRoom mapToChatRoomDTO(ChatEntity chatRoomEntity) {
////        return ChatRoom.fromEntity(chatRoomEntity);
////    }
//
//
//
//        // roomName 으로 채팅방 만들기
////    public ChatRoom createChatRoom(String roomName, Authentication authentication){
////        Member member = ClassUtil.castingInstance(authentication.getPrincipal(),Member.class).get();
//////        채팅방 이름으로 채팅방 생성
////        ChatRoom chatRoom = new ChatRoom().create(roomName);
////        // map에 채팅방 아이디 , 만들어진 채팅룸저장
//////        chatRoomMap.put(chatRoom.getRoomId(), chatRoom);
////        chatRepository.save(chatEntity);
////
////        return chatRoom;
////    }
////
////    private ChatDTO mapToChatDto(ChatRoom chatRoom) {
////        return ChatDTO.fromEntity(ChatRoom);
////    }
//
////
////    // roomId 기준으로 찾기
////    public ChatRoom findByRoomId(String roomId){
////        return chatRoomMap.get(roomId);
////    }
////
//
////
////    // 채팅방 인원 +1
////    public void increaseUser(String roomId){
////
////        ChatRoom chatRoom = chatRoomMap.get(roomId);
////        chatRoom.setUserCount(chatRoom.getUserCount()+1);
////    }
////
////    // 채팅방 인원-1
////    public void decreaseUser(String roomId){
////        ChatRoom chatRoom = chatRoomMap.get(roomId);
////        chatRoom.setUserCount(chatRoom.getUserCount()-1);
////    }
////
////    // 채팅방 userList 에 user 추가
////    public String addUser(String roomId, String userName){
////
////        ChatRoom chatRoom = chatRoomMap.get(roomId);
////        String userUUID = UUID.randomUUID().toString();
////        // 아이디 중복 확인 후 userList추가
////        chatRoom.getUserList().put(userUUID,userName);
////        return userUUID;
////    }
////
////    //채팅방 유저 이름 중복확인
////    public String isDuplicateName(String roomId, String userName){
////
////        ChatRoom chatRoom = chatRoomMap.get(roomId);
////        String temp = userName;
////
////        // 만약 유저네임이 중복이면 랜덤 숫자 붙여줌
////        // 이 때 랜덤한 숫자를 붙였을때 getUserList 안에 있는 닉네임이라면 다시 랜덤숫자 또붙히기
////        while(chatRoom.getUserList().containsValue(temp)){
////            int ranNum = (int)(Math.random()*100)+1;
////            temp = userName+ranNum;
////        }
////        return temp;
////    }
////
////    // 채팅방 유저리스트 삭제
////    public void deleteUser(String roomId, String userUUID){
////        ChatRoom chatRoom = chatRoomMap.get(roomId);
////        chatRoom.getUserList().remove(userUUID);
////    }
////
////    // 채팅방 userName 조회
////    public String getUserName(String roomId, String userUUID){
////        ChatRoom chatRoom = chatRoomMap.get(roomId);
////
////        return chatRoom.getUserList().get(userUUID);
////    }
////
////    //채팅방 전체 userList 조회
////    public List<String> getUserList(String roomId){
////        List<String> list = new ArrayList<>();
////
////        ChatRoom chatRoom = chatRoomMap.get(roomId);
////
////        chatRoom.getUserList().forEach((key,value) -> list.add(value));
////
////        return list;
////    }
////
//
//}
