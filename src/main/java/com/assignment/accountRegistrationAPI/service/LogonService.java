package com.assignment.accountRegistrationAPI.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.assignment.accountRegistrationAPI.exception.APIException;
import com.assignment.accountRegistrationAPI.model.Customer;
import com.assignment.accountRegistrationAPI.model.LoginRequest;
import com.assignment.accountRegistrationAPI.model.LoginResponse;
import com.assignment.accountRegistrationAPI.repository.LogonRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LogonService {

	private final LogonRepository logonRepository;

	public LogonService(LogonRepository logonRepository) {
		this.logonRepository = logonRepository;
	}

	@Transactional
	public LoginResponse logonCustomer(LoginRequest loginRequest) {
		Optional<Customer> customer = logonRepository.findByUserNameAndPassword(loginRequest.userName(),
				loginRequest.password());
		if (customer.isPresent()) {
			return new LoginResponse(customer.get().getCustomerId().toString(), "successfully loggedon");
		} else {
		}
		throw new APIException("Please provide valid userName and password");

	}

}
