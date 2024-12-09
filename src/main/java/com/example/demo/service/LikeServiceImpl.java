package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.dto.LikeAddAgainRequestDto;
import com.example.demo.dto.LikeAddRequestDto;
import com.example.demo.dto.LikeItemDto;
import com.example.demo.dto.LikeListRequestDto;
import com.example.demo.dto.LikeListResponseDto;
import com.example.demo.entity.Like;
import com.example.demo.repository.DutyRepository;
import com.example.demo.repository.LikeRepository;
import com.example.demo.repository.MemberRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
public class LikeServiceImpl implements LikeService{
	
	@Autowired
	private LikeRepository likeRepository;
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private DutyRepository dutyRepository;
	
	@PersistenceContext
    private EntityManager em;
	
	@Override
	public List<LikeItemDto> getList(String memberId) {
		return likeRepository.findAllByMember_MemberIdOrderByLikeIdDesc(memberId).stream()
				.map(LikeItemDto::toDto)
				.collect(Collectors.toList());
	}
	@Override
	public List<LikeItemDto> getList(String memberId, int count) {
		return likeRepository.findAllByMember_MemberIdOrderByLikeIdDesc(memberId, Limit.of(count)).stream()
				.map(LikeItemDto::toDto)
				.collect(Collectors.toList());
	}
	@Override
	public List<LikeItemDto> getList(String memberId, String classification) {
		return likeRepository.findAllByMember_MemberIdAndClassificationOrderByLikeIdDesc(memberId, classification).stream()
				.map(LikeItemDto::toDto)
				.collect(Collectors.toList());
	}
	
	@Override
	public LikeListResponseDto getList(LikeListRequestDto likeReq) {
		Pageable pageable = PageRequest.of(likeReq.getPageNo()-1, likeReq.getNumOfRows());
		List<LikeItemDto> items =  likeRepository.findAllByMember_MemberIdOrderByLikeIdDesc(likeReq.getMemberId(), pageable).stream()
				.map(LikeItemDto::toDto)
				.collect(Collectors.toList());
		long totalCount = likeRepository.countByMember_MemberId(likeReq.getMemberId());
		return new LikeListResponseDto(items, likeReq.getPageNo(), likeReq.getNumOfRows(), totalCount);
	}
	@Override
	public LikeListResponseDto getListWithClassification(LikeListRequestDto likeReq) {
		Pageable pageable = PageRequest.of(likeReq.getPageNo()-1, likeReq.getNumOfRows());
		List<LikeItemDto> items =  likeRepository.findAllByMember_MemberIdAndClassificationOrderByLikeIdDesc(likeReq.getMemberId(), likeReq.getClassification(), pageable).stream()
				.map(LikeItemDto::toDto)
				.collect(Collectors.toList());
		long totalCount = likeRepository.countByMember_MemberIdAndClassification(likeReq.getMemberId(), likeReq.getClassification());
		return new LikeListResponseDto(items, likeReq.getPageNo(), likeReq.getNumOfRows(), totalCount);
	}
	
	@Override
	public boolean insert(LikeAddRequestDto likeReq) {
		Like result = likeRepository.save(new Like(likeReq.getClassification(), memberRepository.findByMemberId(likeReq.getMemberId()).get(), dutyRepository.findById(likeReq.getDutyId()).get()));
		Optional<Like> data = likeRepository.findById(result.getLikeId());
		if(data.isPresent()) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean insert(LikeAddAgainRequestDto likeReq) {
		Like like = likeRepository.save(new Like(likeReq.getLikeId(), likeReq.getClassification(), memberRepository.findByMemberId(likeReq.getMemberId()).get(), dutyRepository.findById(likeReq.getDutyId()).get()));
		Optional<Like> data = likeRepository.findById(like.getLikeId());
		if(data.isPresent()) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean delete(long likeId) {
		Optional<Like> data = likeRepository.findById(likeId);
		if(data.isPresent()) {
			likeRepository.deleteById(likeId);
			data = likeRepository.findById(likeId);
			if(data.isEmpty()) {
				return true;
			}
			// TODO : 예외 처리(즐겨찾기 삭제 실패)
			return false;
		}
		// TODO : 예외 처리(존재하지 않는 즐겨찾기)
		return false;
	}
}
