package com.example.ollethboardproject.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommunityCreateRequest {
    private String region;
    private String interest;
    private String info;
    private String communityName;
}
