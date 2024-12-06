package com.example.demo.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponse<T> {
	private ApiHeader header;
	private T data;
	private String message;
	
	private static <T> ApiResponse<T> createResponse(T data, ResponseCode responseCode) {
		return new ApiResponse<T>(new ApiHeader(responseCode.getHttpStatusCode(), responseCode.getResult()), data, responseCode.getMessage());
	}
	
	public static <T> ApiResponse<T> success(T data, ResponseCode responseCode) {
		return createResponse(data, responseCode);
	}
	
	public static <T> ApiResponse<T> fail(T data, ResponseCode responseCode) {
		return createResponse(data, responseCode);
	}
}
