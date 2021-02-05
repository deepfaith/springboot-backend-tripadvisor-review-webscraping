package com.crescendiatest.spring.datajpa.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gcp.vision.CloudVisionTemplate;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crescendiatest.spring.datajpa.model.Restaurant;
import com.crescendiatest.spring.datajpa.model.RestaurantReview;
import com.crescendiatest.spring.datajpa.model.ReviewerFaceDetect;
import com.crescendiatest.spring.datajpa.repository.RestaurantRepository;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.FaceAnnotation;
import com.google.cloud.vision.v1.Feature;


/**
 * @author ADMIN
 *
 */
@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class RestaurantController {

	@Autowired
	RestaurantRepository restaurantRepository;
	
	@Autowired
	private CloudVisionTemplate cloudVisionTemplate;
	
	@Autowired
	private ResourceLoader resourceLoader;

	
	/**
	 * Saves restaurant. 
	 * Scrape the restaurant url page of tripadvisor and collect important object properties
	 * Using Google Vision API we select the joyLikeHood and sorrowLikeHood properties based on the uploaded image
	 * @param restaurant
	 * @return JSON object of restaurants and reviews or null
	 */
	@PostMapping("/restaurants")
	public ResponseEntity<Restaurant> createRestaurant(@RequestBody Restaurant restaurant) {
		try {
			
			Document doc = Jsoup.connect(restaurant.getRestaurantUrl()).get();
			
			//scraping restaurant name from the webpage
			String restaurantName = doc.select("h1").first().text();
			restaurant.setRestaurantName(restaurantName);
			
			//scraping review DOM element
			Elements reviewElements = doc.select("div.review-container");
			List<RestaurantReview> reviews = new ArrayList<>();
			for (Element element : reviewElements) {
				RestaurantReview review = new RestaurantReview();
				
				//getting review properties from the webpage and setting them to the review object
				String reviewContent = element.select("div.entry p.partial_entry").text();
				String reviewLoc = element.select("div.info_text div.userLoc strong").text();
				String reviewerName = element.select("div.info_text div").first().text();
				String reviewPhoto = element.select("div.ui_avatar img").attr("data-lazyurl");
				review.setReview(reviewContent);
				review.setReviewerAddress(reviewLoc);
				review.setReviewerName(reviewerName);
				review.setRestaurant(restaurant);
				review.setReviewerPhoto(reviewPhoto);
				
				//let's identify the image facedetect using google vision API
				List<ReviewerFaceDetect> reviewLikehoods = this.getFaceDetection(review);
				review.setReviewLikehoods(reviewLikehoods);
				
				reviews.add(review);
				
			}
			restaurant.setReviews(reviews);
			restaurant.setTotalReviews(reviews.size());
			
			//we'll look into database if restaurant record already exists then delete it.
			if( restaurantRepository.existsByRestaurantUrl(restaurant.getRestaurantUrl()) != false ) {
				Restaurant restaurantExists = restaurantRepository.getByRestaurantUrl(restaurant.getRestaurantUrl());	
				restaurantRepository.delete(restaurantExists);
			}	
			restaurant = restaurantRepository.save(restaurant);
			return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
			
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	/**
	 * Face Detection using google vision api to identify facial expressions like joy and sorrow. 
	 * If there is a facial expression then we will put it into List of objects
	 * @param review
	 * @return List<ReviewerFaceDetect> 
	 * @throws IOException
	 */
	private List<ReviewerFaceDetect> getFaceDetection(RestaurantReview review) throws IOException {

		List<ReviewerFaceDetect> reviewLikehoods = new ArrayList<>();
		//let's get our imagesource from url
		Resource imageResource = this.resourceLoader.getResource(review.getReviewerPhoto());
		AnnotateImageResponse response = this.cloudVisionTemplate.analyzeImage(
				imageResource, Feature.Type.FACE_DETECTION);
		
		boolean setLikehood = false;
		for (FaceAnnotation annotation : response.getFaceAnnotationsList()) {
			ReviewerFaceDetect reviewLikehood = new ReviewerFaceDetect();
			if( annotation.getSorrowLikelihoodValue() > 0 ) {
				reviewLikehood.setLikeHood("sorrow");
				reviewLikehood.setRating(annotation.getSorrowLikelihood().toString());
				setLikehood = true;
			}	
			if( annotation.getJoyLikelihoodValue() > 0 ) {
				reviewLikehood.setLikeHood("joy");
				reviewLikehood.setRating(annotation.getJoyLikelihood().toString());
				setLikehood = true;
			}
			//if we identify facial expressions for joy and sorrow we'll put it in the list
			if( setLikehood == true ) {
				reviewLikehood.setRestaurantReview(review);
				reviewLikehoods.add(reviewLikehood);
			}
		}
		return reviewLikehoods;
	}	
}
