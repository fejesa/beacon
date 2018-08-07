package com.bcm.app;

import java.lang.invoke.MethodHandles;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.omnifaces.util.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
@ManagedBean(eager = true)
public class BcmResourceBundleBean {

	final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup()
			.lookupClass());

	@PostConstruct
	public void init() {

		logger.info("i18 configuration read");

		Messages.setResolver(new Messages.Resolver() {

			ResourceBundle bundle = ResourceBundle.getBundle(
					"MessageResources", new Locale("de"));

			public String getMessage(String message, Object... params) {
				if (bundle.containsKey(message)) {
					message = bundle.getString(message);
				}
				return MessageFormat.format(message, params);
			}
		});
	}
}
