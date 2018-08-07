package com.bcm.dto;

public class BeaconInfoResponse {

	private int error = 1;
	private BeaconTO beacon;

	public int getError() {
		return error;
	}

	public void setError(int error) {
		this.error = error;
	}

	public BeaconTO getBeacon() {
		return beacon;
	}

	public void setBeacon(BeaconTO beacon) {
		this.beacon = beacon;
	}
}
