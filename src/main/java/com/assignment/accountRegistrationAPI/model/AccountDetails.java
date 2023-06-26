package com.assignment.accountRegistrationAPI.model;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
//@RequiredArgsConstructor
@Entity
@Table(name = "accountInfo")
public class AccountDetails {

	@JsonIgnore
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id")
	private UUID id = null;

	@JsonProperty("accountNumber")
	@Column(name = "accountNumber")
	private String accountNumber = null;

	@JsonProperty("accountType")
	@Column(name = "accountType")
	private String accountType = null;

	@JsonProperty("balance")
	@Column(name = "balance")
	private Integer balance = null;

	@JsonProperty("currency")
	@Column(name = "currency")
	private String currency = null;

	@JsonProperty("accountCreatedTmstp")
	@Column(name = "accountCreatedTmstp")
	private LocalDate accountCreatedTmstp = null;

	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id", insertable = true, updatable = false, unique = true)
	private Customer customer;

	public AccountDetails(String accountNumber, String accountType, Integer balance, String currency,
			LocalDate accountCreatedTmstp) {
		super();
		this.accountNumber = accountNumber;
		this.accountType = accountType;
		this.balance = balance;
		this.currency = currency;
		this.accountCreatedTmstp = accountCreatedTmstp;
	}

}
