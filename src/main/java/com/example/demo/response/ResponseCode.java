package com.example.demo.response;

import org.springframework.http.HttpStatus;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ResponseCode {
	
	// 200 OK
	SESSION_GET_SUCCESS(HttpStatus.OK, ResultStatus.SUCCESS, "세션 정보 조회 성공"),
	MEMBER_GET_SUCCESS(HttpStatus.OK, ResultStatus.SUCCESS, "사용자 정보 조회 성공"),
	MEMBER_UPDATE_SUCCESS(HttpStatus.OK, ResultStatus.SUCCESS, "사용자 정보 수정 성공"),
	MEMBER_DELETE_SUCCESS(HttpStatus.OK, ResultStatus.SUCCESS, "사용자 정보 삭제 성공"),
	LIKES_INSERT_SUCCESS(HttpStatus.OK, ResultStatus.SUCCESS, "즐겨찾기 추가 성공"),
	LIKES_GET_SUCCESS(HttpStatus.OK, ResultStatus.SUCCESS, "즐겨찾기 목록 조회 성공"),
	LIKES_DELETE_SUCCESS(HttpStatus.OK, ResultStatus.SUCCESS, "즐겨찾기 삭제 성공"),

	REVIEWS_GET_SUCCESS(HttpStatus.OK, ResultStatus.SUCCESS, "리뷰 목록 조회 성공"),
	REVIEWS_UPDATE_SUCCESS(HttpStatus.OK, ResultStatus.SUCCESS, "리뷰 수정 성공"),
	REVIEWS_DELETE_SUCCESS(HttpStatus.OK, ResultStatus.SUCCESS, "리뷰 삭제 성공"),
	
	// 201 Created
	
	
	// 400 Bad Request
	BAD_REQUEST(HttpStatus.BAD_REQUEST, ResultStatus.FAIL, "잘못된 요청입니다."),
	
	// 401 Unauthorized
	UNAUTHORIZED(HttpStatus.UNAUTHORIZED, ResultStatus.FAIL, "로그인 정보가 존재하지 않습니다."),
	
	// 403 Forbidden
	FORBIDDEN(HttpStatus.FORBIDDEN, ResultStatus.FAIL, "해당 요청에 대한 접근 권한이 없습니다."),
	
	// 404 Not Found
	SESSION_NOT_FOUND(HttpStatus.NOT_FOUND, ResultStatus.FAIL, "세션 정보를 찾을 수 없습니다."),
	MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, ResultStatus.FAIL, "사용자 정보를 찾을 수 없습니다."),
	LIKES_NOT_FOUND(HttpStatus.NOT_FOUND, ResultStatus.FAIL, "즐겨찾기 정보를 찾을 수 없습니다."),

	REVIEWS_NOT_FOUND(HttpStatus.NOT_FOUND, ResultStatus.FAIL, "리뷰 정보를 찾을 수 없습니다."),
	
	// 500 Internal Server Error
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, ResultStatus.FAIL, "서버에 오류가 발생하였습니다.");
	
	private final HttpStatus httpStatus;
	private final ResultStatus result;
	private final String message;
	
	public int getHttpStatusCode() {
		return httpStatus.value();
	}
}
