
//TODO ENABLE THIS IF YOU WANT THE SPRING SOLR LIBRARY
//I DIDN'T LIKE IT SO DISABLED IT FOR THE TIME BEING.
//THIS SPRING SOLR LIBRARY DOESN'T HAVE GOOD SUPPORT FOR JSON FACET API AND RELATEDNESS FUNCTION
//WHICH WE CAN USE TO PUSH CONTENT BASED RECOMMENDATIONS ALONG WITH ALS RECOMMENDATIONS.

//package com.galos.recengine.config;
//
//import org.apache.solr.client.solrj.SolrClient;
//import org.apache.solr.client.solrj.impl.HttpSolrClient;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.solr.core.SolrTemplate;
//import org.springframework.data.solr.repository.config.EnableSolrRepositories;
//
//@Configuration
//@EnableSolrRepositories(basePackages = "com.galos.recengine.repository", namedQueriesLocation = "classpath:solr-named-queries.properties")
//@ComponentScan
//public class SolrConfig {
//
//  @Bean
//  public SolrClient solrClient() {
//    return new HttpSolrClient.Builder().withBaseSolrUrl("http://localhost:8983/solr").build();
//  }
//
//  @Bean
//  public SolrTemplate solrTemplate(SolrClient client) throws Exception {
//    return new SolrTemplate(client);
//  }
//
//}
