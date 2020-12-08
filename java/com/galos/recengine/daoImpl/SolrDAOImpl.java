package com.galos.recengine.daoImpl;

import com.galos.recengine.model.SolrMovie;
import com.galos.recengine.repository.SolrMovieRepo;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import javax.annotation.PostConstruct;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

@Repository
public class SolrDAOImpl implements SolrMovieRepo {

  private static final String ITEM_SEARCH_SPLIT_REGEX =  "(,|\\s)(?=([^\"]*\"[^\"]*\")*[^\"]*$)";

  @Value("${solr.movie.host}")
  private String solrMovieHost;

  private SolrClient solrClient;

  @PostConstruct
  public void initIt() throws Exception {
    try {
      solrClient = new HttpSolrClient.Builder(solrMovieHost).build();
      solrClient.ping();
    } catch (Exception e) {
      Logger.getLogger(this.getClass()).error("Error connecting to Solr : " + solrMovieHost + e);
    }
  }

  @Override
  public boolean solrDeltaImport() {
    ModifiableSolrParams params = new ModifiableSolrParams();
    params.set("qt", "/dataimport");
    params.set("command", "delta-import");

    try {
      solrClient.query(params);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  @Override
  public Set<String> findByTitleStartsWith(
      String fragment) {
    SolrQuery query = new SolrQuery();
    final Map<String, String> queryParamMap = new HashMap<String, String>();
    queryParamMap.put("q", "*:*");
    query.setFacet(true);
    query.setFacetPrefix(fragment);
    query.addFacetField("title_t");
    query.setQuery("*:*");
    Set<String> titles = new HashSet<>();
    try {
      final QueryResponse response = solrClient.query(query);
      List<Count> values = response.getFacetFields().get(0).getValues();
      for (Count count : values) {
        titles.add(count.getName());
      }
    } catch (SolrServerException | IOException e) {
      e.printStackTrace();
    }
    return titles;
  }

  @Override
  public List<SolrMovie> findMovieByTitle(String name) {

    SolrQuery query = new SolrQuery();
    query.setQuery(getQueryString(name));
    query.set("defType", "edismax");
    query.set("qf", "title_t^80 movie_tags^20");
    List<SolrMovie> list = Collections.emptyList();
    try {
      QueryResponse response = solrClient.query(query);
      list = response.getBeans(SolrMovie.class);
    } catch (SolrServerException | IOException e) {
      e.printStackTrace();
    }
    return list;

  }

  @Override
  public Set<String> getFacetsOnGenres(Pageable pageable) {
	  SolrQuery query = new SolrQuery();
	  query.setQuery("*:*");
	  query.setFacet(true);
	  query.addFacetField("genres_ss");
	  query.setFacetSort("count");
	  query.setFacetLimit(5);
	  QueryResponse response = null;
	  Set<String> titles = new HashSet<>();
	  try {
	      response = solrClient.query(query);
	      System.out.println(response.getFacetFields().get(0).getValues());
	      List<Count> values = response.getFacetFields().get(0).getValues();
	      for (Count count : values) {
	    	  titles.add(count.getName());
	      }
	    } catch (SolrServerException | IOException e) {
	      e.printStackTrace();
	    }
	    return titles;
  }

  @Override
  public List<SolrMovie> getMoviesWithIds(List<Long> recommendedMovieIds) {
    SolrQuery query = new SolrQuery();
    String joinedMovieIds = StringUtils.join(recommendedMovieIds, " ");
    query.setQuery(joinedMovieIds);
    List<SolrMovie> list = Collections.emptyList();
    try {
      QueryResponse response = solrClient.query(query);
      list = response.getBeans(SolrMovie.class);
    } catch (SolrServerException | IOException e) {
      e.printStackTrace();
    }
    return list;

  }

  private String getQueryString(String item) {
    String[] splittedItems = item.split(ITEM_SEARCH_SPLIT_REGEX);
    StringJoiner stringJoiner = new StringJoiner(" ");
    for (String splittedItem : splittedItems) {
      String term = splittedItem.trim();
      if (term.length() > 0) {
        if (term.startsWith("\"") && term.endsWith("\"")) {
          stringJoiner.add("(text:" + term + ")^100");
        } else {
          stringJoiner.add("(*" + splittedItem + "*)^10");
          stringJoiner.add("(" + splittedItem + ")^100");
        }
      }
    }
    return stringJoiner.toString();
  }


}
