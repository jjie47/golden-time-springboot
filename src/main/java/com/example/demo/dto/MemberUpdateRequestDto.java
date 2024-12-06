package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberUpdateRequestDto {
	private String memberId;
	private String password;
	private String nickname;
	private String email;
	private String phoneNumber;
}
