package com.example.ollethboardproject.service;

import com.example.ollethboardproject.domain.dto.CommunityDTO;
import com.example.ollethboardproject.repository.OllehRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OllehService {
    private final OllehRepository ollehRepository;

    public void delete(CommunityDTO communityDTO) {
        ollehRepository.findByCommunityId(communityDTO.getId()).forEach(olleh -> {
            ollehRepository.delete(olleh);
        });

    }
}
