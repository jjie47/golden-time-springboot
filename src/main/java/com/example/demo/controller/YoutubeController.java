package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.domain.PharmListDTO;
import com.example.demo.domain.YoutubeDTO;
import com.example.demo.service.YoutubeService;

@Controller
@RequestMapping("/api/youtube/*")
public class YoutubeController {
	
	@Autowired
	private YoutubeService service;
	
	@GetMapping("video")
	public ResponseEntity<List<YoutubeDTO>> getYoutube(
	        @RequestParam(defaultValue = "1") int page,  // 기본 페이지 번호 1
	        @RequestParam(defaultValue = "9") int size   // 기본 한 페이지에 9개
	    ) {
		List<YoutubeDTO> youdto = service.getYoutube(page,size);
		System.out.println(youdto);
//      System.out.println("Received HPIDs: " + hpid);
//		List<PharmListDTO> rcount = service.getPharmreviewcount(hpid);

//      System.out.println("리절트절트:" + rcount);

		return new ResponseEntity<>(youdto,HttpStatus.OK);
	}

}
