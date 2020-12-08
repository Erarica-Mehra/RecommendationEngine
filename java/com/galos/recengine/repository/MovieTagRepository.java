package com.galos.recengine.repository;

import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.galos.recengine.model.MovieTag;

@Repository
public interface MovieTagRepository extends JpaRepository<MovieTag, Serializable>{


}
