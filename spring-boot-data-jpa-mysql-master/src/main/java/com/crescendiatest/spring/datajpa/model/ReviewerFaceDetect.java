package com.crescendiatest.spring.datajpa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "review_face_detects")
public class ReviewerFaceDetect {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "likehood")
	private String likeHood;
	
	@Column(name = "rating")
	private String rating;	
	
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private RestaurantReview restaurantReview;

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
	 * @return the likeHood
	 */
	public String getLikeHood() {
		return likeHood;
	}

	/**
	 * @param likeHood the likeHood to set
	 */
	public void setLikeHood(String likeHood) {
		this.likeHood = likeHood;
	}

	/**
	 * @return the rating
	 */
	public String getRating() {
		return rating;
	}

	/**
	 * @param rating the rating to set
	 */
	public void setRating(String rating) {
		this.rating = rating;
	}

	/**
	 * @return the restaurantReview
	 */
	public RestaurantReview getRestaurantReview() {
		return restaurantReview;
	}

	/**
	 * @param restaurantReview the restaurantReview to set
	 */
	public void setRestaurantReview(RestaurantReview restaurantReview) {
		this.restaurantReview = restaurantReview;
	}
    
	
}
