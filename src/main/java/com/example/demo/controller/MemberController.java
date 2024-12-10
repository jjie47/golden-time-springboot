package com.example.demo.controller;



import com.example.demo.service.MemberService;
import com.example.demo.service.MemberServiceImpl;
import com.example.demo.service.provider.EmailProvider;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.domain.MemberDTO;
import com.example.demo.dto.MemberInfoResponseDto;
import com.example.demo.dto.MemberProfileResponseDto;
import com.example.demo.dto.MemberUpdateRequestDto;
import com.example.demo.dto.ReviewItemDto;
import com.example.demo.dto.ReviewListResponseDto;
import com.example.demo.dto.ReviewUpdateRequestDto;
import com.example.demo.entity.Member;
import com.example.demo.response.ApiResponse;
import com.example.demo.response.ResponseCode;
import com.example.demo.service.MemberService;
import com.example.demo.service.MemberServiceImpl;
import com.example.demo.service.ReviewService;
import com.example.demo.service.provider.EmailProvider;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member/*")
public class MemberController {
	
	@Autowired
	private MemberServiceImpl mService;
	@Autowired
	private ReviewService rService;
	
	@Autowired
    private HttpSession session;
	
	@Autowired
	private MemberService service;
	
	private final EmailProvider emailProvider;
	
	//아이디 중복확인
	@GetMapping("checkId")
	public ResponseEntity<String> checkId(@RequestParam String memberId) {
//		System.out.println(memberId);
		if(service.checkId(memberId)) {
			return new ResponseEntity<String>("O",HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("X",HttpStatus.OK);			
		}
	}
	
	// 인증번호 발송
	@PostMapping("sendMailCertificationNumber")
	public ResponseEntity<String> sendMailCertificationNumber(@RequestParam String email){
		String certificationNumber = service.getCertificationNumber();
		session.setAttribute("certificationNumber", certificationNumber);

		System.out.println("메일: " + email);
		System.out.println("인증번호: " + certificationNumber);
		boolean result = emailProvider.sendCertificationMail(email, certificationNumber);

        if (result) {
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
		} else {
			return new ResponseEntity<String>("X",HttpStatus.OK);			
		}
	}
	
	// 회원가입
	@PostMapping("join")
    public ResponseEntity<String> join(@RequestBody MemberDTO memberData, HttpServletResponse resp) {
        // 데이터 처리 로직
//        System.out.println("MemberDTO: " + memberData);
        if(service.join(memberData)) {
        	Cookie cookie = new Cookie("joinId",memberData.getMemberId());
        	cookie.setPath("/");
        	cookie.setMaxAge(30);
        	resp.addCookie(cookie);
        	return new ResponseEntity<String>("O",HttpStatus.OK);
        } else {
        	return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	// 로그인
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
	
	// 아이디 찾기
	@PostMapping("help/IdInquiry")
	public ResponseEntity<String> idInquiry(@RequestBody MemberDTO memberData, HttpServletResponse resp) {
		System.out.println("memberData: " + memberData.getPhoneNumber());
		
		MemberDTO result = service.checkPhoneAndMail(memberData);
		if(result != null) {
			Cookie cookie = new Cookie("joinId",result.getMemberId());
        	cookie.setPath("/");
        	cookie.setMaxAge(30);
        	resp.addCookie(cookie);
			return new ResponseEntity<String>(result.getMemberId(),HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("X",HttpStatus.OK);
		}
	}

	// 비밀번호 찾기
	@PostMapping("help/PwInquiry")
	public ResponseEntity<String> pwInquiry(@RequestBody MemberDTO memberData) {
		System.out.println("memberData: " + memberData.getMemberId());
		System.out.println(service.checkId(memberData.getMemberId()));
		if(service.checkId(memberData.getMemberId())) {
			System.out.println("아이디 없음");
			return new ResponseEntity<String>("등록된 아이디가 없습니다.",HttpStatus.OK);
		}
		
		MemberDTO result = service.checkPhoneAndMail(memberData);
		if(result != null) {
			return new ResponseEntity<String>("O",HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("X",HttpStatus.OK);			
		}
	}
	
	// 비밀번호 변경
	@PostMapping("help/PwResetting")
	public ResponseEntity<String> PwResetting(@RequestBody MemberDTO memberData, HttpServletResponse resp) {
		System.out.println("memberData: " + memberData.getPhoneNumber());
		
		boolean result = service.updatePwByMemberId(memberData);
		if(result) {
			Cookie cookie = new Cookie("joinId",memberData.getMemberId());
        	cookie.setPath("/");
        	cookie.setMaxAge(30);
        	resp.addCookie(cookie);
			return new ResponseEntity<String>("O",HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("X",HttpStatus.OK);			
		}
	}
	
	

	
	@GetMapping("session")
	public ApiResponse<String> get(HttpServletRequest req) {
		String loginMember = (String)req.getSession().getAttribute("loginMember");
		if(loginMember!=null) {
			return ApiResponse.success(loginMember, ResponseCode.SESSION_GET_SUCCESS);		
		}
		return ApiResponse.fail(null, ResponseCode.SESSION_NOT_FOUND);	
	}
	
	@GetMapping("{memberId}")
	public ApiResponse<MemberInfoResponseDto> get(@PathVariable String memberId, HttpServletRequest req){
		String loginMember = (String)req.getSession().getAttribute("loginMember");
		if(loginMember==null) {
			return ApiResponse.fail(null, ResponseCode.UNAUTHORIZED);
		}
		if(!loginMember.equals(memberId)) {
			return ApiResponse.fail(null, ResponseCode.FORBIDDEN);
		}
		MemberInfoResponseDto memberInfo = mService.getInfo(memberId);
		if(memberInfo!=null) {
			return ApiResponse.success(memberInfo, ResponseCode.MEMBER_GET_SUCCESS);		
		}
		return ApiResponse.fail(null, ResponseCode.MEMBER_NOT_FOUND);
	}
	
	@GetMapping("{memberId}/profile")
	public ApiResponse<MemberProfileResponseDto> getProfile(@PathVariable String memberId, HttpServletRequest req){
		String loginMember = (String)req.getSession().getAttribute("loginMember");
		if(loginMember==null) {
			return ApiResponse.fail(null, ResponseCode.UNAUTHORIZED);
		}
		if(!loginMember.equals(memberId)) {
			return ApiResponse.fail(null, ResponseCode.FORBIDDEN);
		}
		MemberProfileResponseDto memberInfo = mService.getProfile(memberId);
		if(memberInfo!=null) {
			return ApiResponse.success(memberInfo, ResponseCode.MEMBER_GET_SUCCESS);		
		}
		return ApiResponse.fail(null, ResponseCode.MEMBER_NOT_FOUND);
	}
	
	@GetMapping("{memberId}/profile-image/{systemName}")
	public ResponseEntity<Resource> getProfile(@PathVariable String memberId,
															@PathVariable String systemName,
															HttpServletRequest req) throws Exception{
		String loginMember = (String)req.getSession().getAttribute("loginMember");
		if(loginMember==null) {
			return new ResponseEntity<Resource>(null, null, HttpStatus.UNAUTHORIZED);
		}
		if(!loginMember.equals(memberId)) {
			return new ResponseEntity<Resource>(null, null, HttpStatus.FORBIDDEN);
		}
		HashMap<String, Object> datas = mService.getProfileImage(systemName);
		String contentType = (String)datas.get("contentType");
		Resource resource = (Resource)datas.get("resource");
		
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, contentType);		
		return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
	}
	
	@PutMapping("{memberId}")
	public ApiResponse<String> modify(@PathVariable String memberId,
										MemberUpdateRequestDto member,
										MultipartFile file,
										String deleteFile,
										HttpServletRequest req) throws Exception{
		String loginMember = (String)req.getSession().getAttribute("loginMember");
		if(loginMember==null) {
			return ApiResponse.fail(null, ResponseCode.UNAUTHORIZED);
		}
		if(!loginMember.equals(memberId)) {
			return ApiResponse.fail(null, ResponseCode.FORBIDDEN);
		}
		System.out.println(file);
		System.out.println(deleteFile);
		member.setMemberId(memberId);
		if(mService.update(member, file, deleteFile)) {
			return ApiResponse.success("success", ResponseCode.MEMBER_UPDATE_SUCCESS);
		}
		return ApiResponse.fail("fail", ResponseCode.INTERNAL_SERVER_ERROR);
	}
	
	@DeleteMapping("{memberId}")
	public ApiResponse<String> remove(@PathVariable String memberId, HttpServletRequest req){
		HttpSession session = req.getSession();
		String loginMember = (String)session.getAttribute("loginMember");
		if(loginMember==null) {
			return ApiResponse.fail(null, ResponseCode.UNAUTHORIZED);
		}
		if(!loginMember.equals(memberId)) {
			return ApiResponse.fail(null, ResponseCode.FORBIDDEN);
		}
		if(mService.delete(memberId)) {
			session.removeAttribute("loginMember");
			return ApiResponse.success("success", ResponseCode.MEMBER_DELETE_SUCCESS);
		}
		return ApiResponse.fail("fail", ResponseCode.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping("{memberId}/reviews")
	public ApiResponse<ReviewListResponseDto> getList(@PathVariable String memberId,
													@RequestParam int pageNo,
													@RequestParam int numOfRows,
													HttpServletRequest req) {
		String loginMember = (String)req.getSession().getAttribute("loginMember");
		if(loginMember==null) {
			return ApiResponse.fail(null, ResponseCode.UNAUTHORIZED);
		}
		if(!loginMember.equals(memberId)) {
			return ApiResponse.fail(null, ResponseCode.FORBIDDEN);
		}
		ReviewListResponseDto list = rService.getList(loginMember, pageNo, numOfRows);
		if(list!=null) {
			return ApiResponse.success(list, ResponseCode.REVIEWS_GET_SUCCESS);
		}
		return ApiResponse.fail(list, ResponseCode.REVIEWS_NOT_FOUND);
	}
	
	@GetMapping("{memberId}/reviews/month")
	public ApiResponse<List<ReviewItemDto>> getList(@PathVariable String memberId,
													@RequestParam int month,
													HttpServletRequest req) {
		String loginMember = (String)req.getSession().getAttribute("loginMember");
		if(loginMember==null) {
			return ApiResponse.fail(null, ResponseCode.UNAUTHORIZED);
		}
		if(!loginMember.equals(memberId)) {
			return ApiResponse.fail(null, ResponseCode.FORBIDDEN);
		}
		List<ReviewItemDto> list = rService.getList(loginMember, month);
		if(list!=null) {
			return ApiResponse.success(list, ResponseCode.REVIEWS_GET_SUCCESS);
		}
		return ApiResponse.fail(list, ResponseCode.REVIEWS_NOT_FOUND);
	}
	
	@GetMapping("{memberId}/reviews/months")
	public ApiResponse<ReviewListResponseDto> getList(@PathVariable String memberId,
													@RequestParam int month,
													@RequestParam int pageNo,
													@RequestParam int numOfRows,
													HttpServletRequest req) {
		String loginMember = (String)req.getSession().getAttribute("loginMember");
		if(loginMember==null) {
			return ApiResponse.fail(null, ResponseCode.UNAUTHORIZED);
		}
		if(!loginMember.equals(memberId)) {
			return ApiResponse.fail(null, ResponseCode.FORBIDDEN);
		}
		ReviewListResponseDto list = rService.getList(loginMember, month, pageNo, numOfRows);
		if(list!=null) {
			return ApiResponse.success(list, ResponseCode.REVIEWS_GET_SUCCESS);
		}
		return ApiResponse.fail(list, ResponseCode.REVIEWS_NOT_FOUND);
	}
	
	@GetMapping("{memberId}/reviews/classification")
	public ApiResponse<ReviewListResponseDto> getList(@PathVariable String memberId,
													@RequestParam String classification,
													@RequestParam int pageNo,
													@RequestParam int numOfRows,
													HttpServletRequest req) {
		String loginMember = (String)req.getSession().getAttribute("loginMember");
		if(loginMember==null) {
			return ApiResponse.fail(null, ResponseCode.UNAUTHORIZED);
		}
		if(!loginMember.equals(memberId)) {
			return ApiResponse.fail(null, ResponseCode.FORBIDDEN);
		}
		ReviewListResponseDto list = rService.getList(loginMember, classification, pageNo, numOfRows);
		if(list!=null) {
			return ApiResponse.success(list, ResponseCode.REVIEWS_GET_SUCCESS);
		}
		return ApiResponse.fail(list, ResponseCode.REVIEWS_NOT_FOUND);
	}
	
	@GetMapping("{memberId}/reviews/conditions")
	public ApiResponse<ReviewListResponseDto> getList(@PathVariable String memberId,
													@RequestParam int month,
													@RequestParam String classification,
													@RequestParam int pageNo,
													@RequestParam int numOfRows,
													HttpServletRequest req) {
		String loginMember = (String)req.getSession().getAttribute("loginMember");
		if(loginMember==null) {
			return ApiResponse.fail(null, ResponseCode.UNAUTHORIZED);
		}
		if(!loginMember.equals(memberId)) {
			return ApiResponse.fail(null, ResponseCode.FORBIDDEN);
		}
		ReviewListResponseDto list = rService.getList(loginMember, month, classification, pageNo, numOfRows);
		if(list!=null) {
			return ApiResponse.success(list, ResponseCode.REVIEWS_GET_SUCCESS);
		}
		return ApiResponse.fail(list, ResponseCode.REVIEWS_NOT_FOUND);
	}
	
	@PutMapping("{memberId}/review/{reviewId}")
	public ApiResponse<String> modify(@PathVariable String memberId, @PathVariable long reviewId, @RequestBody ReviewUpdateRequestDto review, HttpServletRequest req){
		String loginMember = (String)req.getSession().getAttribute("loginMember");
		if(loginMember==null) {
			return ApiResponse.fail(null, ResponseCode.UNAUTHORIZED);
		}
		if(!loginMember.equals(memberId)) {
			return ApiResponse.fail(null, ResponseCode.FORBIDDEN);
		}
		review.setReviewId(reviewId);
		if(rService.update(review)) {
			return ApiResponse.success("success", ResponseCode.REVIEWS_UPDATE_SUCCESS);
		}
		return ApiResponse.fail("fail", ResponseCode.INTERNAL_SERVER_ERROR);
	}
	
	@DeleteMapping("{memberId}/review/{reviewId}")
	public ApiResponse<String> delete(@PathVariable String memberId,
											@PathVariable long reviewId,
											HttpServletRequest req) {
		String loginMember = (String)req.getSession().getAttribute("loginMember");
		if(loginMember==null) {
			return ApiResponse.fail(null, ResponseCode.UNAUTHORIZED);
		}
		if(!loginMember.equals(memberId)) {
			return ApiResponse.fail(null, ResponseCode.FORBIDDEN);
		}
		if(rService.delete(reviewId)) {
			return ApiResponse.success("success", ResponseCode.REVIEWS_DELETE_SUCCESS);
		}
		return ApiResponse.fail("fail", ResponseCode.INTERNAL_SERVER_ERROR);
	}
	
}
