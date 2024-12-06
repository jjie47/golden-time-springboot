package com.example.demo.domain;

import lombok.Data;

@Data
public class YoutubeDTO {
	private String youtubeId;
	private String title;
	private String reference;
	private int totalCount;
	private int totalPages;
}
