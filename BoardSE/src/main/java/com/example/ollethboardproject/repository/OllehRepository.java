package com.example.ollethboardproject.repository;

import com.example.ollethboardproject.domain.entity.Community;
import com.example.ollethboardproject.domain.entity.Member;
import com.example.ollethboardproject.domain.entity.Olleh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OllehRepository extends JpaRepository<Olleh, Long> { //Olleh 기본 키(primary key) 데이터 타입 = Long
    Optional<Olleh> findByMemberAndCommunity(Member member, Community community); //member 가 community 에 olleh(좋아요) 를 했는지 여부 확인 후 안했으면 optional 반환
    Integer countByCommunity(Community community); //community 에 대한 olleh 수 반환
    Optional<List<Olleh>> findByMember(Member member);
}