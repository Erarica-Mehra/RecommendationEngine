package com.galos.recengine.controller;

import com.galos.recengine.model.SolrMovie;
import com.galos.recengine.repository.SparkRepo;
import com.galos.recengine.service.SolrMovieService;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;

import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/search")
public class SearchController {

  @Autowired
  SolrMovieService solrMovieService;

  @Autowired
  SparkRepo sparkRepo;


  @ResponseBody
  @RequestMapping(value = "/autocomplete", produces = "application/json")
  public Set<String> autoComplete(Model model, @RequestParam("term") String query) {
    if (!StringUtils.hasText(query)) {
      return Collections.emptySet();
    }
      Set<String> titles = solrMovieService.autocompleteNameFragment(query);
     return titles;
  }

  @RequestMapping("/movie/{name}")
  public @ResponseBody
  ResponseEntity<List<SolrMovie>> search(Model model, @PathVariable("name") String name,
      HttpServletRequest request) {
    List<SolrMovie> movies = solrMovieService.findMovies(name);
    return new ResponseEntity<List<SolrMovie>>(movies, HttpStatus.OK);
  }

  @ResponseBody
  @RequestMapping("/topGenres")
  public Set<String> getFacetsOnGenres(@PageableDefault(page = 0, size = 1) Pageable pageable) {
	  Set<String> genres = solrMovieService.getFacetsOnGenres(pageable);
	  return genres;
  }

  @RequestMapping("/getRecommendations/{userId}")
  public List<SolrMovie> getFacetsOnGenres(@PathVariable("userId") int userId) {
    return solrMovieService.getRecommendationsForThisUser(userId, 5);
  }


  @RequestMapping("/runRecommendations")
  public String runRecommendations() {
    sparkRepo.reRunRecommendations();
    return "OK";
  }


}
