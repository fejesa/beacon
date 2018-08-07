package com.bcm.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import com.bcm.app.SpringApplicationContext;
import com.bcm.dao.CustomerService;
import com.bcm.dao.TokenService;
import com.bcm.pojo.Customer;

@WebFilter(filterName = "/CustomerFilter", urlPatterns = { "/customer/*" })
public class CustomerFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest httpServletRequest = (HttpServletRequest) request;

		String token = httpServletRequest.getParameter("Token");
		String customerId = httpServletRequest.getParameter("CustomerId");

		if (token == null || token.trim().equals("")) {
			throw new IllegalArgumentException("Missign token");
		}
		if (customerId == null || customerId.trim().equals("")) {
			throw new IllegalArgumentException("Missign customer");
		}

		Customer customer = getCustomerService().getByCustomerId(customerId);
		if (!getTokenService().isValid(token, customer.getUserName())) {
			throw new IllegalArgumentException("Invalid token/customer");
		}

		String uuid = httpServletRequest.getParameter("BeaconUUID");
		String majorId = httpServletRequest.getParameter("MajorId");
		String minorId = httpServletRequest.getParameter("MinorId");

		if (uuid == null || uuid.trim().equals("") || majorId == null
				|| majorId.trim().equals("") || minorId == null
				|| minorId.trim().equals("")) {
			throw new IllegalArgumentException("Missing beacon ids");
		}

		String campaignId = httpServletRequest.getParameter("CampaignId");
		if (campaignId == null || campaignId.trim().equals("")) {
			throw new IllegalArgumentException("Missing campaign id");
		}

		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
	}

	private TokenService getTokenService() {
		return SpringApplicationContext.getBean("TokenService",
				TokenService.class);
	}

	private CustomerService getCustomerService() {
		return SpringApplicationContext.getBean("CustomerService",
				CustomerService.class);
	}
}
