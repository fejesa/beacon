package com.bcm.web;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.springframework.stereotype.Component;

import com.bcm.app.SpringApplicationContext;
import com.bcm.dao.ReportService;
import com.bcm.pojo.CampaignEvent;

@ManagedBean(name = "reportCampaignsCustomersBean")
@RequestScoped
@Component
public class ReportCampaignsCustomersBean {

	private List<CampaignEvent> events;

	private List<CampaignEvent> filteredEvents;

	public List<CampaignEvent> getEvents() {
		events = getReportService().getCampaignsCustomers();
		return events;
	}

	public void setEvents(List<CampaignEvent> events) {
		this.events = events;
	}

	public List<CampaignEvent> getFilteredEvents() {
		return filteredEvents;
	}

	public void setFilteredEvents(List<CampaignEvent> filteredEvents) {
		this.filteredEvents = filteredEvents;
	}

	private ReportService getReportService() {
		return SpringApplicationContext.getBean("ReportService",
				ReportService.class);
	}
}
