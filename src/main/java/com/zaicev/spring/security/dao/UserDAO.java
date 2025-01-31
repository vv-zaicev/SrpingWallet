package com.zaicev.spring.security.dao;

import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zaicev.spring.security.models.User;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Component
public class UserDAO {

	private SessionFactory sessionFactory;

	@Autowired
	public UserDAO(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Transactional
	public Optional<User> getUserByName(String username) {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);

		Root<User> root = criteriaQuery.from(User.class);
		criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("username"), username));

		return Optional.ofNullable(session.createQuery(criteriaQuery).getSingleResultOrNull());
	}

	@Transactional
	public void createUser(User newUser) {
		sessionFactory.getCurrentSession().persist(newUser);
	}

	@Transactional
	public void updateUser(User updatedUser) {
		sessionFactory.getCurrentSession().merge(updatedUser);
	}

}
