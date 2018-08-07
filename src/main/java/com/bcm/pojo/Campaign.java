package com.bcm.pojo;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.google.gson.Gson;

@Entity
@Table(name = "CAMPAIGN")
@NamedQueries({
		@NamedQuery(name = "Campaign.getCampaigns", query = "select c from Campaign c order by c.name"),
		@NamedQuery(name = "Campaign.getCampaignById", query = "select c from Campaign c where c.id = :id") })
public class Campaign extends BaseEntity {

	@Column(name = "name")
	private String name;

	@Column(name = "start_date")
	@Temporal(TemporalType.DATE)
	private Date startDate;

	@Column(name = "end_date")
	@Temporal(TemporalType.DATE)
	private Date endDate;

	@Column(name = "frequency")
	private String msgFrequency;

	@Column(name = "filter")
	@Lob
	private String filter;

	@Enumerated(EnumType.STRING)
	@Column(name = "msg_type")
	private MessageType msgType;

	@Column(name = "msg_text", length = 1024)
	private String msgText;

	@Column(name = "notif_text", length = 1024)
	private String notificationText;

	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private CampaignStatus status;

	@OneToMany(mappedBy = "campaign", cascade = CascadeType.REMOVE)
	private List<CampaignEvent> events;

	@Transient
	private CampaignFilter campaignFilter;

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

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public MessageType getMsgType() {
		return msgType;
	}

	public void setMsgType(MessageType msgType) {
		this.msgType = msgType;
	}

	public String getMsgText() {
		return msgText;
	}

	public void setMsgText(String msgText) {
		this.msgText = msgText;
	}

	public String getNotificationText() {
		return notificationText;
	}

	public void setNotificationText(String notificationText) {
		this.notificationText = notificationText;
	}

	public CampaignStatus getStatus() {
		return status;
	}

	public void setStatus(CampaignStatus status) {
		this.status = status;
	}

	public List<CampaignEvent> getEvents() {
		return events;
	}

	public CampaignFilter getCampaignFilter() {
		if (campaignFilter != null) {
			return campaignFilter;
		}
		if (filter == null) {
			campaignFilter = new CampaignFilter();
			return campaignFilter;
		}
		Gson gson = new Gson();
		campaignFilter = gson.fromJson(filter, CampaignFilter.class);
		return campaignFilter;
	}

	public void setCampaignFilter(CampaignFilter campaignFilter) {
		this.campaignFilter = campaignFilter;
	}
}