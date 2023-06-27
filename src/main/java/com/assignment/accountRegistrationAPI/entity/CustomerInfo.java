package com.assignment.accountRegistrationAPI.entity;

import java.time.LocalDate;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "CustomerInfo")
public class CustomerInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "customerId")
	private UUID customerId;

	@Column(name = "firstName")
	private String firstName;

	@Column(name = "lastName")
	private String lastName;

	@Column(name = "userName")
	private String userName;

	@Column(name = "dateOfBirth")
	private LocalDate dateOfBirth;

	@JsonProperty("password")
	@Column(name = "password")
	private String password;

	@OneToOne(cascade = { CascadeType.ALL }, mappedBy = "customer")
	@PrimaryKeyJoinColumn
	private Address address;

	@OneToOne(cascade = { CascadeType.ALL }, mappedBy = "customer")
	@PrimaryKeyJoinColumn
	private CustomerIdentificationFile customerIdentificationFile;
	
	@OneToOne(cascade = { CascadeType.ALL }, mappedBy = "customer")
	@PrimaryKeyJoinColumn
	private AccountDetails accountInfo;

	public void setAccountInfo(AccountDetails accountInfo) {
		this.accountInfo = accountInfo;
		this.accountInfo.setCustomer(this);
	}

	public void setCustomerIdentificationFile(CustomerIdentificationFile file) {
		this.customerIdentificationFile = file;
		this.customerIdentificationFile.setCustomer(this);
	}

}
