<h4> What is a Recommendation System </h4>
<p>
A  Recommendation System is a system that recommends items or preferences to a user based on her past behaviour as well as the behaviour of other users. Recommendation Systems are a huge portion of our modern day economy as they have a direct influence on a company’s sales.
For example, Amazons recommendation systems which look at your past purchasing behaviour to recommend other products that you might like.

Recommendation systems are not limited to products. A recommendation system used by New York Times recommends articles you might like based on the articles that you have read in the past.

Modern search engines are not limited to information retrieval. They are individualised, they look at your past behaviour to figure out what search results are most relevant to you.

There are several machine learning algorithms used by recommendation systems. The scope of this project is limited to Alternative Limiting Square(ALS) machine learning algorithm to recommend movies.
</p>

<h2> Overview </h2>

Scope of this project includes:
<ul>
  <li>Add and view movies and ratings</li>
 <li>Search movies using Apache Solr </li>
 <li>Autocomplete feature of Apache Solr</li>
<li>Runs Spark jobs that uses ALS algorithm to generate movie recommendation per user and flushes it to mysql database</li>
</ul>

<h2> Getting Started </h2>
<ol>
  <li>Clone the project from git and import into your IDE</li>
<li>Create database in mysql (Refer application.properties). Execute the sql dump and .sql files from /DB folder. </li>
<li> Install and setup Cassandra. Create keyspacae. (Refer application.properties)</br>
<code>/bin/cqlsh </code> </br>
<code>use galos; </code> </br>
Now run cql queries files from /DB folder. </br>
Copy the dump from the movies.csv, ratings.csv and tags.csv file using the following COPY command
  </li>
  
  <li> Install and set up Apache solr from the official website <br>
  Start Solr </br>
  <code>bin/solr start</code> </br>
  Create core named "movies" using the follwing command: </br>
  <code>cd /server/solr </code><br>
  <code>create -c "movies"</code> <br>
  Now manually copy the files from the /solr folder of the project to your /conf folder of the core you just created</br>
</li>
  <li> You are now good to go. Run the project. </li>
  </ol>
  <h2>Algorithm : ALS </h2>
  <p> ALS takes a training dataset (DataFrame) and several parameters that control the model creation process. To determine the best values for the parameters, we will use ALS to train several models</p> </br>
  <p>The mapping <code> /runRecommendations </code> exceutes a Spark job that does the following;</p> </br>
  <ol>
  <li> Loads the data from Cassadra table 'ratings' as a dataframe</li>
  <li> Create an ALS object from the ML Library and set a couple of hyper parameters</br>
  <code> ALS als=new ALS().setMaxIter(5).setRegParam(0.01).setUserCol("userid").setItemCol("movieid").setRatingCol("rating");</code></br>
  Read the documentation for the ALS class carefully. It will help you understand this step.</li>
        
  <li> <code>ALS.fit() </code> trains the model </li>
  <li> Then we can use the model to make  predictions for new users.  The algorithm generates 10 recommendations for each user and stores it into the user_movie_recommendation table</li>
  <li> The Mapping /getRecommendations/{userId} is used to fetch recomendations for a particular user</li>
  </ol>
  
  <h3>Apache Solr</h3>
  <p>I have also incorporated the feature to search movies using Apache Solr using the following mapping:</p></br>
  <ol>
    <li>/movie/{name} : to seach a movie by name</li>
    <li> /autocomplete : explore the autocomplete feature of Apache Solr </li>
    <li> /topGenres : feches the top five genres using the faceting in Apache Solr </li>
   </ol>
  
  <h2>Built With:</h2>
  <ul>
    <li>Java</li>
     <li>Apache Cassandra</li>
     <li>Apache Solr</li>
     <li>Mysql</li>
     <li>Apache Spark</li>
   </ul>
<h2>Author</h2>
  <p> Erarica Mehra</p>
 
