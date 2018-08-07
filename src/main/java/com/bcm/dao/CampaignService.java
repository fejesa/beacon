package com.bcm.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bcm.dto.CampaignTO;
import com.bcm.pojo.Beacon;
import com.bcm.pojo.BeaconStatus;
import com.bcm.pojo.Campaign;
import com.bcm.pojo.CampaignEvent;
import com.bcm.pojo.CampaignStatus;
import com.bcm.pojo.Customer;
import com.bcm.pojo.MessageType;
import com.bcm.pojo.WhereFilter;
import com.bcm.pojo.WhoFilter;

@Service("CampaignService")
@Transactional(readOnly = true)
public class CampaignService {

	@PersistenceContext
	private EntityManager em;

	public List<Campaign> getCampaigns() {
		return em.createNamedQuery("Campaign.getCampaigns", Campaign.class)
				.getResultList();
	}

	public Campaign getCampaign(long id) {
		return em.createNamedQuery("Campaign.getCampaignById", Campaign.class)
				.setParameter("id", id).getSingleResult();
	}

	public Integer getAgeTo() {
		try {
			Date date = em.createNamedQuery("Customer.getOldest", Date.class)
					.getSingleResult();
			return getDuration(date);
		} catch (NoResultException nre) {
			return null;
		}
	}

	public Integer getAgeFrom() {
		try {
			Date date = em.createNamedQuery("Customer.getYoungest", Date.class)
					.getSingleResult();
			return getDuration(date);
		} catch (NoResultException nre) {
			return null;
		}
	}

	public List<String> getJobs() {
		List<String> list = em.createNamedQuery("Customer.getJobs",
				String.class).getResultList();
		list.removeAll(Collections.singleton(null));
		return list;
	}

	public List<String> getCustomerZipCodes() {
		List<String> list = em.createNamedQuery("Customer.getZips",
				String.class).getResultList();
		list.removeAll(Collections.singleton(null));
		return list;
	}

	public List<String> getBeaconZipCodes() {
		List<String> list = em.createNamedQuery("Beacon.getZips", String.class)
				.getResultList();
		list.removeAll(Collections.singleton(null));
		return list;
	}

	public List<String> getBeaconLocations() {
		List<String> list = em.createNamedQuery("Beacon.getLocations",
				String.class).getResultList();
		list.removeAll(Collections.singleton(null));
		return list;
	}

	public List<String> getBeaconLocationTypes() {
		List<String> list = em.createNamedQuery("Beacon.getLocationTypes",
				String.class).getResultList();
		list.removeAll(Collections.singleton(null));
		return list;
	}

	public List<String> getBeaconAddresses() {
		List<String> list = em.createNamedQuery("Beacon.getAddresses",
				String.class).getResultList();
		list.removeAll(Collections.singleton(null));
		return list;
	}

	public Double getMinBalance() {
		try {
			BigDecimal balance = em.createNamedQuery("Customer.getMinBalance",
					BigDecimal.class).getSingleResult();
			return Double.valueOf(balance.doubleValue());
		} catch (NoResultException | NullPointerException nre) {
			return null;
		}
	}

	public Double getMaxBalance() {
		try {
			BigDecimal balance = em.createNamedQuery("Customer.getMaxBalance",
					BigDecimal.class).getSingleResult();
			return Double.valueOf(balance.doubleValue());
		} catch (NoResultException | NullPointerException nre) {
			return null;
		}
	}

