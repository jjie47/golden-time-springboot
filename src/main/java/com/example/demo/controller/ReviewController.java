package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.PharmListDTO;
import com.example.demo.domain.PharmReviewDTO;
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

	@PostMapping("/write")
	public ResponseEntity<String> writeReview(@RequestBody ReviewWriteDTO rv) {
		
		//임시로 설정해준 id 와 별점
		String memberId = "apple";
		
		//임시로 설정해준 id 와 별점 DTO에 넣어줌
		rv.setMemberId(memberId);
	    
	    
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
}
