package com.galos.recengine.service;

import com.galos.recengine.model.SolrMovie;
import com.galos.recengine.model.UserMovieRecommendation;
import com.galos.recengine.repository.RecommendationRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecommenderService {

  @Autowired
  UserService userService;

  @Autowired
  RecommendationRepository recRepo;


  public List<SolrMovie> getRecommendations(String userQuery) {
    return null;
  }

  public List<UserMovieRecommendation> getRecommendedMovieIds(int userId, int noOfRec) {
	  return recRepo.findByUserid(userId).subList(0, noOfRec);	  
  }
}
