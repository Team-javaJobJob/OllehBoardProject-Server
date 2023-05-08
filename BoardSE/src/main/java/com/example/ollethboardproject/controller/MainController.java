package com.example.ollethboardproject.controller;

import com.example.ollethboardproject.controller.response.Response;
import com.example.ollethboardproject.domain.dto.MemberDTO;
import com.example.ollethboardproject.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class MainController {

    private final MemberService memberService;

    @GetMapping("/api/v1/main")
    public ResponseEntity<String> mainPage() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>("{\"message\":\"Hello, World!\"}", headers, HttpStatus.OK);
    }

    
    @PostMapping("/api/v1/loginAfter/{id}")
    public ResponseEntity<String> loginAfter() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>("{\"message\":\"Login After\"}", headers, HttpStatus.OK);
    }
}

