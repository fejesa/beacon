package com.bcm.dao;

import java.lang.invoke.MethodHandles;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bcm.pojo.User;

@Service("UserService")
@Transactional(readOnly = true)
public class UserService {

	private final Logger log = LoggerFactory.getLogger(MethodHandles.lookup()
			.lookupClass());

	@PersistenceContext
	private EntityManager em;

	public List<User> getUsers() {
		return em.createNamedQuery("User.getUsers", User.class).getResultList();
	}

	public User getUserById(long id) {
		return em.createNamedQuery("User.getById", User.class)
				.setParameter("id", id).getSingleResult();
	}

	@Transactional(readOnly = false)
	public User login(String userName) {
		try {
			User user = em.createNamedQuery("User.getByUsername", User.class)
					.setParameter("username", userName).getSingleResult();
			user.setLastLogin(new Date());
			em.merge(user);
			return user;
		} catch (NoResultException e) {
			log.warn("Invalid username {}", userName);
		}
		return null;
	}
}
