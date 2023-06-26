package com.assignment.accountRegistrationAPI.service;

import java.util.Optional;

import org.openapitools.client.model.LoginInfo;
import org.openapitools.client.model.LoginResponse;
import org.springframework.stereotype.Service;

import com.assignment.accountRegistrationAPI.entity.CustomerInfo;
import com.assignment.accountRegistrationAPI.exception.AccountRegistrationAPIException;
import com.assignment.accountRegistrationAPI.repository.LogonRepository;

import jakarta.transaction.Transactional;

@Service
public class LogonService {

	private final LogonRepository logonRepository;

	public LogonService(LogonRepository logonRepository) {
		this.logonRepository = logonRepository;
	}

	/**
	 * this method verify user customer based on userName and password
	 * 
	 * @param loginRequest
	 * @return
	 */
	@Transactional
	public LoginResponse logonCustomer(LoginInfo loginInfo) {
		Optional<CustomerInfo> customer = logonRepository.findByUserNameAndPassword(loginInfo.getUserName(),
				loginInfo.getPassword());
		if (customer.isPresent()) {
			LoginResponse response = new LoginResponse();
			response.setCustomerId(customer.get().getCustomerId().toString());
			response.setMessage("successfully loggedon");
			return response;
		} else {
			throw new AccountRegistrationAPIException("Please provide valid userName and password");
		}
	}

}
