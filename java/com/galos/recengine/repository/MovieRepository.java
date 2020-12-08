package com.galos.recengine.repository;

import com.galos.recengine.model.Movies;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movies, Serializable> {

  void delete(Movies deleted);

  //List<Movie> findAll();

  //Movie save(Movie persisted);

}
