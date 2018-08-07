package com.bcm.web;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import org.springframework.stereotype.Component;

import com.bcm.app.SpringApplicationContext;
import com.bcm.dao.BaseDataService;
import com.bcm.dao.BeaconService;
import com.bcm.pojo.Beacon;
import com.bcm.pojo.BeaconStatus;
import com.bcm.pojo.User;
import com.bcm.pojo.UserType;

@ManagedBean(name = "beaconBean")
@ViewScoped
@Component
@SuppressWarnings("serial")
public class BeaconBean extends BaseBean {

	private List<Beacon> beacons;

	private Beacon beacon;

	private Long selectedBeaconId;

	private String locationType;

	private String location;

	private String uuid;

	private Long majorId;

	private Long minorId;

	private String beaconName;

	private String house;

	private String street;

	private String zipCode;

	private String city;

	private String country;

	private String gpsLatitude;

	private String gpsLongitude;

	private String branchName;

	private Date placementDate;

	private String partnerInput;

	private String freeText1;

	private String freeText2;

	private Date activationDate;

	private BeaconStatus status = BeaconStatus.Inactive;

	private Map<String, String> locationTypes;
	private Map<String, String> locations;

	public void clearForm(ActionEvent actionEvent) {
		clean();
	}

	public void cancel(ActionEvent actionEvent) throws IOException {
		clean();
		Faces.redirect("bo/beacons.xhtml");
	}

	private Beacon copy(Beacon beacon) {
		beacon.setActivationDate(activationDate);
		beacon.setBranchName(branchName);
		beacon.setCity(city);
		beacon.setCountry(country);
		beacon.setFreeText1(freeText1);
		beacon.setFreeText2(freeText2);
		beacon.setGpsLatitude(gpsLatitude);
		beacon.setGpsLongitude(gpsLongitude);
		beacon.setHouse(house);
		beacon.setLocation(location);
		beacon.setLocationType(locationType);
		beacon.setMajorId(majorId);
		beacon.setMinorId(minorId);
		beacon.setPartnerInput(partnerInput);
		beacon.setPlacementDate(placementDate);
		beacon.setStreet(street);
		beacon.setTitle(beaconName);
		beacon.setUuid(uuid);
		beacon.setZipCode(zipCode);
		
		return beacon;
	}

	public void deactivate() {
		if (selectedBeaconId != null) {
			getBeaconService().deactivate(selectedBeaconId);
			Messages.create("beaconInactivated").add();
			clean();
		}
	}
	
	public void activate() {
		if (selectedBeaconId != null) {
			getBeaconService().activate(selectedBeaconId);
			Messages.create("beaconActivated").add();
			clean();
		}
	}

	public void remove(ActionEvent actionEvent) {
		if (selectedBeaconId != null) {
			getBeaconService().remove(selectedBeaconId);
			Messages.create("successDeletion").add();
			clean();
		}
	}

	public void update(ActionEvent actionEvent) throws IOException {

		if (this.beacon == null) {
			beacon = new Beacon();
			beacon = copy(beacon);
			getBeaconService().addBeacon(beacon);
		} else {
			beacon = copy(beacon);
			getBeaconService().update(beacon);
		}

		clean();

		Messages.addFlashGlobalInfo("successUpdate");

		Faces.redirect("bo/beacons.xhtml");
	}

	public Beacon getBeacon() {
		if (selectedBeaconId != null) {
			this.beacon = getBeaconService().getBeacon(selectedBeaconId);

			this.activationDate = beacon.getActivationDate();
			this.placementDate = beacon.getPlacementDate();
			this.beaconName = beacon.getTitle();
			this.city = beacon.getCity();
			this.country = beacon.getCountry();
			this.freeText1 = beacon.getFreeText1();
			this.freeText2 = beacon.getFreeText2();
			this.gpsLatitude = beacon.getGpsLatitude();
			this.gpsLongitude = beacon.getGpsLongitude();
			this.house = beacon.getHouse();
			this.location = beacon.getLocation();
			this.locationType = beacon.getLocationType();
			this.majorId = beacon.getMajorId();
			this.minorId = beacon.getMinorId();
			this.partnerInput = beacon.getPartnerInput();
			this.street = beacon.getStreet();
			this.uuid = beacon.getUuid();
			this.zipCode = beacon.getZipCode();
			this.branchName = beacon.getBranchName();
			this.status = beacon.getStatus();
		}

		return beacon;
	}

