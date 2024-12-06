package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.PharmReviewDTO;
import com.example.demo.domain.YoutubeDTO;
import com.example.demo.mapper.YoutubeMapper;

@Service
public class YoutubeServiceImpl implements YoutubeService{
	
	@Autowired
	private YoutubeMapper youtubeMapper;

	@Override
	public List<YoutubeDTO> getYoutube(int page, int size) {
		int offset = (page - 1) * size;
		List<YoutubeDTO> result = youtubeMapper.getYoutube(offset, size);
		
		for (YoutubeDTO youtube : result) {
	        // 리뷰의 memberid를 통해 닉네임 가져오기
	        int total = youtubeMapper.getTotal();
	        int totalPages = (int) Math.ceil((double) total / size);
	        // 가져온 닉네임을 PharmReviewDTO에 세팅
	        youtube.setTotalCount(total);
	        youtube.setTotalPages(totalPages);
	    }
		
		System.out.println(result);
		
		return result;
	}

}
