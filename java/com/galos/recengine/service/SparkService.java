package com.galos.recengine.service;


import com.galos.recengine.repository.SparkRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SparkService {

  @Autowired
  SparkRepo sparkRepo;

  public void reRunRecommendations() {
    sparkRepo.reRunRecommendations();
  }
}
