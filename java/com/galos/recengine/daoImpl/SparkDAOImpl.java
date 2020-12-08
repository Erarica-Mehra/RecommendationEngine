package com.galos.recengine.daoImpl;

import com.galos.recengine.repository.SparkRepo;
import java.util.Properties;
import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.ml.evaluation.RegressionEvaluator;
import org.apache.spark.ml.recommendation.ALS;
import org.apache.spark.ml.recommendation.ALSModel;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.functions;
import org.springframework.stereotype.Repository;

@Repository
public class SparkDAOImpl implements SparkRepo {

  @Override
  public void reRunRecommendations() {

    SparkSession spark = getSparkSession();
    Properties connectionProperties = new Properties();
    connectionProperties.put("user", "galos");
    connectionProperties.put("password", "galos");
    
    Dataset<Row> ratings = spark.read().format("org.apache.spark.sql.cassandra").option("keyspace", "galos").option("table", "ratings").load();
    Dataset<Row>[] splits = ratings.randomSplit(new double[]{0.8, 0.2});
    Dataset<Row> training = splits[0];
    Dataset<Row> test = splits[1];


    ALS als = new ALS()
        .setMaxIter(5)
        .setRegParam(0.01)
        .setUserCol("userid")
        .setItemCol("movieid")
        .setRatingCol("rating");
    ALSModel model = als.fit(training);
    model.setColdStartStrategy("drop");
    Dataset<Row> predictions = model.transform(test);

    RegressionEvaluator evaluator = new RegressionEvaluator()
        .setMetricName("rmse")
        .setLabelCol("rating")
        .setPredictionCol("prediction");
    double rmse = evaluator.evaluate(predictions);
    System.out.println("Root-mean-square error = " + rmse);

// Generate top 10 movie recommendations for each user
    Dataset<Row> userRecs = model.recommendForAllUsers(10);
    Dataset<Row> explodedRecs = userRecs.withColumn("recs", org.apache.spark.sql.functions.explode(
        functions.col("recommendations")));
    Dataset<Row> finalRecs = explodedRecs.select("userid", "recs.movieid",  "recs.rating");
    finalRecs.withColumn("id", functions.monotonically_increasing_id());
    finalRecs.show(5, false);
    finalRecs.write()
        .mode(SaveMode.Append).jdbc("jdbc:mysql://localhost:3306/recommender?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true", "recommender.user_movie_recommendations", connectionProperties);

  }
  private SparkSession getSparkSession() {
    return SparkSession.builder().sparkContext(getSparkContext()).getOrCreate();
  }

  private SparkContext getSparkContext() {
    return new SparkContext(getSparkConfig());

  }

  private SparkConf getSparkConfig() {
    return new SparkConf().setMaster("local").setAppName("recengine").setMaster("local");
  }
  private SQLContext getSQLContext() {
    SparkConf sparkConf = new SparkConf().setMaster("local").setAppName("recengine").setMaster("local");
    SparkContext context = new SparkContext(sparkConf);
    SparkSession ss = SparkSession.builder().config(sparkConf).getOrCreate();
    SQLContext sqlContext = new org.apache.spark.sql.SQLContext(ss);
    return sqlContext;
  }
}
