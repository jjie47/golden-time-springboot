package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.MemberDTO;
//import com.example.demo.mapper.BoardMapper;
//import com.example.demo.mapper.FileMapper;
//import com.example.demo.mapper.ReplyMapper;
//import com.example.demo.mapper.UserMapper;
import com.example.demo.mapper.MemberMapper;

@Service
public class MemberService {
	
	@Autowired
	private MemberMapper mMapper;
//	@Autowired
//	private BoardMapper bmapper;
//	@Autowired
//	private ReplyMapper rmapper;
//	@Autowired
//	private FileMapper fmapper;
//
//

	public boolean login(String memberId, String password) {
		MemberDTO member = mMapper.getMemberByMemberId(memberId);
		if(member != null) {
			if(member.getPassword().equals(password)) {
				return true;
			}
		}
		return false;
	}

	
	// 아이디 중복 확인
	public boolean checkId(String memberId) {
		MemberDTO member = mMapper.getMemberByMemberId(memberId);
		return member == null;
	}
	
	
	// 랜덤 번호 생성
	public String getCertificationNumber() {
	    StringBuilder certificationNumber = new StringBuilder();

	    for (int count = 0; count < 6; count++) {
	        certificationNumber.append((int)(Math.random() * 10));
	    }
	    
	    return certificationNumber.toString();
	}
	
	
	public boolean join(MemberDTO member) {
		return mMapper.insertMember(member) == 1;
	}
	
	
	
//
//	@Override
//	public UserDTO getDetail(String userid) {
//		return umapper.getUserByUserid(userid);
//	}
//	
//	@Override
//	public boolean modify(UserDTO user) {
//		return umapper.updateUser(user) == 1;
//	}
//
//	@Override
//	public boolean leaveId(String userid) {
//		List<BoardDTO> list = bmapper.getListByUserid(userid);
//		for(BoardDTO board : list) {
//			rmapper.deleteRepliesByBoardnum(board.getBoardnum());
//			fmapper.deleteFilesByBoardnum(board.getBoardnum());
//		}
//		return umapper.deleteUser(userid) == 1;
//	}

}






