package com.example.ollethboardproject.controller.request.community;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommunityUpdateRequest {
    private String region;
    private String interest;
    private String info;
    private String communityName;
}
