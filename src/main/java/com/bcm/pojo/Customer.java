package com.bcm.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "CUSTOMER")
@NamedQueries({
		@NamedQuery(name = "Customer.getCustomers", query = "select c from Customer c left join fetch c.bankAccountant"),
		@NamedQuery(name = "Customer.getCustomerById", query = "select c from Customer c left join fetch c.bankAccountant where c.id = :id"),
		@NamedQuery(name = "Customer.getCustomerByCustomerId", query = "select c from Customer c left join fetch c.bankAccountant where c.customerId = :id"),
		@NamedQuery(name = "Customer.getByUsername", query = "select c from Customer c left join fetch c.bankAccountant where c.userName = :username"),
		@NamedQuery(name = "Customer.getOldest", query = "select min(c.birthDate) from Customer c"),
		@NamedQuery(name = "Customer.getYoungest", query = "select max(c.birthDate) from Customer c"),
		@NamedQuery(name = "Customer.getJobs", query = "select distinct(c.job) from Customer c order by c.job"),
		@NamedQuery(name = "Customer.getZips", query = "select distinct(c.zipCode) from Customer c order by c.zipCode"),
		@NamedQuery(name = "Customer.getMinBalance", query = "select min(c.balance) from Customer c"),
		@NamedQuery(name = "Customer.getMaxBalance", query = "select max(c.balance) from Customer c"),
		@NamedQuery(name = "Customer.getMinBonus", query = "select min(c.bonus) from Customer c"),
		@NamedQuery(name = "Customer.getMaxBonus", query = "select max(c.bonus) from Customer c"),
		@NamedQuery(name = "Customer.getBranches", query = "select distinct(c.branchCode) from Customer c order by c.branchCode")})
public class Customer extends BaseEntity {

	@Column(name = "username")
	private String userName;

	@Column(name = "cust_id")
	private String customerId; // kuid

	@Column(name = "title")
	private String title; // TITEL

	@Column(name = "street")
	private String street; // STRASSE

	@Column(name = "zip_code")
	private String zipCode; // PLZ

	@Column(name = "city")
	private String city; // ORT

	@Column(name = "branch_code")
	private String branchCode;	// BLZ

	@Column(name = "job")
	private String job; // KUBERUFBRNCH

	@Column(name = "balance", precision = 8, scale = 2) 
	private BigDecimal balance; // Kontostand

	@Column(name = "bonus")
	private Integer bonus; // Bonuspunkte

	@Column(name = "first_name")
	private String firstName; // vorname

	@Column(name = "last_name")
	private String lastName; // nachname

	@Temporal(TemporalType.DATE)
	@Column(name = "birth_date")
	private Date birthDate; // GEBDAT

	@Column(name = "email")
	private String email; // EMAIL

	@ManyToOne
	@JoinColumn(name = "accountant_id")
	private BankAccountant bankAccountant; // BETREUER

	@OneToMany(mappedBy = "customer")
	private List<CampaignEvent> events;

	public String getName() {
		return firstName + " " + lastName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public Integer getBonus() {
		return bonus;
	}

	public void setBonus(Integer bonus) {
		this.bonus = bonus;
	}

	public List<CampaignEvent> getEvents() {
		return events;
	}

	public void setEvents(List<CampaignEvent> events) {
		this.events = events;
	}

	public BankAccountant getBankAccountant() {
		return bankAccountant;
	}

	public void setBankAccountant(BankAccountant bankAccountant) {
		this.bankAccountant = bankAccountant;
	}

}