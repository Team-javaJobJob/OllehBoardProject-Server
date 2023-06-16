package com.example.ollethboardproject.service;

import com.example.ollethboardproject.controller.request.community.CommunityCreateRequest;
import com.example.ollethboardproject.controller.request.community.CommunityUpdateRequest;
import com.example.ollethboardproject.domain.dto.CommunityDTO;
import com.example.ollethboardproject.domain.entity.Community;
import com.example.ollethboardproject.domain.entity.Keyword;
import com.example.ollethboardproject.exception.ErrorCode;
import com.example.ollethboardproject.exception.OllehException;
import com.example.ollethboardproject.repository.KeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class KeywordService {

    private final KeywordRepository keywordRepository;

    public void saveKeywordAndCommunity(CommunityCreateRequest communityCreateRequest, Community community) {
        //저장할 키워드 추출
        String[] keywords = communityCreateRequest.getKeywords();
        //키워드 저장
        Arrays.stream(keywords).forEach(keyword -> {
            keywordRepository.save(Keyword.of(keyword, community));
        });
    }

    public void saveKeywordToUpdateCommunity(CommunityUpdateRequest communityUpdateRequest, Community community) {
        //키워드 추출
        String[] keywords = communityUpdateRequest.getKeywords();
        //수정할 키워드 저장
        Arrays.stream(keywords).forEach(optKeyword -> {
            keywordRepository.save(Keyword.of(optKeyword, community));
        });
    }

    public List<CommunityDTO> findCommunitiesByKeyword(String keyword) {
        List<Keyword> keywords = keywordRepository.findByKeyword(keyword).orElseThrow(
                () -> new OllehException(ErrorCode.KEYWORD_DOES_NOT_EXIST));

        List<CommunityDTO> communityDTOList = keywords.stream()
                .map(Keyword::getCommunity)
                .map(this::mapToCommunityDto)
                .collect(Collectors.toList());

        return communityDTOList;
    }

    // entity -> dto 변환 메서드
    private CommunityDTO mapToCommunityDto(Community community) {
        return CommunityDTO.fromEntity(community);
    }

    public void deleteKeywordByCommunity(Community community) {
        keywordRepository.findByCommunity(community).forEach(keyword -> {
            keywordRepository.delete(keyword);
        });
    }
}
