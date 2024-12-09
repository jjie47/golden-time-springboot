package com.example.demo.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Duty;
import com.example.demo.entity.Like;
import com.example.demo.entity.Review;

import jakarta.transaction.Transactional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>{
	List<Review> findAllByMember_MemberIdOrderByUpdatedAtDesc(String memberId);
	List<Review> findAllByMember_MemberIdOrderByUpdatedAtDesc(String memberId, Pageable pageable);
	List<Review> findAllByMember_MemberIdAndUpdatedAtGreaterThanEqualOrderByUpdatedAtDesc(String memberId, LocalDateTime date);
	List<Review> findAllByMember_MemberIdAndUpdatedAtGreaterThanEqualOrderByUpdatedAtDesc(String memberId, LocalDateTime date, Pageable pageable);
	List<Review> findAllByMember_MemberIdAndClassificationOrderByUpdatedAtDesc(String memberId, String classification);
	List<Review> findAllByMember_MemberIdAndClassificationOrderByUpdatedAtDesc(String memberId, String classification, Pageable pageable);
	List<Review> findAllByMember_MemberIdAndUpdatedAtGreaterThanEqualAndClassificationOrderByUpdatedAtDesc(String memberId, LocalDateTime date, String classification);
	List<Review> findAllByMember_MemberIdAndUpdatedAtGreaterThanEqualAndClassificationOrderByUpdatedAtDesc(String memberId, LocalDateTime date, String classification, Pageable pageable);
//	@Query(value = "select r " +
//					"from Review r join r.member m join r.duty d " +
//					"where m.memberId = :memberId and :date <= r.updatedAt " +
//					"order by r.updatedAt desc")
//	List<Review> findAllByMember_MemberIdAndUpdatedAtGreaterThanEqualMonthsOrderByUpdatedAtDesc(@Param("memberId") String memberId, @Param("date") LocalDateTime date);

	long countByMember_MemberId(String memberId);
	long countByMember_MemberIdAndUpdatedAtGreaterThanEqual(String memberId, LocalDateTime date);
	long countByMember_MemberIdAndClassification(String memberId, String classification);
	long countByMember_MemberIdAndUpdatedAtGreaterThanEqualAndClassification(String memberId, LocalDateTime date, String classification);
}