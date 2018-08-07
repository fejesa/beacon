package com.bcm.dto;

import com.bcm.pojo.Beacon;
import com.bcm.pojo.Campaign;

public class BeaconCampaignTO {

	private Beacon beacon;

	private Campaign campaign;

	private long messagesSent;

	private long messagesResponse;

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

	public long getMessagesSent() {
		return messagesSent;
	}

	public void setMessagesSent(long messagesSent) {
		this.messagesSent = messagesSent;
	}

	public long getMessagesResponse() {
		return messagesResponse;
	}

	public void setMessagesResponse(long messagesResponse) {
		this.messagesResponse = messagesResponse;
	}
}
