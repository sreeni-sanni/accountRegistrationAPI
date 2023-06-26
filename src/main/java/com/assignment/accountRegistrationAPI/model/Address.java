package com.assignment.accountRegistrationAPI.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

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
	@JsonIgnore
	@JsonProperty("id")
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id")
	private UUID id;

	@JsonProperty("street")
	@Column(name = "street")
	private String street;

	@JsonProperty("city")
	@Column(name = "city")
	private String city;

	@JsonProperty("state")
	@Column(name = "state")
	private String state;

	@JsonProperty("country")
	@Column(name = "country")
	private String country;

	@JsonProperty("zip")
	@Column(name = "zip")
	private String zip;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id", insertable = true, updatable = false, unique = true)
	private CustomerInfo customer;
}
