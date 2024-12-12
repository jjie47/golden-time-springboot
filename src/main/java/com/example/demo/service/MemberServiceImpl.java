package com.example.demo.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
public class MemberServiceImpl{
	
	@Value("${FILE_DIR}")
	private String saveFolder;
	
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private ReviewRepository reviewRepository;
	@Autowired
	private LikeRepository likeRepository; 
	
	@PersistenceContext
    private EntityManager em;
	
	public MemberInfoResponseDto getInfo(String memberId) {
		Optional<Member> data = memberRepository.findByMemberId(memberId);
		if(data.isEmpty()) {
			// TODO: 예외 처리
			return null;
		}
		return MemberInfoResponseDto.toDto(data.get());
	}
	
	public MemberProfileResponseDto getProfile(String memberId) {
		Optional<Member> data = memberRepository.findByMemberId(memberId);
		long reviewCnt = reviewRepository.countByMember_MemberId(memberId);
		long likeCnt = likeRepository.countByMember_MemberId(memberId);
		if(data.isEmpty()) {
			// TODO: 예외 처리
			return null;
		}
		return MemberProfileResponseDto.toDto(data.get(), reviewCnt, likeCnt);
	}
	
	public HashMap<String, Object> getProfileImage(String systemName) throws Exception{
		Path path = Paths.get(saveFolder+systemName);
		String contentType = Files.probeContentType(path);
		Resource resource = new InputStreamResource(Files.newInputStream(path));
		
		HashMap<String, Object> datas = new HashMap<>();
		datas.put("contentType", contentType);
		datas.put("resource", resource);
		return datas;
	}
	
	public HashMap<String, Object> getMemberImage(String memberId) throws Exception{
		Member member = memberRepository.findByMemberId(memberId).get();
		String systemName = member.getSystemName();
		Path path = Paths.get(saveFolder+systemName);
		String contentType = Files.probeContentType(path);
		Resource resource = new InputStreamResource(Files.newInputStream(path));
		
		HashMap<String, Object> datas = new HashMap<>();
		datas.put("contentType", contentType);
		datas.put("resource", resource);
		return datas;
	}
	
	public boolean update(MemberUpdateRequestDto member, MultipartFile file, String deleteFile) throws Exception{
		Member data = em.find(Member.class, member.getMemberId());
		if(data==null) {
			return false;
		}
		if(file != null) {
			String orgName = file.getOriginalFilename();
			if(orgName!=data.getOriginName()) {
				int lastIdx = orgName.lastIndexOf(".");
				String ext = orgName.substring(lastIdx);
				LocalDateTime now = LocalDateTime.now();
				String time = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
				String systemName = time+UUID.randomUUID().toString()+ext;
				String path = saveFolder+systemName;
				data.setSystemName(systemName);
				data.setOriginName(orgName);
				file.transferTo(new File(path));
			}
		}
		if(deleteFile != null && !deleteFile.equals("default_image.png")) {
			File delFile = new File(saveFolder, deleteFile);
			if(delFile.exists()) {
				delFile.delete();
			}
			if(file == null) {
				data.setSystemName("default_image.png");
				data.setOriginName("default_image");
			}
		}
		data.setPassword(member.getPassword());
		data.setNickname(member.getNickname());
		data.setEmail(member.getEmail());
		data.setPhoneNumber(member.getPhoneNumber());
		return true;	
	}
	
	public boolean delete(String memberId) {
		Optional<Member> data = memberRepository.findByMemberId(memberId);
		if(data.isPresent()) {
			String deleteFile = data.get().getSystemName();
			if(!deleteFile.equals("default_image.png")) {
				File delFile = new File(saveFolder, deleteFile);
				if(delFile.exists()) {
					delFile.delete();
				}
			}
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