	public User getUser(){
		return (User) Faces.getRequest().getSession().getAttribute("user");
	}
	
	
	public Date getActivationDate() {
		return activationDate;
	}

	public void setActivationDate(Date activationDate) {
		this.activationDate = activationDate;
	}

	public Date getPlacementDate() {
		return placementDate;
	}

	public void setPlacementDate(Date placementDate) {
		this.placementDate = placementDate;
	}

	public String getLocationType() {
		return locationType;
	}

	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Long getSelectedBeaconId() {
		return selectedBeaconId;
	}

	public void setSelectedBeaconId(Long selectedBeaconId) {
		this.selectedBeaconId = selectedBeaconId;
		getBeacon();
	}

	public List<Beacon> getBeacons() {
		beacons = getBeaconService().getBeacons(getUser().getId());
		return beacons;
	}

	public Map<String, String> getLocations() {
		if (locations == null) {
			locations = getBaseDataService().getLocations();
		}
		return locations;
	}

	public Map<String, String> getLocationTypes() {
		if (getUser().getUserType() == UserType.Partner) {
			locationTypes = new HashMap<>();
			locationTypes.put("Partner", "Partner");
		} else {
			locationTypes = getBaseDataService().getLocationTypes();
		}

		return locationTypes;
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

	public String getBeaconName() {
		return beaconName;
	}

	public void setBeaconName(String beaconName) {
		this.beaconName = beaconName;
	}

	public String getHouse() {
		return house;
	}

	public void setHouse(String house) {
		this.house = house;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getGpsLatitude() {
		return gpsLatitude;
	}

	public void setGpsLatitude(String gpsLatitude) {
		this.gpsLatitude = gpsLatitude;
	}

	public String getGpsLongitude() {
		return gpsLongitude;
	}

	public void setGpsLongitude(String gpsLongitude) {
		this.gpsLongitude = gpsLongitude;
	}

	public String getPartnerInput() {
		return partnerInput;
	}

	public void setPartnerInput(String partnerInput) {
		this.partnerInput = partnerInput;
	}

	public String getFreeText1() {
		return freeText1;
	}

	public void setFreeText1(String freeText1) {
		this.freeText1 = freeText1;
	}

	public String getFreeText2() {
		return freeText2;
	}

	public void setFreeText2(String freeText2) {
		this.freeText2 = freeText2;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public BeaconStatus getStatus() {
		return status;
	}

	public void setStatus(BeaconStatus status) {
		this.status = status;
	}
	
	public boolean isPartnerLocation() {
		return locationType != null && locationType.equals("Partner") ? true
				: false;
	}

	private BeaconService getBeaconService() {
		return SpringApplicationContext.getBean("BeaconService", BeaconService.class);
	}

	private BaseDataService getBaseDataService() {
		return SpringApplicationContext.getBean("BaseDataService", BaseDataService.class);
	}

	private void clean() {
		this.activationDate = null;
		this.placementDate = null;

		this.city = null;
		this.country = null;
		this.freeText1 = null;
		this.freeText2 = null;
		this.gpsLatitude = null;
		this.gpsLongitude = null;
		this.house = null;
		this.location = null;
		this.locationType = null;
		this.majorId = null;
		this.minorId = null;
		this.partnerInput = null;
		this.street = null;
		this.beaconName = null;
		this.uuid = null;
		this.zipCode = null;
		this.branchName = null;

		this.beacon = null;
		this.beacons = null;
		this.selectedBeaconId = null;
		this.status = BeaconStatus.Inactive;

	}
}