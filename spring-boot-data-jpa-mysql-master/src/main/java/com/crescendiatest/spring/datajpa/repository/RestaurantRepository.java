package com.crescendiatest.spring.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.crescendiatest.spring.datajpa.model.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
	boolean existsByRestaurantUrl(String restaurantUrl);
	Restaurant getByRestaurantUrl(String restaurantUrl);
}
