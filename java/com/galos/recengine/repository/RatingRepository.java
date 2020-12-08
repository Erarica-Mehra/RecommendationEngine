package com.galos.recengine.repository;

import com.galos.recengine.model.Rating;
import java.io.Serializable;
import org.springframework.data.repository.CrudRepository;

public interface RatingRepository extends CrudRepository<Rating, Serializable> {

  Rating save(Rating newRating);

}
