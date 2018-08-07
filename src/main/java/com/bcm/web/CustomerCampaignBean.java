package com.bcm.web;

import java.io.Serializable;
import java.lang.invoke.MethodHandles;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.bcm.app.SpringApplicationContext;
import com.bcm.dao.BeaconService;
import com.bcm.dao.CampaignService;
import com.bcm.dao.CustomerService;
import com.bcm.pojo.Beacon;
import com.bcm.pojo.Campaign;
import com.bcm.pojo.Customer;

@ManagedBean(name = "customerCampaignBean")
@ViewScoped
@Component
@SuppressWarnings("serial")
public class CustomerCampaignBean implements Serializable {

	private final Logger log = LoggerFactory.getLogger(MethodHandles.lookup()
			.lookupClass());

	private String customerId;

	private String uuid;

	private Long majorId;

	private Long minorId;

	private Long campaignId;

	private Campaign campaign;

	private Customer customer;

	private String message;

	private String token;

	public void send() {
		log.info("Customer {} send message: {}", customerId, message);
		message = null;
		Messages.create("customerCampaignMessageSent").add();
	}

	public Campaign getCampaign() {
		try {
			Beacon beacon = getBeaconService().getBeaconByIds(uuid, majorId,
					minorId);
			customer = getCustomerService()
					.getByCustomerId(customerId);
			campaign = getCampaignService().getCustomerCampaign(
					customer.getId(), beacon.getId(), campaignId);
			return campaign;
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	public Customer getCustomer() {
		if (customer == null) {
			customer = getCustomerService().getByCustomerId(customerId);
		}
		return customer;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Long getMajorId() {
		return majorId;
	}

	public void setMajorId(Long majorId) {
		this.majorId = majorId;
	}

	public Long getMinorId() {
		return minorId;
	}

	public void setMinorId(Long minorId) {
		this.minorId = minorId;
	}

	public Long getCampaignId() {
		return campaignId;
	}

	public void setCampaignId(Long campaignId) {
		this.campaignId = campaignId;
	}

	public void setCampaign(Campaign campaign) {
		this.campaign = campaign;
	}

	private CustomerService getCustomerService() {
		return SpringApplicationContext.getBean("CustomerService",
				CustomerService.class);
	}

	private BeaconService getBeaconService() {
		return SpringApplicationContext.getBean("BeaconService",
				BeaconService.class);
	}

	private CampaignService getCampaignService() {
		return SpringApplicationContext.getBean("CampaignService",
				CampaignService.class);
	}
}
