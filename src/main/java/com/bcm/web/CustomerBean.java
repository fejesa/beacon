package com.bcm.web;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.bcm.app.SpringApplicationContext;
import com.bcm.dao.CustomerService;
import com.bcm.pojo.Customer;

@ManagedBean(name = "customerBean")
@RequestScoped
public class CustomerBean {

	public List<Customer> getCustomers() {
		return getCustomerService().getCustomers();
	}

	private CustomerService getCustomerService() {
		return SpringApplicationContext.getBean("CustomerService", CustomerService.class);
	}
}
