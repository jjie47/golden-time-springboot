package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.dto.LikeAddRequestDto;
import com.example.demo.dto.LikeItemDto;
import com.example.demo.dto.LikeListRequestDto;
import com.example.demo.dto.LikeListResponseDto;
import com.example.demo.entity.Duty;
import com.example.demo.entity.Like;
import com.example.demo.repository.DutyRepository;
import com.example.demo.repository.LikeRepository;
import com.example.demo.repository.MemberRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

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
	public long insert(LikeAddRequestDto likeReq) {
		// Like  확인
		Optional<Like> checked = likeRepository.findByMember_MemberIdAndDuty_DutyId(likeReq.getMemberId(), likeReq.getDuty().getDutyId());
		if(checked.isPresent()) {
			return checked.get().getLikeId();
		}
		// Duty 확인
		Duty reqDuty = likeReq.getDuty();
		Optional<Duty> dutyData = dutyRepository.findById(reqDuty.getDutyId());
		if(dutyData.isEmpty()) {
			Duty newDuty = dutyRepository.save(new Duty(reqDuty.getDutyId(), reqDuty.getDutyName(), reqDuty.getDutyDiv(), reqDuty.getDutyTel()));
			dutyData = Optional.of(newDuty);
		}
		Like result = likeRepository.save(new Like(likeReq.getClassification(), memberRepository.findByMemberId(likeReq.getMemberId()).get(), dutyData.get()));
		return result.getLikeId();
	}
	
	@Override
	public long getId(String memberId, String dutyId) {
		Optional<Like> opt = likeRepository.findByMember_MemberIdAndDuty_DutyId(memberId, dutyId);
		if(opt.isPresent()) return opt.get().getLikeId();
		return -1;
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
