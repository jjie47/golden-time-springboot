package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.MemberInfoResponseDto;
import com.example.demo.dto.MemberProfileResponseDto;
import com.example.demo.dto.MemberUpdateRequestDto;
import com.example.demo.entity.Member;
import com.example.demo.repository.LikeRepository;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.ReviewRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class MemberServiceImpl implements MemberService{
	
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private ReviewRepository reviewRepository;
	@Autowired
	private LikeRepository likeRepository; 
	
	@PersistenceContext
    private EntityManager em;
	
	@Override
	public MemberInfoResponseDto getInfo(String memberId) {
		Optional<Member> data = memberRepository.findByMemberId(memberId);
		if(data.isEmpty()) {
			// TODO: 예외 처리
			return null;
		}
		return MemberInfoResponseDto.toDto(data.get());
	}
	@Override
	public MemberProfileResponseDto getProfile(String memberId) {
		Optional<Member> data = memberRepository.findByMemberId(memberId);
		long reviewCnt = reviewRepository.countByMember_MemberId(memberId);
		long likeCnt = likeRepository.countByMember_MemberId(memberId);
		System.out.println(reviewCnt);
		if(data.isEmpty()) {
			// TODO: 예외 처리
			return null;
		}
		return MemberProfileResponseDto.toDto(data.get(), reviewCnt, likeCnt);
	}
	
	@Override
	public boolean update(MemberUpdateRequestDto member) {
		Member data = em.find(Member.class, member.getMemberId());
		if(data==null) {
			return false;
		}
		data.setPassword(member.getPassword());
		data.setNickname(member.getNickname());
		data.setEmail(member.getEmail());
		data.setPhoneNumber(member.getPhoneNumber());
		return true;	
	}
	
	@Override
	public boolean delete(String memberId) {
		Optional<Member> data = memberRepository.findByMemberId(memberId);
		if(data.isPresent()) {
			memberRepository.deleteById(memberId);
			data = memberRepository.findByMemberId(memberId);
			if(data.isEmpty()) {
				return true;
			}
			// TODO : 예외 처리(멤버 삭제 실패)
			return false;
		}
		// TODO : 예외 처리(존재하지 않는 멤버)
		return false;
	}
}
