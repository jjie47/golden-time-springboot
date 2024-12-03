package com.example.demo.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class ApiHeader {
	private int code;
	private String status;
	
	ApiHeader(int code, ResultStatus resultStatus) {
		this.code = code;
		this.status = resultStatus.getStatus();
	}
}
