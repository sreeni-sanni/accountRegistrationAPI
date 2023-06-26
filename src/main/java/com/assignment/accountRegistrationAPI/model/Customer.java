package com.assignment.accountRegistrationAPI.model;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;

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
@Table(name = "customer")
public class Customer {

	@JsonProperty("customerId")
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "customerId")
	private UUID customerId;

	@JsonProperty("firstName")
	@Column(name = "firstName")
	private String firstName;

	@JsonProperty("lastName")
	@Column(name = "lastName")
	private String lastName;

	@JsonProperty("userName")
	@Column(name = "userName")
	private String userName;

	@JsonProperty("dateOfBirth")
	@JsonFormat(pattern = "dd/MM/yyyy")
	@Column(name = "dateOfBirth")
	//@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate dateOfBirth;

	@JsonProperty("password")
	@Column(name = "password")
	private String password;

	@JsonProperty("address")
	@OneToOne(cascade = { CascadeType.ALL }, mappedBy = "customer")
	@PrimaryKeyJoinColumn
	private Address address;

	@JsonIgnore
	@OneToOne(cascade = { CascadeType.ALL }, mappedBy = "customer")
	@PrimaryKeyJoinColumn
	private CustomerIdentificationFile customerIdentificationFile;
	
	@JsonIgnore
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
