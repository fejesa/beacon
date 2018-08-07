package com.bcm.app;

import java.util.Locale;

import javax.el.ELContext;
import javax.el.ELException;
import javax.faces.context.FacesContext;

import org.springframework.context.MessageSource;
import org.springframework.web.jsf.el.SpringBeanFacesELResolver;

public class CustomSpringBeanFacesELResolver extends SpringBeanFacesELResolver {

	@Override
	public Object getValue(ELContext elContext, Object base, Object property)
			throws ELException {
		if (base instanceof MessageSource && property instanceof String) {
			String result = ((MessageSource) base).getMessage(
					(String) property, null, getLocale());
			if (null != result) {
				elContext.setPropertyResolved(true);
			}
			return result;
		}
		return super.getValue(elContext, base, property);
	}

	private Locale getLocale() {
		return FacesContext.getCurrentInstance().getViewRoot().getLocale();
	}
}