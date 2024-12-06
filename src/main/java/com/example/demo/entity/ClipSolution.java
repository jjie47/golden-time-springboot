package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name="clip_solution")
public class ClipSolution {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long clipId;
	
	@Column(nullable=false, length=300)
	private String youtubeId;
	
	@Column(nullable=false, length=300)
	private String title;
	
	@Column(nullable=false, length=300)
	private String reference;
	
	public ClipSolution(String youtubeId, String title, String reference) {
		this.youtubeId = youtubeId;
		this.title = title;
		this.reference = reference;
	}
}
