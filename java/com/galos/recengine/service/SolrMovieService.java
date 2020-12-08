package com.galos.recengine.service;

import com.galos.recengine.daoImpl.SolrDAOImpl;
import com.galos.recengine.model.SolrMovie;
import com.galos.recengine.model.UserMovieRecommendation;
import com.galos.recengine.repository.SolrMovieRepo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SolrMovieService {

  private static final Pattern IGNORED_CHARS_PATTERN = Pattern.compile("\\p{Punct}");

  @Autowired
  SolrMovieRepo solrMovieRepo;

  @Autowired
  SolrDAOImpl solrDaoImpl;

  @Autowired
  RecommenderService recommenderService;

  public Page<SolrMovie> findByName(String searchTerm, Pageable pageable) {

//    return solrMovieRepo.findByTitleIn(splitSearchTermAndRemoveIgnoredCharacters(searchTerm), pageable);
    return null;
  }


  public Set<String> autocompleteNameFragment(String fragment) {

    if (StringUtils.isBlank(fragment)) {
      return Collections.emptySet();
    }
    return solrMovieRepo.findByTitleStartsWith(fragment);
  }

  private Collection<String> splitSearchTermAndRemoveIgnoredCharacters(String searchTerm) {
    String[] searchTerms = StringUtils.split(searchTerm, " ");
    return Arrays.stream(searchTerms).filter(StringUtils::isNotEmpty)
        .map(term -> IGNORED_CHARS_PATTERN.matcher(term).replaceAll(" "))
        .collect(Collectors.toCollection(() -> new ArrayList<>(searchTerms.length)));
  }

  public List<SolrMovie> findMovies(String name) {
    return solrMovieRepo.findMovieByTitle(name);
  }

  public List<SolrMovie> getRecommendationsForThisUser(int userId, int noOfRec) {
    List<UserMovieRecommendation> recommendedMovies = recommenderService.getRecommendedMovieIds(userId, noOfRec);
    List<Long> movieIds = new ArrayList<>();
    recommendedMovies.forEach(movie -> movieIds.add(Long.valueOf(movie.getMovieId())));
    return solrMovieRepo.getMoviesWithIds(movieIds);
  }

  public Set<String> getFacetsOnGenres(Pageable pageable) {
    return solrMovieRepo.getFacetsOnGenres(pageable);
  }

}
