package com.bcm.pojo;

import java.util.ArrayList;
import java.util.List;

public class WhereFilter {

	public List<String> locations = new ArrayList<>();

	public List<String> zipCodes = new ArrayList<>();

	public List<String> addresses = new ArrayList<>();

	public List<String> locationTypes = new ArrayList<>();

	public List<String> distances = new ArrayList<>();

	public List<String> getLocations() {
		return locations;
	}

	public void setLocations(List<String> locations) {
		this.locations = locations;
	}

	public List<String> getZipCodes() {
		return zipCodes;
	}

	public void setZipCodes(List<String> zipCodes) {
		this.zipCodes = zipCodes;
	}

	public List<String> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<String> addresses) {
		this.addresses = addresses;
	}

	public List<String> getLocationTypes() {
		return locationTypes;
	}

	public void setLocationTypes(List<String> locationTypes) {
		this.locationTypes = locationTypes;
	}

	public List<String> getDistances() {
		return distances;
	}

	public void setDistances(List<String> distances) {
		this.distances = distances;
	}
}