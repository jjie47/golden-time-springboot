package com.example.demo.entity;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@Table(name="member")
@DynamicInsert
public class Member {
	
	@Id
	@Column(nullable=false, length=50)
	private String memberId;
	
	@Column(nullable=false, length=50)
	private String password;
	
	@Column(nullable=false, length=50)
	private String nickname;
	
	@Column(nullable=false, length=100)
	private String email;
	
	@Column(nullable=false, length=50)
	private String phoneNumber;
	
	@Column(length=3000)
	@ColumnDefault("'default_image'")
	private String systemName;
	
	@Column(length=3000)
	@ColumnDefault("'default_image'")
	private String originName;

	public Member(String memberId, String password, String nickname, String email, String phoneNumber) {
		this.memberId = memberId;
		this.password = password;
		this.nickname = nickname;
		this.email = email;
		this.phoneNumber = phoneNumber;
	}
}
