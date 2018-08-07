package com.bcm.web;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.primefaces.webapp.MultipartRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUploadFilter implements Filter {

	private final Logger logger = LoggerFactory.getLogger(MethodHandles
			.lookup().lookupClass());

	private String uploadDir;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		uploadDir = filterConfig.getInitParameter("uploadDirectory");
		logger.info("FileUploadFilter initiated successfully; upload dir is {}", uploadDir);
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		boolean isMultipart = ServletFileUpload
				.isMultipartContent(httpServletRequest);

		if (isMultipart) {
			DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
			if (uploadDir != null) {
				diskFileItemFactory.setRepository(new File(uploadDir));
			}

			ServletFileUpload servletFileUpload = new ServletFileUpload(
					diskFileItemFactory);
			MultipartRequest multipartRequest = new MultipartRequest(
					httpServletRequest, servletFileUpload);
			filterChain.doFilter(multipartRequest, response);
		} else {
			filterChain.doFilter(request, response);
		}
	}

	@Override
	public void destroy() {
	}
}