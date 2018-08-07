package com.bcm.web;

import java.io.Serializable;

import org.omnifaces.util.Faces;

import com.bcm.pojo.User;

@SuppressWarnings("serial")
public class BaseBean implements Serializable {

	protected User getUser() {
		User user = (User) Faces.getRequest().getSession().getAttribute("user");
		return user;
	}
}