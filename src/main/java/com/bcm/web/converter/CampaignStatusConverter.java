package com.bcm.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.bcm.pojo.CampaignStatus;

@FacesConverter(value = "campaignStatusConverter")
public class CampaignStatusConverter implements Converter {

	@Override
	public CampaignStatus getAsObject(FacesContext context, UIComponent component,
			String value) {
		return CampaignStatus.valueOf(value);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		if (value == null) {
			return "";
		}
		try {
			return BcmEnumsI18Converter.getEnumString((CampaignStatus) value);
		} catch (ClassCastException cce) {
			// No faculty is selected - conversion cannot be done
			return (String) value;
		}
	}
}