package com.example.demo.controller;

import com.example.demo.dto.MemberDTO;
import com.example.demo.service.MemberService;
import com.example.demo.service.provider.EmailProvider;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member/*")
public class MemberController {
	
	@Autowired
	private MemberService service;
	
	private final EmailProvider emailProvider;
	
	//아이디 중복확인
	@GetMapping("checkId")
	public ResponseEntity<String> checkId(@RequestParam String memberId) {
		System.out.println(memberId);
		if(service.checkId(memberId)) {
			return new ResponseEntity<String>("O",HttpStatus.OK);
		}
		else {
			return new ResponseEntity<String>("X",HttpStatus.OK);			
		}
	}
	
	
	String certificationNumber = "";
	
	// 인증번호 발송
	@PostMapping("sendMailCertificationNumber")
	public ResponseEntity<String> sendMailCertificationNumber(@RequestParam String email){
		System.out.println("Received email: " + email);
		certificationNumber = service.getCertificationNumber();
		System.out.println(certificationNumber);
//		boolean result = emailProvider.sendCertificationMail(email, certificationNumber);

        if (true) {
            return new ResponseEntity<String>("인증메일이 발송되었습니다.",HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("메일발송에 실패했습니다.",HttpStatus.OK);
        }
	}
	
	// 인증번호 확인
	@PostMapping("certificationNumberCheck")
	public ResponseEntity<String> certificationNumberCheck(@RequestParam String checkNumber){
		System.out.println("사용자입력번호 : " + checkNumber);
		System.out.println("인증번호 " + certificationNumber);
		if(checkNumber == certificationNumber ) {
			return new ResponseEntity<String>("O",HttpStatus.OK);
		}
		else {
			return new ResponseEntity<String>("X",HttpStatus.OK);			
		}
	}
	
	
	// 회원가입 Submit
	@PostMapping("join")
    public ResponseEntity<String> join(@RequestBody MemberDTO memberData, HttpServletResponse resp) {
        // 데이터 처리 로직
        System.out.println("MemberDTO: " + memberData);
        if(service.join(memberData)) {
        	Cookie cookie = new Cookie("joinid",memberData.getMemberId());
        	cookie.setPath("/");
        	cookie.setMaxAge(60);
        	resp.addCookie(cookie);
        	return new ResponseEntity<String>("O",HttpStatus.OK);
        }
        else {
        	return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        // 예: 비밀번호 유효성 검사 로직 추가 필요 시
//        if (memberDTO.getPassword() == null || memberDTO.getPassword().isEmpty()) {
//            return ResponseEntity.badRequest().body("Password cannot be empty.");
//        }
    }
	
	
	
	
	
//	@GetMapping("logout")
//	public ResponseEntity<String> logout(HttpServletRequest req) {
//		req.getSession().invalidate();
//		return new ResponseEntity<String>("O",HttpStatus.OK);
//	}
//    
//    
//    @GetMapping("login")
//    public ResponseEntity<String> login(String memberId, String password, HttpServletRequest req) {
//    	HttpSession session = req.getSession();
//    	if(service.login(memberId, password)) {
//    		session.setAttribute("loginMember", memberId);
//    		return new ResponseEntity<String>("O",HttpStatus.OK);
//    	}
//    	return new ResponseEntity<String>("X",HttpStatus.OK);
//    }
//    
//    
//    // 회원가입 후 로그인창에서 아이디 자동입력
//    @GetMapping("joinCheck")
//    public ResponseEntity<String> joinCheck(HttpServletRequest req) {
//    	// 혹시 방금 회원가입된 아이디가 있는지
//    	if(req.getHeader("Cookie") != null) {
//    		Cookie[] cookies = req.getCookies();
//    		for(Cookie cookie : cookies) {
//    			if(cookie.getName().equals("joinId")) {
//    				return new ResponseEntity<String>(cookie.getValue(),HttpStatus.OK);
//    			}
//    		}
//    	}
//    	return new ResponseEntity<String>("",HttpStatus.OK);
//    }
//    
//    
//    // 현재 로그인되어 있는지
//    @GetMapping("loginCheck")
//    public ResponseEntity<String> loginCheck(HttpServletRequest req) {
//    	Object temp = req.getSession().getAttribute("loginMember");
//    	// 혹시 로그인이 되어있다면
//    	if(temp != null) {
//    		return new ResponseEntity<String>((String)temp, HttpStatus.OK);
//    	}
//    	return new ResponseEntity<String>("",HttpStatus.OK);
//    }
    
}














