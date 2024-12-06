package com.example.demo.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Member;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.Optional;


@Repository
public interface MemberRepository extends JpaRepository<Member, String>{
	
	Optional<Member> findByMemberId(String memberId);
	
	// Optional
	// : null 방지를 위한 객체
	// NullPointerException이 발생하지 않도록 클래스로 한번 감싼 것(Wrapper Class) ex) int -> Integer
	
	// 사용메서드
	// {Optional}.get(); // 값 가져오기 - 객체
	// {Optional}.isEmpty(); // 값이 비어있는지 - boolean
	// {Optional}.isPresent(); // 값이 들어있는지 - boolean
} 
