package com.example.demo.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ResultStatus {
	SUCCESS("success"),
	FAIL("fail");
	
	private final String status;
}
