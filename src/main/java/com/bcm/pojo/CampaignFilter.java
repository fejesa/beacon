package com.bcm.pojo;

public class CampaignFilter {

	public WhereFilter where = new WhereFilter();

	public WhoFilter who = new WhoFilter();

	public WhereFilter getWhere() {
		return where;
	}

	public WhoFilter getWho() {
		return who;
	}
}