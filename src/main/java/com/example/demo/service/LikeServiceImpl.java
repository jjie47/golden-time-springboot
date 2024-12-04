package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Limit;
import org.springframework.stereotype.Service;

import com.example.demo.dto.LikeListResponseDto;
import com.example.demo.entity.Like;
import com.example.demo.repository.LikeRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class LikeServiceImpl implements LikeService{
	
	@Autowired
	private LikeRepository likeRepository;
	
	@Override
	public List<LikeListResponseDto> getList(String memberId) {
		return likeRepository.findAllByMember_MemberId(memberId).stream()
				.map(LikeListResponseDto::toDto)
				.collect(Collectors.toList());
	}
	@Override
	public List<LikeListResponseDto> getList(String memberId, int count) {
		return likeRepository.findAllByMember_MemberId(memberId, Limit.of(count)).stream()
				.map(LikeListResponseDto::toDto)
				.collect(Collectors.toList());
	}
	@Override
	public List<LikeListResponseDto> getList(String memberId, String classification) {
		return likeRepository.findAllByMember_MemberIdAndClassification(memberId, classification).stream()
				.map(LikeListResponseDto::toDto)
				.collect(Collectors.toList());
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
