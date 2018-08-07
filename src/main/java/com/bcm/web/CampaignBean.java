package com.bcm.web;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import org.primefaces.model.DualListModel;
import org.springframework.stereotype.Component;

import com.bcm.app.SpringApplicationContext;
import com.bcm.dao.BaseDataService;
import com.bcm.dao.CampaignService;
import com.bcm.pojo.Campaign;
import com.bcm.pojo.CampaignFilter;
import com.bcm.pojo.CampaignStatus;
import com.bcm.pojo.MessageType;
import com.bcm.pojo.UserType;
import com.google.gson.Gson;

@ManagedBean(name = "campaignBean")
@ViewScoped
@Component
@SuppressWarnings("serial")
public class CampaignBean extends BaseBean implements Serializable {

	private List<Campaign> campaigns;

	private Long selectedCampaignId;

	private Campaign campaign;

	private String name;

	private Date startDate;

	private Date endDate;

	private String msgFrequency;

	private CampaignStatus status = CampaignStatus.Inactive;

	private Map<String, String> messageFrequencies;

	private CampaignFilter filters;

	private DualListModel<String> jobs;

	private DualListModel<String> customerZipCodes;

	private DualListModel<String> branches;

	private DualListModel<String> relationshipManagers;

	private DualListModel<String> beaconLocations;

	private DualListModel<String> beaconLocationTypes;

	private DualListModel<String> beaconZipCodes;

	private DualListModel<String> beaconAddresses;

	private DualListModel<String> distances;

	private MessageType messageType;

	private String messageText;

	private String notificationText;

	public void clearForm(ActionEvent actionEvent) {
		clean();
		filters = new CampaignFilter();
	}

	public List<Campaign> getCampaigns() {
		campaigns = getCampaignService().getCampaigns();
		return campaigns;
	}

	public Campaign getCampaign() {
		if (selectedCampaignId != null) {
			campaign = getCampaignService().getCampaign(selectedCampaignId);
			filters = campaign.getCampaignFilter();
			this.name = campaign.getName();
			this.startDate = campaign.getStartDate();
			this.endDate = campaign.getEndDate();
			this.status = campaign.getStatus();
			this.msgFrequency = campaign.getMsgFrequency();
			this.messageType = campaign.getMsgType();
			this.messageText = campaign.getMsgText();
			this.notificationText = campaign.getNotificationText();
		}
		return campaign;
	}

	public void deactivate() {
		if (selectedCampaignId != null) {
			getCampaignService().deactivate(selectedCampaignId);
			Messages.create("campaignInactivated").add();
			clean();
			getCampaigns();
		}
	}

	public void activate() {
		if (selectedCampaignId != null) {
			getCampaignService().activate(selectedCampaignId);
			Messages.create("campaignActivated").add();
			clean();
			getCampaigns();
		}
	}

	public void remove(ActionEvent actionEvent) {
		if (selectedCampaignId != null) {
			getCampaignService().remove(selectedCampaignId);
			Messages.create("successDeletion").add();
			clean();
			getCampaigns();
		}
	}

	public void cancel(ActionEvent actionEvent) throws IOException {
		clean();
		Faces.redirect("bo/campaigns.xhtml");
	}

	public void setSelectedCampaignId(Long selectedCampaignId) {
		this.selectedCampaignId = selectedCampaignId;
		getCampaign();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getMsgFrequency() {
		return msgFrequency;
	}

	public void setMsgFrequency(String msgFrequency) {
		this.msgFrequency = msgFrequency;
	}

	public CampaignFilter getFilters() {
		return filters;
	}

	public Map<String, String> getMessageFrequencies() {
		if (messageFrequencies == null) {
			messageFrequencies = getBaseDataService().getMessageFrequencies();
		}
		return messageFrequencies;
	}

	public String getFromResource(String key) {
		Locale defaultLocale = new Locale("de");
		ResourceBundle bundle = ResourceBundle.getBundle("MessageResources",
				defaultLocale);
		try {
			return bundle.getString(key);
		} catch (MissingResourceException e) {
			try {
				bundle = ResourceBundle.getBundle("Enums", defaultLocale);
				return bundle.getString(key);
			}catch (MissingResourceException ex) {
				System.out.print("Key not found:" + key);
				return "";
			}
				
		}
	}

	public CampaignStatus getStatus() {
		return status;
	}

	public MessageType getMessageType() {
		return messageType;
	}

	public void setMessageType(MessageType messageType) {
		this.messageType = messageType;
	}

	public String getMessageText() {
		return messageText;
	}

	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}

