package com.crescendiatest.spring.datajpa.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "restaurant_reviews")
public class RestaurantReview {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "reviewer_name")
	private String reviewerName;
	
	@Column(name = "reviewer_address")
	private String reviewerAddress;
	
	@Column(name = "review", columnDefinition = "TEXT")
	private String review;	
	
	@Column(name = "reviewer_photo")
	private String reviewerPhoto;	
	
	@OneToMany(mappedBy = "restaurantReview", cascade = CascadeType.ALL,orphanRemoval = true)
	private List<ReviewerFaceDetect> reviewLikehoods = new ArrayList<>();
	
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Restaurant restaurant;

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the reviewerName
	 */
	public String getReviewerName() {
		return reviewerName;
	}

	/**
	 * @param reviewerName the reviewerName to set
	 */
	public void setReviewerName(String reviewerName) {
		this.reviewerName = reviewerName;
	}

	/**
	 * @return the reviewerAddress
	 */
	public String getReviewerAddress() {
		return reviewerAddress;
	}

	/**
	 * @param reviewerAddress the reviewerAddress to set
	 */
	public void setReviewerAddress(String reviewerAddress) {
		this.reviewerAddress = reviewerAddress;
	}

	/**
	 * @return the review
	 */
	public String getReview() {
		return review;
	}

	/**
	 * @param review the review to set
	 */
	public void setReview(String review) {
		this.review = review;
	}

	/**
	 * @return the reviewerPhoto
	 */
	public String getReviewerPhoto() {
		return reviewerPhoto;
	}

	/**
	 * @param reviewerPhoto the reviewerPhoto to set
	 */
	public void setReviewerPhoto(String reviewerPhoto) {
		this.reviewerPhoto = reviewerPhoto;
	}

	/**
	 * @return the reviewLikehoods
	 */
	public List<ReviewerFaceDetect> getReviewLikehoods() {
		return reviewLikehoods;
	}

	/**
	 * @param reviewLikehoods the reviewLikehoods to set
	 */
	public void setReviewLikehoods(List<ReviewerFaceDetect> reviewLikehoods) {
		this.reviewLikehoods = reviewLikehoods;
	}

	/**
	 * @return the restaurant
	 */
	public Restaurant getRestaurant() {
		return restaurant;
	}

	/**
	 * @param restaurant the restaurant to set
	 */
	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}
    
	
	
}
