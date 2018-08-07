package com.bcm.web;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.omnifaces.util.Faces;

import com.bcm.pojo.User;

@SessionScoped
@ManagedBean(name = "dashboardBean")
@SuppressWarnings("serial")
public class DashboardBean implements Serializable {

	private User user;

	public boolean isPermitted(String function) {
		return getUser().getUserType().isPermitted(function);
	}

	public User getUser() {
		if (user == null) {
			user = (User) Faces.getSessionAttribute("user");
		}
		return user;
	}

	public float getOpacity(String function) {
		return isPermitted(function) ? 1f : 0.4f;
	}
}