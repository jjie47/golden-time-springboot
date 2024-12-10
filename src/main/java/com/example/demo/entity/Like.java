package com.example.demo.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@Table(name="`like`")
@DynamicInsert
@ToString
public class Like {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long likeId;
	
	@Column(nullable=false, length=50)
	private String classification;
	
	@ManyToOne
	@JoinColumn(name="member_id", nullable=false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Member member;
	
	@ManyToOne
	@JoinColumn(name="duty_id", nullable=false)
	private Duty duty;
	
	public Like(String classification, Member member, Duty duty) {
		this.classification = classification;
		this.member = member;
		this.duty = duty;
	}
}
