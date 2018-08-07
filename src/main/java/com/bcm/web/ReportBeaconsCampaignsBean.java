package com.bcm.web;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.springframework.stereotype.Component;

import com.bcm.app.SpringApplicationContext;
import com.bcm.dao.ReportService;
import com.bcm.dto.BeaconCampaignTO;

@ManagedBean(name = "reportBeaconsCampaignsBean")
@RequestScoped
@Component
public class ReportBeaconsCampaignsBean {

	private List<BeaconCampaignTO> beacons;

	private List<BeaconCampaignTO> filteredBeacons;

	private ReportService getReportService() {
		return SpringApplicationContext.getBean("ReportService",
				ReportService.class);
	}

	public List<BeaconCampaignTO> getBeacons() {

		beacons = getReportService().getBeaconWithCampaigns();

		return beacons;
	}

	public void setBeacons(List<BeaconCampaignTO> beacons) {
		this.beacons = beacons;
	}

	public List<BeaconCampaignTO> getFilteredBeacons() {
		return filteredBeacons;
	}

	public void setFilteredBeacons(List<BeaconCampaignTO> filteredBeacons) {
		this.filteredBeacons = filteredBeacons;
	}
}
