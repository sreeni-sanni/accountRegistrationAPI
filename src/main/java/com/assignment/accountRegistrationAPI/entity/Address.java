package com.assignment.accountRegistrationAPI.entity;

import java.util.UUID;

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

@Data
@Entity
@Table(name = "address")
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id")
	private UUID id;

	@Column(name = "street")
	private String street;

	@Column(name = "city")
	private String city;

	@Column(name = "state")
	private String state;

	@Column(name = "country")
	private String country;

	@Column(name = "zip")
	private String zip;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id", insertable = true, updatable = false, unique = true)
	private CustomerInfo customer;
}
