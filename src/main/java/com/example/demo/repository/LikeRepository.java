package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Like;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long>{
	Optional<Like> findByMember_MemberIdAndDuty_DutyId(String memberId, String dutyId);
	List<Like> findAllByMember_MemberIdOrderByLikeIdDesc(String memberId);
	List<Like> findAllByMember_MemberIdOrderByLikeIdDesc(String memberId, Limit limit);
	List<Like> findAllByMember_MemberIdOrderByLikeIdDesc(String memberId, Pageable pageable);
	List<Like> findAllByMember_MemberIdAndClassificationOrderByLikeIdDesc(String memberId, String classification);
	List<Like> findAllByMember_MemberIdAndClassificationOrderByLikeIdDesc(String memberId, String classification, Pageable pageable);
	long countByMember_MemberId(String memberId);
	long countByMember_MemberIdAndClassification(String memberId, String classification);
}
