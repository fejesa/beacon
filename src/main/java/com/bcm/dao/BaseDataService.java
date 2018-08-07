package com.bcm.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.bcm.pojo.MessageType;

@Service("BaseDataService")
public class BaseDataService {

	private static Map<String, String> locationTypes;

	private static Map<String, String> locations;

	private static List<String> distances;

	private static List<String> messageTypes;

	private static Map<String, String> messageFrequencies;

	static {
		locations = new LinkedHashMap<>();
		locations.put("ATM", "ATM");
		locations.put("Eingang", "Eingang");
		locations.put("Verkaufsstelle", "Verkaufsstelle");

		locationTypes = new LinkedHashMap<>();
		
		locationTypes.put("Partner", "Partner");
		locationTypes.put("Bank Filiale", "Bank Filiale");

		distances = Arrays.asList("Unmittelbar", "Nah", "Weit");

		messageTypes = new ArrayList<>();
		for (MessageType mt : MessageType.values()) {
			messageTypes.add(mt.name());
		}

		messageFrequencies = new LinkedHashMap<>();
		messageFrequencies.put("Einmalig", "one-time");
		messageFrequencies.put("Jede Minute", "every-minute");
		messageFrequencies.put("Jede 10 Minute", "every-10-minutes");
		messageFrequencies.put("Jede 30 Minute", "every-30-minutes");
		messageFrequencies.put("Jeden Tag", "every-day");
		messageFrequencies.put("Jedes Monat", "every-month");
	}

	public Map<String, String> getMessageFrequencies() {
		return new LinkedHashMap<>(messageFrequencies);
	}

	public Map<String, String> getLocations() {
		return new LinkedHashMap<String, String>(locations);
	}

	public Map<String, String> getLocationTypes() {
		return new LinkedHashMap<String, String>(locationTypes);
	}

	public List<String> getDistances() {
		return new ArrayList<String>(distances);
	}

	public List<String> getMessageTypes() {
		return new ArrayList<String>(messageTypes);
	}
}