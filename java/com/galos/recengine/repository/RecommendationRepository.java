package com.galos.recengine.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.galos.recengine.model.UserMovieRecommendation;

@Repository
public interface RecommendationRepository {

	List<UserMovieRecommendation> findByUserid(int userId);
  
}
