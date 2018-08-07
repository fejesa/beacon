package com.bcm.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bcm.dto.BeaconCampaignTO;
import com.bcm.pojo.Beacon;
import com.bcm.pojo.Campaign;
import com.bcm.pojo.CampaignEvent;

@Service("ReportService")
@Transactional(readOnly = true)
public class ReportService {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private CampaignService campaignService;

	public List<CampaignEvent> getCampaignsCustomers() {
		return em.createNamedQuery("CampaignEvent.getCampaignsCustomers",
				CampaignEvent.class).getResultList();
	}

	public List<BeaconCampaignTO> getBeaconWithCampaigns() {

		List<BeaconCampaignTO> list = new ArrayList<>();

		List<Beacon> beacons = em.createNamedQuery("Beacon.getBeacons",
				Beacon.class).getResultList();
		List<Campaign> campaigns = em.createNamedQuery("Campaign.getCampaigns",
				Campaign.class).getResultList();

		@SuppressWarnings("unchecked")
		List<Object[]> messageNumbers = em.createNamedQuery(
				"CampaignEvent.getMessagesNumbers").getResultList();

		for (Beacon b : beacons) {
			for (Campaign c : campaigns) {
				if (campaignService.isCampaignBeacon(c.getCampaignFilter()
						.getWhere(), b)) {

					BeaconCampaignTO to = new BeaconCampaignTO();
					to.setBeacon(b);
					to.setCampaign(c);

					for (Object[] objs : messageNumbers) {
						long bid = ((BigInteger) objs[0]).longValue();
						long cid = ((BigInteger) objs[1]).longValue();
						long received = ((BigInteger) objs[2]).longValue();
						long sent = ((BigInteger) objs[3]).longValue();

						if (bid == b.getId() && cid == c.getId()) {
							to.setMessagesSent(sent);
							to.setMessagesResponse(received);
						}
					}
					list.add(to);
				}
			}
		}
		return list;
	}
}