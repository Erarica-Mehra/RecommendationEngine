package com.galos.recengine.model;

import java.io.Serializable;
import java.sql.Timestamp;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("ratings")
public  class Rating implements Serializable {
  @PrimaryKeyColumn(name = "userid", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
  @Column("userid")
  private Integer userId;

  @PrimaryKeyColumn(name = "movieid", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
  @Column("movieid")
  private Integer movieId;

  @Column("rating")
  private float rating;

  @Column("timestamp")
  private Timestamp timestamp;

  public Rating() {
  }

  public Rating(int userId, int movieId, float rating, Timestamp timestamp) {
    this.userId = userId;
    this.movieId = movieId;
    this.rating = rating;
    this.timestamp = timestamp;
  }

  public int getUserId() {
    return userId;
  }

  public int getMovieId() {
    return movieId;
  }

  public float getRating() {
    return rating;
  }

  public Timestamp getTimestamp() {
    return timestamp;
  }

  public static Rating parseRating(String str) {
    String[] fields = str.split("::");
    if (fields.length != 4) {
      throw new IllegalArgumentException("Each line must contain 4 fields");
    }
    int userId = Integer.parseInt(fields[0]);
    int movieId = Integer.parseInt(fields[1]);
    float rating = Float.parseFloat(fields[2]);
    long timestamp = Long.parseLong(fields[3]);

    return new Rating(userId, movieId, rating, new Timestamp(timestamp));
  }

  @Override
  public String toString() {
    return "Rating{" +
        "userId=" + userId +
        ", movieId=" + movieId +
        ", rating=" + rating +
        ", timestamp=" + timestamp +
        '}';
  }
}


