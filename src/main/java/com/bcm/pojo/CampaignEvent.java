package com.bcm.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "CAMPAING_EVENT")
@NamedQueries({
		@NamedQuery(name = "CampaignEvent.getLastByCustomer", query = "select max(e.msgSent) from CampaignEvent e where e.campaign = :campaign and e.customer = :customer"),
		@NamedQuery(name = "CampaignEvent.getCampaignsCustomers", query = "select e from CampaignEvent e left join fetch e.campaign left join fetch e.customer order by e.campaign") })
@SqlResultSetMappings({ @SqlResultSetMapping(name = "CampaignEvent.messageNumbers", columns = {
		@ColumnResult(name = "beacon_id"), @ColumnResult(name = "camp_id"),
		@ColumnResult(name = "received"), @ColumnResult(name = "sent") }) })
@NamedNativeQueries({ @NamedNativeQuery(name = "CampaignEvent.getMessagesNumbers", query = "select beacon_id, camp_id, count(msg_received) as received, count(msg_sent) as sent from jbossews.CAMPAING_EVENT group by beacon_id, camp_id", resultSetMapping = "CampaignEvent.messageNumbers") })
public class CampaignEvent extends BaseEntity {

	@ManyToOne
	@JoinColumn(name = "cust_id")
	private Customer customer;

	@ManyToOne
	@JoinColumn(name = "beacon_id")
	private Beacon beacon;

	@ManyToOne
	@JoinColumn(name = "camp_id")
	private Campaign campaign;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "msg_sent")
	private Date msgSent;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "msg_received")
	private Date msgReceived;

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Beacon getBeacon() {
		return beacon;
	}

	public void setBeacon(Beacon beacon) {
		this.beacon = beacon;
	}

	public Campaign getCampaign() {
		return campaign;
	}

	public void setCampaign(Campaign campaign) {
		this.campaign = campaign;
	}

	public Date getMsgSent() {
		return msgSent;
	}

	public void setMsgSent(Date msgSent) {
		this.msgSent = msgSent;
	}

	public Date getMsgReceived() {
		return msgReceived;
	}

	public void setMsgReceived(Date msgReceived) {
		this.msgReceived = msgReceived;
	}
}
