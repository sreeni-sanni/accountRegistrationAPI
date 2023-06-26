package com.assignment.accountRegistrationAPI.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.assignment.accountRegistrationAPI.exception.APIException;
import com.assignment.accountRegistrationAPI.model.AccountDetails;
import com.assignment.accountRegistrationAPI.repository.AccountDetailsRepository;
import com.assignment.accountRegistrationAPI.service.AccountDetailsService;

@ExtendWith(MockitoExtension.class)
public class AccountDetailsServiceTest {

	AccountDetailsService accountDetailsService;
	@Mock
	AccountDetailsRepository accountDetailsRepository;

	@BeforeEach
	public void setService() {
		MockitoAnnotations.openMocks(this);
		accountDetailsService = new AccountDetailsService(accountDetailsRepository);
	}

	@Test
	@DisplayName("customer accountdeatils with based on customerId")
	public void getAccountDetailsTest() {
		AccountDetails acctDetails = new AccountDetails();
		acctDetails.setAccountNumber("NL83ABCD6657861510");
		acctDetails.setAccountType("savings");
		acctDetails.setBalance(0);
		acctDetails.setCurrency("€");
		Optional<AccountDetails> accDetails = Optional.of(acctDetails);
		when(accountDetailsRepository.findByCustomerId(UUID.fromString("01a91d21-a8e6-4841-868f-4ab2eecadc01")))
				.thenReturn(accDetails);
		AccountDetails res = accountDetailsService.getAccountDetails("01a91d21-a8e6-4841-868f-4ab2eecadc01");
		assertEquals(acctDetails.getAccountNumber(), res.getAccountNumber());
	}

	@Test
	@DisplayName("when customer accountdeatils not found")
	public void accountDetailsExeptionTest() {
		APIException exception = assertThrows(APIException.class,
				() -> accountDetailsService.getAccountDetails("01a91d21-a8e6-4841-868f-4ab2eecadc01"));
		assertEquals("Account details not found", exception.getMessage());
	}

}
