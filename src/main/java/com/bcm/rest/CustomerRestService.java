package com.bcm.rest;

import java.lang.invoke.MethodHandles;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bcm.app.SpringApplicationContext;
import com.bcm.dao.BeaconService;
import com.bcm.dao.CampaignService;
import com.bcm.dao.CustomerService;
import com.bcm.dao.TokenService;
import com.bcm.dto.BeaconInfoResponse;
import com.bcm.dto.BeaconTO;
import com.bcm.dto.CampaignTO;
import com.bcm.dto.CustomerLoginResponse;
import com.bcm.dto.CustomerTO;
import com.bcm.pojo.Beacon;
import com.bcm.pojo.Customer;

@Path("/customer")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerRestService {

	private final Logger log = LoggerFactory.getLogger(MethodHandles.lookup()
			.lookupClass());

	private final SimpleDateFormat format = new SimpleDateFormat(
			"dd-MM-yyyy hh:mm:ss");

	@GET
	@Path("/login/{username}/{password}")
	public Response login(@PathParam("username") String username,
			@PathParam("password") String password) {

		log.info("Login user {}", username);

		CustomerLoginResponse response = new CustomerLoginResponse();
		
		if (!"99Qwerty00".equals(password)) {
			log.warn("Invalid password");
		} else {

			try {
				CustomerTO user = getCustomer(username, password);
				response.setUser(user);
				response.setToken(getTokenService().generate(username));
				response.setError(0);

			} catch (NoResultException nre) {
				log.warn("Invalid user {}", username);
			}
		}

		return Response.status(Status.ACCEPTED).entity(response).build();
	}

	private CustomerTO getCustomer(String username, String password) {
		Customer customer = getCustomerService().getByUserName(username);
		CustomerTO user = new CustomerTO();
		user.setCreated(format.format(new Date()));
		user.setEmail(customer.getEmail());
		user.setName(customer.getName());
		user.setCustomerId(customer.getCustomerId());

		return user;
	}

	@GET
	@Path("/beacon/{beaconuuid}/{majorid}/{minorid}/{user}/{token}")
	public Response getBeaconInfo(@PathParam("beaconuuid") String beaconuuid,
			@PathParam("majorid") long majorid,
			@PathParam("minorid") long minorid, @PathParam("user") String user,
			@PathParam("token") String token) {

		log.info("Get beacon {} info by user {}", beaconuuid, user);

		BeaconInfoResponse response = new BeaconInfoResponse();

		if (!getTokenService().isValid(token, user)) {
			log.warn("Invalid token {} is used by user {}", token, user);
			response.setError(2); // Authentication error
		} else {
			try {
				Customer customer = getCustomerService().getByUserName(user);
				try {
					BeaconTO beacon = getBeacon(beaconuuid, majorid, minorid,
							customer);
					response.setBeacon(beacon);
					response.setError(0);
				} catch (NoResultException nre) {
					log.warn(
							"Invalid beacon uuid: {}, major id: {}, minor id: {}",
							beaconuuid, majorid, minorid);
				}
			} catch (NoResultException nre) {
				log.warn("Invalid user {}", user);
			}
		}

		return Response.status(Status.ACCEPTED).entity(response).build();
	}

	private BeaconTO getBeacon(String beaconuuid, long majorid, long minorid,
			Customer customer) {

		Beacon b = getBeaconService().getBeaconByIds(beaconuuid, majorid,
				minorid);

		List<CampaignTO> campaigns = getCampaignService().getCustomerCampaigns(
				customer.getId(), b.getId());

		BeaconTO beacon = new BeaconTO();
		beacon.setBeaconuuid(b.getUuid());
		beacon.setMajorid(b.getMajorId());
		beacon.setMinorid(b.getMinorId());
		beacon.setCampaigns(campaigns);

		return beacon;
	}

	private CustomerService getCustomerService() {
		return SpringApplicationContext.getBean("CustomerService",
				CustomerService.class);
	}

	private BeaconService getBeaconService() {
		return SpringApplicationContext.getBean("BeaconService",
				BeaconService.class);
	}

	private TokenService getTokenService() {
		return SpringApplicationContext.getBean("TokenService",
				TokenService.class);
	}

	private CampaignService getCampaignService() {
		return SpringApplicationContext.getBean("CampaignService",
				CampaignService.class);
	}
}