package com.bcm.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.bcm.pojo.BeaconStatus;

@FacesConverter(value = "beaconStatusConverter")
public class BeaconStatusConverter implements Converter {

	@Override
	public BeaconStatus getAsObject(FacesContext context, UIComponent component,
			String value) {
		return BeaconStatus.valueOf(value);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		if (value == null) {
			return "";
		}
		try {
			return BcmEnumsI18Converter.getEnumString((BeaconStatus) value);
		} catch (ClassCastException cce) {
			// No faculty is selected - conversion cannot be done
			return (String) value;
		}
	}
}
