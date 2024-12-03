package com.example.demo.dto;

import lombok.Data;

@Data
public class MemberUpdateRequestDto {
	private String memberId;
	private String password;
	private String nickname;
	private String email;
	private String phoneNumber;
}
