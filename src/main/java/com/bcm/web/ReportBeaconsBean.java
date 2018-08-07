package com.bcm.web;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.springframework.stereotype.Component;

import com.bcm.app.SpringApplicationContext;
import com.bcm.dao.BeaconService;
import com.bcm.pojo.Beacon;

@ManagedBean(name = "reportBeaconsBean")
@RequestScoped
@Component
public class ReportBeaconsBean {

	private List<Beacon> beacons;

	private List<Beacon> filteredBeacons;

	public List<Beacon> getBeacons() {

		beacons = getBeaconService().getBeacons();

		return beacons;
	}

	public List<Beacon> getFilteredBeacons() {
		return filteredBeacons;
	}

	public void setFilteredBeacons(List<Beacon> filteredBeacons) {
		this.filteredBeacons = filteredBeacons;
	}

	private BeaconService getBeaconService() {
		return SpringApplicationContext.getBean("BeaconService",
				BeaconService.class);
	}
}