	public String getNotificationText() {
		return notificationText;
	}

	public void setNotificationText(String notificationText) {
		this.notificationText = notificationText;
	}

	public String autosave(String menu) {
		update(null);
		switch (menu) {
		case "when":
			return "/bo/campaign_when.xhtml?faces-redirect=true";
		case "who":
			return "/bo/campaign_who.xhtml?faces-redirect=true";
		case "where":
			return "/bo/campaign_where.xhtml?faces-redirect=true";
		case "what":
			return "/bo/campaign_what.xhtml?faces-redirect=true";
		default:
			throw new IllegalArgumentException();
		}
	}

	public void update(ActionEvent actionEvent) {
		if (this.campaign == null) {
			campaign = new Campaign();
			copy(campaign);
			getCampaignService().createCampaign(campaign);
		} else {
			copy(campaign);
			getCampaignService().update(campaign);
		}
		getCampaigns();

		Messages.addFlashGlobalInfo("successUpdate");
	}

	public Integer getCustomerAgeFrom() {
		if (filters == null) {
			return null;
		}
		if (filters.who.ageFrom == null) {
			filters.who.ageFrom = getCampaignService().getAgeFrom();
		}
		return filters.who.ageFrom;
	}

	public void setCustomerAgeFrom(Integer i) {
		filters.who.ageFrom = i;
	}

	public Integer getCustomerAgeTo() {
		if (filters == null) {
			return null;
		}
		if (filters.who.ageTo == null) {
			filters.who.ageTo = getCampaignService().getAgeTo();
		}
		return filters.who.ageTo;
	}

	public void setCustomerAgeTo(Integer i) {
		filters.who.ageTo = i;
	}

	public Double getAccountBalanceFrom() {
		if (filters == null) {
			return null;
		}
		if (filters.who.balanceFrom == null) {
			filters.who.balanceFrom = getCampaignService().getMinBalance();
		}
		return filters.who.balanceFrom;
	}

	public void setAccountBalanceFrom(Double d) {
		filters.who.balanceFrom = d;
	}

	public Double getAccountBalanceTo() {
		if (filters == null) {
			return null;
		}
		if (filters.who.balanceTo == null) {
			filters.who.balanceTo = getCampaignService().getMaxBalance();
		}
		return filters.who.balanceTo;
	}

	public void setAccountBalanceTo(Double d) {
		filters.who.balanceTo = d;
	}

	public Integer getBonusFrom() {
		if (filters == null) {
			return null;
		}
		if (filters.who.bonusFrom == null) {
			filters.who.bonusFrom = getCampaignService().getMinBonus();
		}
		return filters.who.bonusFrom;
	}

	public void setBonusFrom(Integer i) {
		filters.who.bonusFrom = i;
	}

	public Integer getBonusTo() {
		if (filters == null) {
			return null;
		}
		if (filters.who.bonusTo == null) {
			filters.who.bonusTo = getCampaignService().getMaxBonus();
		}
		return filters.who.bonusTo;
	}

	public void setBonusTo(Integer i) {
		filters.who.bonusTo = i;
	}

	private DualListModel<String> createPickList(List<String> source,
			List<String> target) {
		source.removeAll(target);
		return new DualListModel<String>(source, target);
	}

	public DualListModel<String> getJobs() {
		if (filters == null) {
			return new DualListModel<String>();
		}
		jobs = createPickList(getCampaignService().getJobs(), filters.who.jobs);
		return jobs;
	}

	public void setJobs(DualListModel<String> jobs) {
		this.jobs = jobs;
		filters.who.jobs = jobs.getTarget();
	}

	public DualListModel<String> getCustomerZipCodes() {
		if (filters == null) {
			return new DualListModel<String>();
		}
		customerZipCodes = createPickList(getCampaignService()
				.getCustomerZipCodes(), filters.who.zipCodes);

		return customerZipCodes;
	}

	public void setCustomerZipCodes(DualListModel<String> zipCodes) {
		this.customerZipCodes = zipCodes;
		filters.who.zipCodes = zipCodes.getTarget();
	}

	public DualListModel<String> getBranches() {
		if (filters == null) {
			return new DualListModel<String>();
		}
		branches = createPickList(getCampaignService().getBranchCodes(),
				filters.who.branchCodes);
		return branches;
	}

