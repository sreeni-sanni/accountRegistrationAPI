package com.assignment.accountRegistrationAPI.service;

import java.util.Optional;
import java.util.UUID;

import org.openapitools.client.model.AccountInfo;
import org.springframework.stereotype.Service;

import com.assignment.accountRegistrationAPI.entity.AccountDetails;
import com.assignment.accountRegistrationAPI.exception.AccountRegistrationAPIException;
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
	
	/**
	 * This method will retrieve account details by customerId
	 * @param coustmerId
	 * @return AccountDetails
	 */
	@Transactional
	public AccountInfo getAccountDetails(String coustmerId) {
		Optional<AccountDetails> acctDetails = accountDetailsRepository.findByCustomerId(UUID.fromString(coustmerId));
		if (acctDetails.isPresent()) {
			return getAcctInfo(acctDetails.get());
		} else {
			throw new AccountRegistrationAPIException("Account details not found");
		}
	}
	
	private AccountInfo getAcctInfo(AccountDetails details) {
		AccountInfo acctInfo=new AccountInfo();
		acctInfo.setId(details.getId());
		acctInfo.setAccountNumber(details.getAccountNumber());
		acctInfo.setAccountType(details.getAccountType());
		acctInfo.setBalance(details.getBalance());
		acctInfo.setCurrency(details.getCurrency());
		acctInfo.setAccountCreatedTmstp(details.getAccountCreatedTmstp());
		return acctInfo;
	}

}
