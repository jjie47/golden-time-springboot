package com.example.demo.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Duty;
import com.example.demo.entity.Like;
import com.example.demo.entity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>{
	List<Review> findAllByMember_MemberId(String memberId);
	List<Review> findAllByMember_MemberIdAndClassification(String memberId, String classification);
	
	@Query(value = "select r " +
					"from Review r join r.member m join r.duty d " +
					"where m.memberId = :memberId and :date <= r.updatedAt")
	List<Review> findAllByMember_MemberIdAndUpdatedAtGreaterThanEqualMonths(@Param("memberId") String memberId, @Param("date") LocalDateTime date);
}