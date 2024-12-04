package com.example.demo.controller;

import com.example.demo.dto.MemberDTO;
import com.example.demo.service.MemberService;
import com.example.demo.service.provider.EmailProvider;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member/*")
public class MemberController {
	
	@Autowired
    private HttpSession session;
	
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

	
	// 인증번호 발송
	@PostMapping("sendMailCertificationNumber")
	public ResponseEntity<String> sendMailCertificationNumber(@RequestParam String email){
		String certificationNumber = service.getCertificationNumber();
		session.setAttribute("certificationNumber", certificationNumber);

		System.out.println("인증번호: " + certificationNumber);
//		boolean result = emailProvider.sendCertificationMail(email, certificationNumber);

        if (true) {
            return new ResponseEntity<String>("인증메일이 발송되었습니다.",HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("메일발송에 실패했습니다.",HttpStatus.OK);
        }
	}
	
	
	// 인증번호 확인
	@PostMapping("certificationNumberCheck")
	public ResponseEntity<String> certificationNumberCheck(@RequestParam String memberMailNumber){
		String certificationNumber = (String) session.getAttribute("certificationNumber");
		
		System.out.println("사용자 입력번호: " + memberMailNumber);
		
		if(memberMailNumber != null && certificationNumber.equals(memberMailNumber)) {
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
        	Cookie cookie = new Cookie("joinId",memberData.getMemberId());
        	cookie.setPath("/");
        	cookie.setMaxAge(120);
        	resp.addCookie(cookie);
        	return new ResponseEntity<String>("O",HttpStatus.OK);
        }
        else {
        	return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	
	// 로그인 Submit
	@PostMapping("login")
	public ResponseEntity<String> login(@RequestBody Map<String, String> data, HttpServletRequest req) {
		String memberId = data.get("memberId");
	    String password = data.get("password");
	    HttpSession session = req.getSession();
	    if (service.login(memberId, password)) {
	        session.setAttribute("loginMember", memberId);
	        return new ResponseEntity<String>("O", HttpStatus.OK);
	    }
	    return new ResponseEntity<String>("X", HttpStatus.OK);
	}

	
	// 로그아웃
	@GetMapping("logout")
	public ResponseEntity<String> logout(HttpServletRequest req) {
		req.getSession().invalidate();
		return new ResponseEntity<String>("O",HttpStatus.OK);
	}
    
}














