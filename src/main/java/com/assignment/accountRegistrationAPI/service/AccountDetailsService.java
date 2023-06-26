package com.assignment.accountRegistrationAPI.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.assignment.accountRegistrationAPI.exception.APIException;
import com.assignment.accountRegistrationAPI.model.AccountDetails;
import com.assignment.accountRegistrationAPI.repository.AccountDetailsRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AccountDetailsService {

	AccountDetailsRepository accountDetailsRepository;

	public AccountDetailsService(AccountDetailsRepository accountDetailsRepository) {
		this.accountDetailsRepository = accountDetailsRepository;
	}
	
	@Transactional
	public AccountDetails getAccountDetails(String coustmerId) {
		Optional<AccountDetails> acctDetails = accountDetailsRepository.findByCustomerId(UUID.fromString(coustmerId));
		if (acctDetails.isPresent()) {
			return acctDetails.get();
		} else {
			throw new APIException("Account details not found");
		}
	}

}