	public void setBranches(DualListModel<String> branches) {
		this.branches = branches;
		filters.who.branchCodes = branches.getTarget();
	}

	public DualListModel<String> getRelationshipManagers() {
		if (filters == null) {
			return new DualListModel<String>();
		}
		relationshipManagers = createPickList(getCampaignService()
				.getAccountants(), filters.who.accountants);
		return relationshipManagers;
	}

	public void setRelationshipManagers(
			DualListModel<String> relationshipManagers) {
		this.relationshipManagers = relationshipManagers;
		filters.who.accountants = relationshipManagers.getTarget();
	}

	public DualListModel<String> getBeaconLocations() {
		if (filters == null) {
			return new DualListModel<String>();
		}
		beaconLocations = createPickList(getCampaignService()
				.getBeaconLocations(), filters.where.locations);
		return beaconLocations;
	}

	public void setBeaconLocations(DualListModel<String> beaconLocations) {
		this.beaconLocations = beaconLocations;
		filters.where.locations = beaconLocations.getTarget();
	}

	public DualListModel<String> getBeaconLocationTypes() {
		if (filters == null) {
			return new DualListModel<String>();
		}
		beaconLocationTypes = createPickList(
				getUser().getUserType() == UserType.Partner ? Arrays.asList("Partner")
						: getCampaignService()
				.getBeaconLocationTypes(), filters.where.locationTypes);
		return beaconLocationTypes;
	}

	public void setBeaconLocationTypes(DualListModel<String> beaconLocationTypes) {
		this.beaconLocationTypes = beaconLocationTypes;
		filters.where.locationTypes = beaconLocationTypes.getTarget();
	}

	public DualListModel<String> getBeaconZipCodes() {
		if (filters == null) {
			return new DualListModel<String>();
		}
		beaconZipCodes = createPickList(getCampaignService()
				.getBeaconZipCodes(), filters.where.zipCodes);
		return beaconZipCodes;
	}

	public void setBeaconZipCodes(DualListModel<String> beaconZipCodes) {
		this.beaconZipCodes = beaconZipCodes;
		filters.where.zipCodes = beaconZipCodes.getTarget();
	}

	public DualListModel<String> getBeaconAddresses() {
		if (filters == null) {
			return new DualListModel<String>();
		}
		beaconAddresses = createPickList(getCampaignService()
				.getBeaconAddresses(), filters.where.addresses);
		return beaconAddresses;
	}

	public void setBeaconAddresses(DualListModel<String> beaconAddresses) {
		this.beaconAddresses = beaconAddresses;
		filters.where.addresses = beaconAddresses.getTarget();
	}

	public DualListModel<String> getDistances() {
		if (filters == null) {
			return new DualListModel<String>();
		}
		distances = createPickList(getBaseDataService().getDistances(),
				filters.where.distances);
		return distances;
	}

	public void setDistances(DualListModel<String> distances) {
		this.distances = distances;
		filters.where.distances = distances.getTarget();
	}

	public List<String> getMessageTypes() {
		return getBaseDataService().getMessageTypes();
	}

	public boolean isDisabled() {
		return messageType == null || messageType.equals(MessageType.Counter);
	}

	private Campaign copy(Campaign campaign) {
		campaign.setName(name);
		campaign.setStartDate(startDate);
		campaign.setEndDate(endDate);
		campaign.setMsgFrequency(msgFrequency);
		campaign.setCampaignFilter(filters);
		campaign.setMsgText(messageText);
		campaign.setMsgType(messageType);
		campaign.setNotificationText(notificationText);
		Gson gson = new Gson();
		campaign.setFilter(gson.toJson(campaign.getCampaignFilter()));
		return campaign;
	}

	private CampaignService getCampaignService() {
		return SpringApplicationContext.getBean("CampaignService",
				CampaignService.class);
	}

	private BaseDataService getBaseDataService() {
		return SpringApplicationContext.getBean("BaseDataService",
				BaseDataService.class);
	}

	private void clean() {
		name = null;
		startDate = null;
		endDate = null;
		msgFrequency = null;
		filters = null;
		status = CampaignStatus.Inactive;
		jobs = null;
		customerZipCodes = null;
		branches = null;
		relationshipManagers = null;
		beaconAddresses = null;
		beaconLocations = null;
		beaconLocationTypes = null;
		beaconZipCodes = null;
		distances = null;
		messageType = null;
		messageText = null;
		campaign = null;
	}
}