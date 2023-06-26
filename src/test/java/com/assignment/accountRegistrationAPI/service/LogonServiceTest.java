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
import org.openapitools.client.model.LoginInfo;
import org.openapitools.client.model.LoginResponse;

import com.assignment.accountRegistrationAPI.entity.CustomerInfo;
import com.assignment.accountRegistrationAPI.exception.AccountRegistrationAPIException;
import com.assignment.accountRegistrationAPI.repository.LogonRepository;
import com.assignment.accountRegistrationAPI.service.LogonService;

@ExtendWith(MockitoExtension.class)
public class LogonServiceTest {

	LogonService logonService;

	@Mock
	LogonRepository logonRepository;

	@BeforeEach
	public void setService() {
		MockitoAnnotations.openMocks(this);
		logonService = new LogonService(logonRepository);
	}

	@Test
	@DisplayName("when customer logged on with success")
	public void customerLogonTest() {
		LoginInfo loginReq = new LoginInfo();
		loginReq.setUserName("Sreeni");
		loginReq.setPassword("gBE3XgRWjZ");
		CustomerInfo cust = new CustomerInfo();
		cust.setCustomerId(UUID.fromString("01a91d21-a8e6-4841-868f-4ab2eecadc01"));
		Optional<CustomerInfo> customer = Optional.of(cust);
		when(logonRepository.findByUserNameAndPassword(loginReq.getUserName(), loginReq.getPassword())).thenReturn(customer);
		LoginResponse res = logonService.logonCustomer(loginReq);
		assertEquals(cust.getCustomerId().toString(), res.getCustomerId());
	}

	@Test
	@DisplayName("when customer logon failed")
	public void customerLogonExeptionTest() {
		LoginInfo loginReq = new LoginInfo();
		loginReq.setUserName("Sreeni");
		loginReq.setPassword("gBE3XgRWjZ");
		AccountRegistrationAPIException exception = assertThrows(AccountRegistrationAPIException.class, () -> logonService.logonCustomer(loginReq));
		assertEquals("Please provide valid userName and password", exception.getMessage());
	}

}
