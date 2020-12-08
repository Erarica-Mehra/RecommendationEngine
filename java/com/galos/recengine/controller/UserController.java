package com.galos.recengine.controller;

import com.galos.recengine.model.MovieTag;
import com.galos.recengine.model.Rating;
import com.galos.recengine.service.MovieService;
import com.galos.recengine.service.MovieTagService;
import com.galos.recengine.service.RatingService;
import com.galos.recengine.service.UserService;
import java.sql.Timestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/users")

public class UserController {

  @Autowired
  UserService userService;

  @Autowired
  MovieService movieService;

  @Autowired
  RatingService ratingService;
  
  @Autowired
  MovieTagService movieTagService;


  @PostMapping(path="/addRating")
  public ResponseEntity<Rating> addNewRating (@RequestParam("userId") int userId, @RequestParam("movieId") int movieId, @RequestParam("rating") float rating) {
    Rating newRating = new Rating(userId, movieId, rating, new Timestamp(System.currentTimeMillis()));
    newRating = ratingService.save(newRating);
    return new ResponseEntity<Rating>(newRating, HttpStatus.OK);
  }

  @PostMapping(path="/addTags")
  public ResponseEntity<MovieTag> addNewTag (@RequestParam int userId, @RequestParam int movieId, @RequestParam String tag) {
	MovieTag newTag = new MovieTag(userId, movieId, tag, System.currentTimeMillis());
	newTag = movieTagService.save(newTag);
    return new ResponseEntity<MovieTag>(newTag, HttpStatus.OK);
  }

}
