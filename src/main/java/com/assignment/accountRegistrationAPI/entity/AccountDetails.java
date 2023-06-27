package com.assignment.accountRegistrationAPI.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "accountInfo")
public class AccountDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id")
	private UUID id = null;

	@Column(name = "accountNumber")
	private String accountNumber = null;

	@Column(name = "accountType")
	private String accountType = null;

	@Column(name = "balance")
	private Integer balance = null;

	@Column(name = "currency")
	private String currency = null;

	@Column(name = "accountCreatedTmstp")
	private LocalDateTime accountCreatedTmstp = null;

	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id", insertable = true, updatable = false, unique = true)
	private CustomerInfo customer;

	public AccountDetails(UUID id,String accountNumber, String accountType, Integer balance, String currency,
			LocalDateTime accountCreatedTmstp) {
		super();
		this.id=id;
		this.accountNumber = accountNumber;
		this.accountType = accountType;
		this.balance = balance;
		this.currency = currency;
		this.accountCreatedTmstp = accountCreatedTmstp;
	}

}
