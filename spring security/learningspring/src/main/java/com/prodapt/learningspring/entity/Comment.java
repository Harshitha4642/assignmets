package com.prodapt.learningspring.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Entity
@Data

public class Comment {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int commentId;
	
	private int postId;
	private int commentorId;
	private String content;
	/*
	@Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date timestamp;
    */

}
