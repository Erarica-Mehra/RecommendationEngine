package com.galos.recengine.repository;

import com.galos.recengine.model.SolrMovie;
import java.util.List;
import java.util.Set;

import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface SolrMovieRepo {

  public boolean solrDeltaImport();


  Set<String> findByTitleStartsWith(String splitSearchTermAndRemoveIgnoredCharacters);

  List<SolrMovie> findMovieByTitle(String name);

  Set<String> getFacetsOnGenres(Pageable pageable);

  List<SolrMovie> getMoviesWithIds(List<Long> recommendedMovieIds);
}
