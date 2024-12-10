package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.MemberDTO;

import com.example.demo.domain.PharmLikeDTO;
import com.example.demo.domain.PharmListDTO;
import com.example.demo.domain.PharmReviewDTO;
import com.example.demo.domain.ReviewDTO;
import com.example.demo.domain.ReviewWriteDTO;
import com.example.demo.service.ReviewService;


@RestController
@RequestMapping("/api/review/*")
public class ReviewController {

	@Autowired
	private ReviewService service;

	@GetMapping("pharmlist")
	public ResponseEntity<List<PharmListDTO>> getPharmreviewcount(@RequestParam List<String> hpid) {
//        System.out.println("Received HPIDs: " + hpid);
		List<PharmListDTO> rcount = service.getPharmreviewcount(hpid);

//        System.out.println("리절트절트:" + rcount);

		return new ResponseEntity<>(rcount, HttpStatus.OK);
	}

	@GetMapping("pharmreview")
	public ResponseEntity<List<PharmReviewDTO>> getPharmReview(@RequestParam String hpid) {
//        System.out.println("react로 부터 받은 값: " + hpid);

		List<PharmReviewDTO> reviews = service.getPharmReview(hpid);
//        System.out.println("컨트롤러 답변: "+reviews);

		return new ResponseEntity<>(reviews, HttpStatus.OK);
	}
	
	@GetMapping("pharmlike")
	public ResponseEntity<List<Integer>> getPharmlike(@RequestParam List<String> hpid,@RequestParam String memberId) {
//        System.out.println("react로 부터 받은 값: " + hpid);
//        System.out.println("react로 부터 받은 아이디: " + memberId);


        List<Integer> result = service.getPharmlike(hpid,memberId);
        
//        System.out.println("난 컨트롤러:" +result);

		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("pharmlikeone")
	public ResponseEntity<Integer> getPharmlike(@RequestParam String hpid,@RequestParam String memberId) {
//        System.out.println("react로 부터 받은 값 단일: " + hpid);
//        System.out.println("react로 부터 받은 아이디 단일: " + memberId);

        int result = service.getPharmlikeone(hpid,memberId);        

		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PostMapping("likeadd")
	public ResponseEntity<String> likeadd(@RequestBody ReviewWriteDTO rv) {
		
	    //약국 값은 기본으로 넣어줌, 병원의 경우 외과 안과 이런거 받아서 넣어야함
	    String classification = "약국";
	    rv.setClassification(classification);
	    
	    System.out.println(rv);
	    //로직
	    service.likeadd(rv);
//        System.out.println("Received hpid: " + rv.getDutyId());
//        System.out.println("Received review: " + rv.getContent());
//        System.out.println("Received name: " + rv.getDutyName());
//        System.out.println("Received call: " + rv.getDutyTel());

		return ResponseEntity.ok("리뷰 등록 성공");
	}

	@PostMapping("write")
	public ResponseEntity<String> writeReview(@RequestBody ReviewWriteDTO rv) {
		

	    //약국 값은 기본으로 넣어줌, 병원의 경우 외과 안과 이런거 받아서 넣어야함
	    String classification = "약국";
	    rv.setClassification(classification);
	    
	    //로직
	    service.writeReview(rv);
//        System.out.println("Received hpid: " + rv.getDutyId());
//        System.out.println("Received review: " + rv.getContent());
//        System.out.println("Received name: " + rv.getDutyName());
//        System.out.println("Received call: " + rv.getDutyTel());

		return ResponseEntity.ok("리뷰 등록 성공");
	}
	

	@GetMapping("getnickname")
	public ResponseEntity<String> getNickName(@RequestParam String memberId) {      

		String nick = service.getNickName(memberId);

		return new ResponseEntity<>(nick, HttpStatus.OK);
	}

	
	
	// 병원 리뷰 등록
	@PostMapping("writeHospitalReview")
	public ResponseEntity<?> writeHospitalReview(@RequestBody Map<String, Object> sendData) {
		boolean result = service.writeHospitalReview(sendData);
		
		if(result) {
        	return new ResponseEntity<String>("O",HttpStatus.OK);
        } else {
        	return new ResponseEntity<String>("X",HttpStatus.OK);
        }
	}
	
	// 병원ID에 해당하는 리뷰 목록 조회
	@GetMapping("{hpid}/reviews")
    public ResponseEntity<Map<String, Object>> getReviewsByDutyId(@PathVariable String hpid) {

        List<ReviewDTO> reviews = service.getReviewsByDutyId(hpid);
        // 리뷰 개수
        int reviewCount = reviews.size();
        
        // 평균 평점 계산 (리뷰가 없으면 0)
        double averageRating = reviews.stream().mapToInt(ReviewDTO::getRating).average().orElse(0.0);
        
        // 평점별 개수 카운트
        Map<Integer, Long> ratingCount = reviews.stream()
            .collect(Collectors.groupingBy(ReviewDTO::getRating, Collectors.counting()));

        // 평점 1~5까지의 개수를 계산
        Map<Integer, Long> ratingCounts = new HashMap<>();
        for (int i = 1; i <= 5; i++) {
            ratingCounts.put(i, ratingCount.getOrDefault(i, 0L));
        }
        
        Map<String, Object> response = new HashMap<>();
        response.put("reviews", reviews);
        response.put("rating", averageRating);
        response.put("reviewCount", reviewCount);
        response.put("ratingCounts", ratingCounts);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
	
	
	
	
	

}
