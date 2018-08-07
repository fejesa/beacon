package com.bcm.dao;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bcm.pojo.Beacon;
import com.bcm.pojo.BeaconStatus;
import com.bcm.pojo.User;
import com.bcm.pojo.UserType;

@Service("BeaconService")
@Transactional
public class BeaconService {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private UserService userService;

	@Transactional(readOnly = true)
	public List<Beacon> getBeacons(long userId) {
		User user = userService.getUserById(userId);
		return user.getUserType() == UserType.Partner ? em.createNamedQuery(
				"Beacon.getPartnerBeacons", Beacon.class).getResultList() : em
				.createNamedQuery("Beacon.getBeacons", Beacon.class)
				.getResultList();
	}

	@Transactional(readOnly = true)
	public List<Beacon> getBeacons() {
		return em.createNamedQuery("Beacon.getBeacons", Beacon.class)
				.getResultList();
	}

	public void activate(long id) {
		Beacon beacon = em.find(Beacon.class, id);
		beacon.setStatus(BeaconStatus.Active);
		em.merge(beacon);
	}

	public void deactivate(long id) {
		Beacon beacon = em.find(Beacon.class, id);
		beacon.setStatus(BeaconStatus.Inactive);
		em.merge(beacon);
	}

	@Transactional(readOnly = true)
	public Beacon getBeacon(long id) {
		return em.createNamedQuery("Beacon.getBeaconById", Beacon.class)
				.setParameter("id", id).getSingleResult();
	}

	public void update(Beacon beacon) {
		Beacon b = getBeacon(beacon.getId());

		b.setActivationDate(beacon.getActivationDate());
		b.setBranchName(beacon.getBranchName());
		b.setCity(beacon.getCity());
		b.setCountry(beacon.getCountry());
		b.setFreeText1(beacon.getFreeText1());
		b.setFreeText2(beacon.getFreeText2());
		b.setGpsLatitude(beacon.getGpsLatitude());
		b.setGpsLongitude(beacon.getGpsLongitude());
		b.setHouse(beacon.getHouse());
		b.setLocation(beacon.getLocation());
		b.setLocationType(beacon.getLocationType());
		b.setMajorId(beacon.getMajorId());
		b.setMinorId(beacon.getMinorId());
		b.setPartnerInput(beacon.getPartnerInput());
		b.setPlacementDate(beacon.getPlacementDate());
		b.setStreet(beacon.getStreet());
		b.setTitle(beacon.getTitle());
		b.setUuid(beacon.getUuid());
		b.setZipCode(beacon.getZipCode());

		em.merge(b);
	}

	public void addBeacon(Beacon beacon) {
		beacon.setStatus(BeaconStatus.Inactive);
		em.persist(beacon);
	}

	public void remove(long id) {
		Beacon beacon = em.find(Beacon.class, id);
		em.remove(beacon);
	}

	@Transactional(readOnly = true)
	public Beacon getBeaconByIds(String uuid, long majorId, long minorId) {
		return em.createNamedQuery("Beacon.getBeaconByIds", Beacon.class)
				.setParameter("uuid", uuid).setParameter("majorId", majorId)
				.setParameter("minorId", minorId).getSingleResult();
	}

	@Transactional(readOnly = true)
	public List<String> getZipCodes() {
		List<String> list = em.createNamedQuery("Beacon.getZips",
				String.class).getResultList();
		list.removeAll(Collections.singleton(null));
		return list;
	}
}