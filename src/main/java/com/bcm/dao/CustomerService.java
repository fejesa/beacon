package com.bcm.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bcm.pojo.Customer;

@Service("CustomerService")
@Transactional(readOnly = true)
public class CustomerService {

	@PersistenceContext
	private EntityManager em;

	public Customer getByUserName(String username) {
		return em.createNamedQuery("Customer.getByUsername", Customer.class)
				.setParameter("username", username).getSingleResult();
	}

	public Customer getByCustomerId(String customerId) {
		return em
				.createNamedQuery("Customer.getCustomerByCustomerId",
						Customer.class).setParameter("id", customerId)
				.getSingleResult();
	}

	public List<Customer> getCustomers() {
		return em.createNamedQuery("Customer.getCustomers", Customer.class)
				.getResultList();
	}
}