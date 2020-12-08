package com.galos.recengine.model;

import java.util.List;
import org.apache.solr.client.solrj.beans.Field;

public class SolrMovie implements SearchableMovieDefinition {

  @Field(value = SearchableMovieDefinition.ID_FIELD_NAME)
  private long id;

  @Field(value = SearchableMovieDefinition.MOVIE_FIELD_NAME)
  private long movieId;

  @Field(value = SearchableMovieDefinition.DIRECTOR_FIELD_NAME)
  private String director;

  @Field(value = SearchableMovieDefinition.GENRES_FIELD_NAME)
  private List<String> genres;

  @Field(value = SearchableMovieDefinition.TITLE_FIELD_NAME)
  private String title;


  public String getDirector() {
    return director;
  }

  public void setDirector(String director) {
    this.director = director;
  }

  public List<String> getGenres() {
    return genres;
  }

  public void setGenres(List<String> genres) {
    this.genres = genres;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getMovieId() {
    return movieId;
  }

  public void setMovieId(long movieId) {
    this.movieId = movieId;
  }

  @Override
  public String toString() {
    return "SolrMovie{" +
        "id=" + id +
        ", movieId=" + movieId +
        ", director='" + director + '\'' +
        ", genres=" + genres +
        ", title='" + title + '\'' +
        '}';
  }
}
