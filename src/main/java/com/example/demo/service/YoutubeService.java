package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.domain.YoutubeDTO;

@Service
public interface YoutubeService {

	List<YoutubeDTO> getYoutube(int page, int size);

}
