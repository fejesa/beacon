package com.bcm.dto;

import java.util.List;

public class BeaconTO {

	private String beaconuuid;

	private long majorid;

	private long minorid;

	private List<CampaignTO> campaigns;

	public String getBeaconuuid() {
		return beaconuuid;
	}

	public void setBeaconuuid(String beaconuuid) {
		this.beaconuuid = beaconuuid;
	}

	public long getMajorid() {
		return majorid;
	}

	public void setMajorid(long majorid) {
		this.majorid = majorid;
	}

	public long getMinorid() {
		return minorid;
	}

	public void setMinorid(long minorid) {
		this.minorid = minorid;
	}

	public List<CampaignTO> getCampaigns() {
		return campaigns;
	}

	public void setCampaigns(List<CampaignTO> campaigns) {
		this.campaigns = campaigns;
	}
}