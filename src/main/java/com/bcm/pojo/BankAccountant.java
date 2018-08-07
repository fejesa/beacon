package com.bcm.pojo;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "BANK_ACCOUNTANT")
@NamedQueries({
	@NamedQuery(name = "BankAccountant.getByAccountantId", query = "select b from BankAccountant b where b.accountantId = :id"),
	@NamedQuery(name = "BankAccountant.getAccountantIds", query = "select distinct(b.accountantId) from BankAccountant b order by b.accountantId")})
public class BankAccountant extends BaseEntity {

	@Column(name = "first_name")
	private String firstName; // NPVORNAME

	@Column(name = "last_name")
	private String lastName; // NPNACHNAME

	@Column(name = "phone")
	private String phone; // TELEFON

	@Column(name = "email")
	private String email; // EMAIL

	@Column(name = "accountant_id")
	private String accountantId; // BETREUER

	@OneToMany(mappedBy = "bankAccountant")
	private List<Customer> customers;

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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAccountantId() {
		return accountantId;
	}

	public void setAccountantId(String accountantId) {
		this.accountantId = accountantId;
	}

	public List<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}
}
