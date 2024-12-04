package com.example.demo.repository;

import java.util.List;

import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Like;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long>{
	List<Like> findAllByMember_MemberId(String memberId);
	List<Like> findAllByMember_MemberId(String memberId, Limit limit);
	List<Like> findAllByMember_MemberIdAndClassification(String memberId, String classification);
}
