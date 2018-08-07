package com.bcm.pojo;

import java.util.ArrayList;
import java.util.List;

public class WhoFilter {

	public Integer ageFrom;

	public Integer ageTo;

	public Double balanceFrom;

	public Double balanceTo;

	public Integer bonusFrom;

	public Integer bonusTo;

	public List<String> jobs = new ArrayList<>();

	public List<String> zipCodes = new ArrayList<>();

	public List<String> accountants = new ArrayList<>();

	public List<String> branchCodes = new ArrayList<>();

	public Integer getAgeFrom() {
		return ageFrom;
	}

	public Integer getAgeTo() {
		return ageTo;
	}

	public Double getBalanceFrom() {
		return balanceFrom;
	}

	public Double getBalanceTo() {
		return balanceTo;
	}

	public Integer getBonusFrom() {
		return bonusFrom;
	}

	public Integer getBonusTo() {
		return bonusTo;
	}

	public List<String> getJobs() {
		return jobs;
	}

	public List<String> getZipCodes() {
		return zipCodes;
	}

	public List<String> getAccountants() {
		return accountants;
	}

	public List<String> getBranchCodes() {
		return branchCodes;
	}
}