package com.example.ollethboardproject.service;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.ollethboardproject.domain.entity.Community;
import com.example.ollethboardproject.domain.entity.Image;
import com.example.ollethboardproject.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
@Transactional
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;
    private final AmazonS3 s3client;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    public void saveImage(MultipartFile file, Community community) throws Exception {
        // Amazon S3에 파일 업로드
        String uniqueFileName = getUniqueFileName(file);
        // InputStream을 사용하여 MultipartFile을 File 객체로 변환할 필요없이 파일을 전송합니다.
        PutObjectRequest request = new PutObjectRequest(bucketName, uniqueFileName, file.getInputStream(), new ObjectMetadata());
        s3client.putObject(request);

        // 데이터베이스에 이미지 정보 저장
        // S3에 저장되어있는 파일URL로 변경하여 저장하도록 이미지 생성
        String publicImageUrl = s3client.getUrl(bucketName, uniqueFileName).toString();
        Image image = Image.of(uniqueFileName, publicImageUrl, community);
        imageRepository.save(image);
    }

    public void deleteImageByCommunity(Community community) {
        imageRepository.delete(imageRepository.findByCommunity(community));
    }

    //고유한 파일명 추출
    public String getUniqueFileName(MultipartFile file) {
        return UUID.randomUUID() + "_" + file.getOriginalFilename();
    }


}
