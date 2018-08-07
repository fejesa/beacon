package com.bcm.web;

import java.io.IOException;
import java.lang.invoke.MethodHandles;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.omnifaces.util.Faces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bcm.pojo.User;

@ManagedBean(name = "logoutBean")
@RequestScoped
public class LogoutBean {

	private final Logger log = LoggerFactory.getLogger(MethodHandles.lookup()
			.lookupClass());

	public void logout() throws IOException {
		User user = (User) Faces.getRequest().getSession().getAttribute("user");
		log.info("User {} logged out", user.getUserName());
		Faces.getRequest().getSession().removeAttribute("user");
		Faces.invalidateSession();
		Faces.redirect("login.xhtml");
	}
}