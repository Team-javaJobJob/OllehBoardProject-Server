package com.example.ollethboardproject.repository;

import com.example.ollethboardproject.domain.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    Image findByCommunityId(Long communityId);
}