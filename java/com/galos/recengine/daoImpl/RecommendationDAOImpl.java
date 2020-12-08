package com.galos.recengine.daoImpl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.galos.recengine.model.UserMovieRecommendation;
import com.galos.recengine.repository.RecommendationRepository;

@Repository
public class RecommendationDAOImpl implements RecommendationRepository {
	@PersistenceContext
	protected EntityManager entityManager;

	@Override
	public List<UserMovieRecommendation> findByUserid(int userid) {
		TypedQuery<UserMovieRecommendation> query = entityManager.createQuery(
				"SELECT u FROM UserMovieRecommendation u WHERE u.userid=:userid ", UserMovieRecommendation.class);
		query.setParameter("userid", userid);
		try {
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<UserMovieRecommendation>();
		}
	}

}
