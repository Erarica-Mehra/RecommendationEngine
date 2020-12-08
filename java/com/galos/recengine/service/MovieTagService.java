package com.galos.recengine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galos.recengine.model.MovieTag;
import com.galos.recengine.repository.MovieTagRepository;

@Service
public class MovieTagService {
	
	@Autowired
	MovieTagRepository movieTagRepo;
	
	public MovieTag save(MovieTag tag) {
		return movieTagRepo.save(tag);
	}

}
