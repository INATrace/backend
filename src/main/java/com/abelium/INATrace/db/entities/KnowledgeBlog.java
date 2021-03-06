package com.abelium.INATrace.db.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.abelium.INATrace.api.types.Lengths;
import com.abelium.INATrace.db.base.TimestampEntity;
import com.abelium.INATrace.types.KnowledgeBlogType;

@Entity
@Table(indexes = { @Index(columnList = "type") })
public class KnowledgeBlog extends TimestampEntity {
	
	/**
	 * Product this part belongs to 
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	private Product product;
	
	/**
	 * product status
	 */
	@Enumerated(EnumType.STRING)
    @Column(nullable = false, length = Lengths.ENUM)
    private KnowledgeBlogType type;	
	
	/**
	 * Title
	 */
	@Column(length = Lengths.DEFAULT)
	private String title;
	
	/**
	 * Abstract
	 */
	@Lob
	private String summary;
	
	/**
	 * Content
	 */
	@Lob
	private String content;

	/**
	 * Date
	 */
	@Column
	private LocalDate date; 

	/**
	 * Youtube url
	 */
	@Column(length = Lengths.DEFAULT)
	private String youtubeUrl;
	
	/**
	 * Documents
	 */
	@ManyToMany(cascade = CascadeType.ALL)
	private List<Document> documents = new ArrayList<>();


	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	public KnowledgeBlogType getType() {
		return type;
	}

	public void setType(KnowledgeBlogType type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getYoutubeUrl() {
		return youtubeUrl;
	}

	public void setYoutubeUrl(String youtubeUrl) {
		this.youtubeUrl = youtubeUrl;
	}

	public List<Document> getDocuments() {
		return documents;
	}

	public void setDocuments(List<Document> documents) {
		this.documents = documents;
	}
}
