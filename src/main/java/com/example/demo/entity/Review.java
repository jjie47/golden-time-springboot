package com.example.demo.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@Table(name="review")
@DynamicInsert
@ToString
public class Review {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long reviewId;
	
	@Lob
	@Column(nullable=false, length=1000)
	private String content;
	
	@Column
	private LocalDateTime createdAt;
	
	@Column
	private LocalDateTime updatedAt;
	
	@Column(nullable=false)
	private int rating;
	
	@Column(nullable=false, length=50)
	private String classification;
	
	@ManyToOne
	@JoinColumn(name="member_id", nullable=false)
	private Member member;
	
	@ManyToOne
	@JoinColumn(name="duty_id", nullable=false)
	private Duty duty;
	
	@PrePersist
	public void prePersist() {
//		this.createdAt = LocalDateTime.now();
		this.updatedAt = this.createdAt;
	}
	
//	@PreUpdate
//	public void preUpdate() {
//		this.updatedAt = LocalDateTime.now();
//	}
	
	public Review(String content, int rating, LocalDateTime createdAt, String classification, Member member, Duty duty) {
		this.content = content;
		this.rating = rating;
		this.createdAt = createdAt;
		this.classification = classification;
		this.member = member;
		this.duty = duty;
	}
	
}
