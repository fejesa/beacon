package com.bcm.pojo;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "BEACON")
@NamedQueries({
		@NamedQuery(name = "Beacon.getBeacons", query = "select b from Beacon b order by b.title"),
		@NamedQuery(name = "Beacon.getPartnerBeacons", query = "select b from Beacon b where b.locationType = 'Partner' order by b.title"),
		@NamedQuery(name = "Beacon.getBeaconById", query = "select b from Beacon b where b.id = :id"),
		@NamedQuery(name = "Beacon.getBeaconByIds", query = "select b from Beacon b where b.uuid = :uuid and b.majorId = :majorId and b.minorId = :minorId"),
		@NamedQuery(name = "Beacon.getZips", query = "select distinct(b.zipCode) from Beacon b order by b.zipCode"),
		@NamedQuery(name = "Beacon.getLocations", query = "select distinct(b.location) from Beacon b order by b.location"),
		@NamedQuery(name = "Beacon.getLocationTypes", query = "select distinct(b.locationType) from Beacon b order by b.locationType"),
		@NamedQuery(name = "Beacon.getAddresses", query = "select distinct concat(b.city, ' - ', b.street) from Beacon b order by b.city, b.street") })
public class Beacon extends BaseEntity {

	@Column(name = "uuid")
	private String uuid;

	@Column(name = "major_id")
	private long majorId;

	@Column(name = "minor_id")
	private long minorId;

	@Column(name = "title")
	private String title;

	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private BeaconStatus status;

	@Column(name = "house")
	private String house;

	@Column(name = "street")
	private String street;

	@Column(name = "zip")
	private String zipCode;

	@Column(name = "city")
	private String city;

	@Column(name = "country")
	private String country;

	@Column(name = "gps_latitude")
	private String gpsLatitude;

	@Column(name = "gps_longitude")
	private String gpsLongitude;

	@Column(name = "location_type")
	private String locationType;

	@Column(name = "location")
	private String location;

	@Column(name = "branch_name")
	private String branchName;

	@Temporal(TemporalType.DATE)
	@Column(name = "placement_date")
	private Date placementDate;

	@Column(name = "partner_input")
	private String partnerInput;

	@Column(name = "free_text1")
	private String freeText1;

	@Column(name = "free_text2")
	private String freeText2;

	@Temporal(TemporalType.DATE)
	@Column(name = "activation_date")
	private Date activationDate;

	@OneToMany(mappedBy = "beacon")
	private List<CampaignEvent> events;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public long getMajorId() {
		return majorId;
	}

	public void setMajorId(long majorId) {
		this.majorId = majorId;
	}

	public long getMinorId() {
		return minorId;
	}

	public void setMinorId(long minorId) {
		this.minorId = minorId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public BeaconStatus getStatus() {
		return status;
	}

	public void setStatus(BeaconStatus status) {
		this.status = status;
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

	public Date getPlacementDate() {
		return placementDate;
	}

	public void setPlacementDate(Date placementDate) {
		this.placementDate = placementDate;
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

	public Date getActivationDate() {
		return activationDate;
	}

	public void setActivationDate(Date activationDate) {
		this.activationDate = activationDate;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getAddress() {
		StringBuilder builder = new StringBuilder();
		if (city != null) {
			builder.append(city);
		}
		if (street != null) {
			builder.append(" - ").append(street);
		}
		return builder.toString();
	}

	public String getBeaconId() {
		return new StringBuilder().append(uuid).append(" # ").append(majorId)
				.append(" # ").append(minorId).toString();
	}
}