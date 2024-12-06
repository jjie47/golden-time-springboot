package com.example.demo.domain;

import lombok.Data;

@Data
public class MemberDTO {
	private String memberId;
    private String password;
    private String nickname;
    private String phoneNumber;
    private String email;
}
