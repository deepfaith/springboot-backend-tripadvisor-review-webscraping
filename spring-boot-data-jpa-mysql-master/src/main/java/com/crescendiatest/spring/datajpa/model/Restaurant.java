package com.crescendiatest.spring.datajpa.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "restaurants")
public class Restaurant {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "restauranturl")
	private String restaurantUrl;

	@Column(name = "restaurantname")
	private String restaurantName;
	
	@Column(name = "totalreviews")
	private long totalReviews;
	
	@OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL,orphanRemoval = true)
	private List<RestaurantReview> reviews = new ArrayList<>();
	
	
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
	 * @return the restaurantUrl
	 */
	public String getRestaurantUrl() {
		return restaurantUrl;
	}



	/**
	 * @param restaurantUrl the restaurantUrl to set
	 */
	public void setRestaurantUrl(String restaurantUrl) {
		this.restaurantUrl = restaurantUrl;
	}



	/**
	 * @return the restaurantName
	 */
	public String getRestaurantName() {
		return restaurantName;
	}



	/**
	 * @param restaurantName the restaurantName to set
	 */
	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}



	/**
	 * @return the totalReviews
	 */
	public long getTotalReviews() {
		return totalReviews;
	}



	/**
	 * @param totalReviews the totalReviews to set
	 */
	public void setTotalReviews(long totalReviews) {
		this.totalReviews = totalReviews;
	}



	/**
	 * @return the reviews
	 */
	public List<RestaurantReview> getReviews() {
		return reviews;
	}



	/**
	 * @param reviews the reviews to set
	 */
	public void setReviews(List<RestaurantReview> reviews) {
		this.reviews = reviews;
	}


	@Override
	public String toString() {
		return "Restaurant [id=" + id + ", restaurantName=" + restaurantName + ", restaurantUrl=" + restaurantUrl + ", restaurantUrl=" + restaurantUrl + ", totalReviews=" + totalReviews + "]";
	}

}
