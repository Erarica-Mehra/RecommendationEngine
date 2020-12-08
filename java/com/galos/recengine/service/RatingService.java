package com.galos.recengine.service;

import com.galos.recengine.model.Rating;
import com.galos.recengine.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RatingService {

  @Autowired
  RatingRepository rateRepo;

  public Rating save(Rating newRating) {
    return rateRepo.save(newRating);
  }

  public Iterable<Rating> findRatings(int userId) {

//    return rateRepo.findAllById(userId);
    return null;
  }

}
