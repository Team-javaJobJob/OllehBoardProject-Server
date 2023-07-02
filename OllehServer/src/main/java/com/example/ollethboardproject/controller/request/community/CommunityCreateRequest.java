package com.example.ollethboardproject.controller.request.community;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommunityCreateRequest {
    private String region;
    private String interest;
    private String info;
    private String communityName;
    private String[] keywords;



    // MultipartFile은 Spring에서 제공하는 인터페이스로, 클라이언트가 업로드한 파일을 표현하는 역할을 합니다. 멀티파트 요청을 처리하면서 HTTP 요청의 파일 데이터를 전달 받을 때 사용됩니다. 이 인터페이스는 파일의 이름, 원본 파일 이름, 크기, 데이터 및 파일 타입과 같은 정보를 제공합니다.
    //
    //주로 다음과 같은 상황에서 사용됩니다:
    //
    //클라이언트가 파일을 업로드할 때 서버에서 업로드된 파일을 처리하는 경우.
    //파일 업로드 요청을 처리하고 서버에 저장된 파일의 메타데이터를 관리하는 경우.
}
