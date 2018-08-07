package com.bcm.web;

import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import com.bcm.app.SpringApplicationContext;
import com.bcm.dao.UserService;
import com.bcm.pojo.User;

@ManagedBean(name = "loginBean")
@ViewScoped
@SuppressWarnings("serial")
public class LoginBean implements Serializable {

	private String loginName;

	private String password;

	private Map<String, String> institutes;

	private String institute;

	public void login() throws IOException {
		if (!"99Qwerty00".equals(password)) {
			Messages.create("authenticationError").error().add();
		} else {
			User user = getUserService().login(loginName);
			if (user == null) {
				Messages.create("authenticationError").error().add();
			} else {
				Faces.getRequest().getSession().setAttribute("user", user);
				Faces.redirect("bo/dashboard.xhtml");
			}
		}
	}

	public Map<String, String> getInstitutes() {
		if (institutes == null) {
			institutes = new LinkedHashMap<>();
			institutes.put("Sparkasse BCM", "Sparkasse BCM");
		}
		return institutes;
	}

	public boolean isInstituteSelected() {
		return institute != null && !institute.isEmpty();
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getInstitute() {
		return institute;
	}

	public void setInstitute(String institute) {
		this.institute = institute;
	}

	private UserService getUserService() {
		return SpringApplicationContext.getBean("UserService",
				UserService.class);
	}
}
