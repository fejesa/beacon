package com.bcm.web;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.bcm.app.SpringApplicationContext;
import com.bcm.dao.UserService;
import com.bcm.pojo.User;

@ManagedBean(name = "userBean")
@ViewScoped
@SuppressWarnings("serial")
public class UserBean implements Serializable {

	private List<User> users;

	private User user;

	private Long selectedUserId;

	public List<User> getUsers() {
		if (users ==  null) {
			users = getUserService().getUsers();
		}
		return users;
	}

	public User getUser() {
		if (this.selectedUserId != null) {
			user = getUserService().getUserById(selectedUserId);
		}
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getSelectedUserId() {
		return selectedUserId;
	}

	public void setSelectedUserId(Long selectedUserId) {
		this.selectedUserId = selectedUserId;
		getUser();
	}

	private UserService getUserService() {
		return SpringApplicationContext.getBean("UserService", UserService.class);
	}
}