	public Integer getMinBonus() {
		try {
			return em.createNamedQuery("Customer.getMinBonus", Integer.class)
					.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	public Integer getMaxBonus() {
		try {
			return em.createNamedQuery("Customer.getMaxBonus", Integer.class)
					.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	public List<String> getBranchCodes() {
		List<String> list = em.createNamedQuery("Customer.getBranches",
				String.class).getResultList();
		list.removeAll(Collections.singleton(null));
		return list;
	}

	public List<String> getAccountants() {
		List<String> list = em.createNamedQuery(
				"BankAccountant.getAccountantIds", String.class)
				.getResultList();
		list.removeAll(Collections.singleton(null));
		return list;
	}

	private Integer getDuration(Date date) {
		if (date == null) {
			return null;
		}

		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int past = c.get(Calendar.YEAR);

		int now = Calendar.getInstance().get(Calendar.YEAR);

		return now - past;
	}

	@Transactional
	public void createCampaign(Campaign campaign) {
		campaign.setStatus(CampaignStatus.Inactive);
		em.persist(campaign);
	}

	@Transactional
	public void remove(long id) {
		Campaign campaign = em.find(Campaign.class, id);
		em.remove(campaign);
	}

	@Transactional
	public void activate(long id) {
		Campaign campaign = em.find(Campaign.class, id);
		campaign.setStatus(CampaignStatus.Active);
		em.merge(campaign);
	}

	@Transactional
	public void deactivate(long id) {
		Campaign campaign = em.find(Campaign.class, id);
		campaign.setStatus(CampaignStatus.Inactive);
		em.merge(campaign);
	}

	@Transactional
	public void update(Campaign campaign) {
		Campaign c = getCampaign(campaign.getId());

		c.setEndDate(campaign.getEndDate());
		c.setFilter(campaign.getFilter());
		c.setMsgFrequency(campaign.getMsgFrequency());
		c.setMsgText(campaign.getMsgText());
		c.setMsgType(campaign.getMsgType());
		c.setName(campaign.getName());
		c.setNotificationText(campaign.getNotificationText());
		c.setStartDate(campaign.getStartDate());

		em.merge(c);
	}

	@Transactional(readOnly = false)
	public Campaign getCustomerCampaign(long customerId, long beaconId,
			long campaignId) {

		Beacon beacon = em.find(Beacon.class, beaconId);
		if (!isActiveBeacon(beacon)) {
			throw new IllegalArgumentException("Beacon is not active");
		}

		Campaign campaign = em
				.createNamedQuery("Campaign.getCampaignById", Campaign.class)
				.setParameter("id", campaignId).getSingleResult();

		if (!isActiveCampaign(campaign)) {
			throw new IllegalArgumentException("Campaign is not active");
		}

		Customer customer = em
				.createNamedQuery("Customer.getCustomerById", Customer.class)
				.setParameter("id", customerId).getSingleResult();

		if (!isCampaignBeacon(campaign.getCampaignFilter().getWhere(), beacon)
				|| !isCampaingCustomer(campaign.getCampaignFilter().getWho(),
						customer)) {
			throw new IllegalArgumentException(
					"Beacon or customer is not related to the campaign");
		}

		generateReceivedMessage(campaign, customer, beacon);

		return campaign;
	}

	@Transactional(readOnly = false)
	public List<CampaignTO> getCustomerCampaigns(long customerId, long beaconId) {

		Beacon beacon = em.find(Beacon.class, beaconId);
		if (!isActiveBeacon(beacon)) {
			return Collections.emptyList();
		}

		Customer customer = em
				.createNamedQuery("Customer.getCustomerById",
						Customer.class).setParameter("id", customerId)
				.getSingleResult();

		List<Campaign> campaigns = em.createNamedQuery("Campaign.getCampaigns",
				Campaign.class).getResultList();
		List<CampaignTO> result = new ArrayList<>();
		for (Campaign campaign : campaigns) {
			if (isActiveCampaign(campaign)
					&& isCampaignBeacon(
							campaign.getCampaignFilter().getWhere(), beacon)
					&& isCampaingCustomer(
							campaign.getCampaignFilter().getWho(), customer)
					&& !hasSendMessage(campaign, customer, beacon)) {

				generateSentMessage(campaign, customer, beacon);

				CampaignTO to = new CampaignTO();
				to.setId(campaign.getId());
				to.setType(campaign.getMsgType().name());
				to.setNotification(campaign.getNotificationText());
				if (campaign.getMsgType() == MessageType.Url) {
					to.setUrl(generateUrl(campaign, customer, beacon));
				}
				result.add(to);
			}
		}

		return result;
	}

	private boolean isActiveBeacon(Beacon beacon) {
		return beacon.getStatus() == BeaconStatus.Active;
	}

	private boolean isActiveCampaign(Campaign campaign) {
		if (campaign.getStatus() == CampaignStatus.Active) {
			Calendar c = Calendar.getInstance();
			if (campaign.getEndDate().before(c.getTime())) {
				return false;
			}
			if (campaign.getStartDate().before(c.getTime())) {
				return true;
			}
		}
		return false;
	}

	private String generateUrl(Campaign campaign, Customer customer,
			Beacon beacon) {
		// In case of url type append customer and beacon ids
		StringBuilder builder = new StringBuilder(campaign.getMsgText());
		builder.append("?KuID=").append(customer.getCustomerId());
		builder.append("&BeaconUUID=").append(beacon.getUuid());
		builder.append("&MajorID=").append(beacon.getMajorId());
		builder.append("&MinorID=").append(beacon.getMinorId());
		return builder.toString();
	}

	private void generateSentMessage(Campaign campaign, Customer customer,
			Beacon beacon) {
		// Persist message event
		CampaignEvent event = new CampaignEvent();
		event.setBeacon(beacon);
		event.setCampaign(campaign);
		event.setCustomer(customer);
		event.setMsgSent(new Date());

		em.persist(event);
	}

	private void generateReceivedMessage(Campaign campaign, Customer customer,
			Beacon beacon) {
		// Persist message event
		CampaignEvent event = new CampaignEvent();
		event.setBeacon(beacon);
		event.setCampaign(campaign);
		event.setCustomer(customer);
		event.setMsgReceived(new Date());

		em.persist(event);
	}

	private static final long MINUTE = 60 * 1000L;
	private static final long MINUTES_10 = 10 * 60 * 1000L;
	private static final long MINUTES_30 = 30 * 60 * 1000L;
	private static final long DAY = 24 * 60 * 60 * 1000L;
	private static final long MONTH = 30 * 24 * 60 * 60 * 1000L;

	private boolean hasSendMessage(Campaign campaign, Customer customer,
			Beacon beacon) {
		// We have to check the last time of the event generated to the given
		// client in the given campaign...
		try {
			Date date = em
					.createNamedQuery("CampaignEvent.getLastByCustomer",
							Date.class).setParameter("campaign", campaign)
					.setParameter("customer", customer).getSingleResult();
			if (date != null) {
				Date now = new Date();
				switch (campaign.getMsgFrequency()) {
				case "one-time":
					return true;
				case "every-minute":
					return now.getTime() - date.getTime() < MINUTE;
				case "every-10-minutes":
					return now.getTime() - date.getTime() < MINUTES_10;
				case "every-30-minutes":
					return now.getTime() - date.getTime() < MINUTES_30;
				case "every-day":
					return now.getTime() - date.getTime() < DAY;
				case "every-month":
					return now.getTime() - date.getTime() < MONTH;
				default:
					break;
				}
			}
		} catch (NoResultException e) {
		}
		return false;
	}

	public boolean isCampaignBeacon(WhereFilter filter, Beacon beacon) {
		if (!filter.zipCodes.isEmpty()) {
			if (beacon.getZipCode() == null
					|| !filter.getZipCodes().contains(beacon.getZipCode())) {
				return false;
			}
		}
		if (!filter.locations.isEmpty()) {
			if (beacon.getLocation() == null
					|| !filter.getLocations().contains(beacon.getLocation())) {
				return false;
			}
		}
		if (!filter.getLocationTypes().isEmpty()) {
			if (beacon.getLocationType() == null
					|| !filter.getLocationTypes().contains(
							beacon.getLocationType())) {
				return false;
			}
		}
		if (!filter.getAddresses().isEmpty()) {
			if (beacon.getAddress() == null
					|| !filter.getAddresses().contains(beacon.getAddress())) {
				return false;
			}
		}
		return true;
	}

	public boolean isCampaingCustomer(WhoFilter filter, Customer customer) {
		if (!filter.getAccountants().isEmpty()) {
			if (customer.getBankAccountant() == null
					|| !filter.getAccountants().contains(
							customer.getBankAccountant().getAccountantId())) {
				return false;
			}
		}
		if (filter.getAgeFrom() != null) {
			if (customer.getBirthDate() == null
					|| filter.getAgeFrom() > getDuration(customer
							.getBirthDate())) {
				return false;
			}
		}
		if (filter.getAgeTo() != null) {
			if (customer.getBirthDate() == null
					|| filter.getAgeTo() < getDuration(customer.getBirthDate())) {
				return false;
			}
		}
		if (filter.getBalanceFrom() != null) {
			if (customer.getBalance() == null
					|| filter.getBalanceFrom() > customer.getBalance()
							.doubleValue()) {
				return false;
			}
		}
		if (filter.getBalanceTo() != null) {
			if (customer.getBalance() == null
					|| filter.getBalanceTo() < customer.getBalance()
							.doubleValue()) {
				return false;
			}
		}
		if (filter.getBonusFrom() != null) {
			if (customer.getBonus() == null
					|| filter.getBonusFrom() > customer.getBonus()) {
				return false;
			}
		}
		if (filter.getBalanceTo() != null) {
			if (customer.getBonus() == null
					|| filter.getBonusTo() < customer.getBonus()) {
				return false;
			}
		}
		if (!filter.getBranchCodes().isEmpty()) {
			if (customer.getBranchCode() == null
					|| !filter.getBranchCodes().contains(
							customer.getBranchCode())) {
				return false;
			}
		}
		if (!filter.getJobs().isEmpty()) {
			if (customer.getJob() == null
					|| !filter.getJobs().contains(customer.getJob())) {
				return false;
			}
		}
		if (!filter.getZipCodes().isEmpty()) {
			if (customer.getZipCode() == null
					|| !filter.getZipCodes().contains(customer.getZipCode())) {
				return false;
			}
		}
		return true;
	}
}