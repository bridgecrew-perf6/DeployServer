package edu.fsoft.spring.model;

import java.security.Timestamp;
import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "news")
public class News {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column
	private String title;
	
	@Column
	private String image;
	
	@Column
	private String shortContent;
	
	@Column
	private String content;
	
	@Column
	@CreatedDate
	private LocalDate createdDate;
	
	@Column
	@CreatedBy
	private String createdBy;
	
}