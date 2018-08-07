package com.bcm.web.converter;

import java.util.Locale;
import java.util.ResourceBundle;

public class BcmEnumsI18Converter {

	private final static Locale defaultLocale = new Locale("de");

	private static ResourceBundle getBundle() {
		return ResourceBundle.getBundle("Enums", defaultLocale);
	}

	public static <E extends Enum<E>> String getEnumString(E e) {
		return getBundle().getString(e.name());
	}
}
