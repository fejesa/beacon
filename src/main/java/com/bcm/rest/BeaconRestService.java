package com.bcm.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.bcm.app.SpringApplicationContext;
import com.bcm.dao.BeaconService;
import com.bcm.pojo.Beacon;
import com.bcm.pojo.BeaconStatus;

@Path("/beacon")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BeaconRestService {

	@GET
	public List<String> getBeacons() {
		List<Beacon> beacons = getBeaconService().getBeacons();
		List<String> list = new ArrayList<>();
		for (Beacon b : beacons) {
			if (b.getStatus() == BeaconStatus.Active) {
				list.add(b.getUuid());
			}
		}
		return list;
	}

	private BeaconService getBeaconService() {
		return SpringApplicationContext.getBean("BeaconService",
				BeaconService.class);
	}
}
