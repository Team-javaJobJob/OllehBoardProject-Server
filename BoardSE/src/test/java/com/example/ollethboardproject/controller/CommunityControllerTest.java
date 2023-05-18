package com.example.ollethboardproject.controller;

import com.example.ollethboardproject.controller.request.community.CommunityCreateRequest;
import com.example.ollethboardproject.controller.request.community.CommunityUpdateRequest;
import com.example.ollethboardproject.domain.Gender;
import com.example.ollethboardproject.domain.dto.CommunityDTO;
import com.example.ollethboardproject.domain.entity.Member;
import com.example.ollethboardproject.repository.CommunityRepository;
import com.example.ollethboardproject.service.CommunityService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CommunityControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    CommunityService communityService;
    @MockBean
    CommunityRepository communityRepository;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    @WithMockUser
    void 전체_커뮤니티_조회() throws Exception {
        // 반환 객체인 Member 정보 세팅
        Member member = new Member("userName", "password", "nickName", Gender.FEMALE);
        // 반환 객체 세팅
        List<CommunityDTO> communityDTOList = new ArrayList<>();
        communityDTOList.add(new CommunityDTO(1L, "region", "interest", "info", "communityName", member));

        when(communityService.findAllCommunities()).thenReturn(communityDTOList);

        mockMvc.perform(get("/api/v1/communities")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.region").value("region"))
                .andExpect(jsonPath("$.interest").value("interest"))
                .andExpect(jsonPath("$.info").value("info"))
                .andExpect(jsonPath("$.communityName").value("communityName"));
    }

    @Test
    @WithMockUser
    void 커뮤니티_생성() throws Exception {
        // 반환 객체인 Member 정보 세팅
        Member member = new Member("userName", "password", "nickName", Gender.FEMALE);
        // 반환 객체 세팅
        CommunityDTO communityDTO = new CommunityDTO(1L, "region", "interest", "info","communityName", member);
        when(communityService.createCommunity(any(), any()))
                .thenReturn(communityDTO);

        mockMvc.perform(post("/api/v1/communities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new CommunityCreateRequest("region", "interest", "info", "communityName")))
                ).andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser
    void 커뮤니티_정보_수정() throws Exception {
        // 반환 객체인 Member 정보 세팅
        Member member = new Member("userName", "password", "nickName", Gender.FEMALE);
        // 반환 객체 세팅
        CommunityDTO communityDTO = new CommunityDTO(1L, "region", "interest", "info","communityName", member);

        when(communityService.updateCommunity(anyLong(), any(), any())).thenReturn(communityDTO);

        mockMvc.perform(put("/api/v1/communities/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new CommunityUpdateRequest("region", "interest", "info", "communityName")))
                ).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void 커뮤니티_삭제() throws Exception {

        doNothing().when(communityService).deleteCommunity(anyLong(), any());

        mockMvc.perform(delete("/api/v1/communities/1")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isNoContent());
    }
}