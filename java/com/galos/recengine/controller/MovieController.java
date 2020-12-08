package com.galos.recengine.controller;

import com.galos.recengine.model.Movies;
import com.galos.recengine.service.MovieService;
import com.galos.recengine.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/movies")
public class MovieController {

  @Autowired
  MovieService movieService;

  @Autowired
  RatingService ratingService;


  @PostMapping(path = "/save")
  public ResponseEntity<Movies> saveNewMovie (@RequestBody Movies movie) {
   Movies newMovie = movieService.save(movie);
    return new ResponseEntity<>(newMovie, HttpStatus.OK);
  }


  @GetMapping(path="/all")
  public @ResponseBody Iterable<Movies> getAllMovies(){
    return movieService.findAll();
  }
}
