package com.galos.recengine.service;

import com.galos.recengine.model.Movies;
import com.galos.recengine.repository.MovieRepository;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class MovieService {

  @Autowired
  MovieRepository movieRepository;

  public Movies save (Movies newMovie) {
    return movieRepository.save(newMovie);
  }

  public List<Movies> findAll() {
    return movieRepository.findAll();
  }

}
