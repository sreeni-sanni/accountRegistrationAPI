package com.assignment.accountRegistrationAPI.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.client.model.AccountInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.assignment.accountRegistrationAPI.entity.AccountDetails;
import com.assignment.accountRegistrationAPI.service.AccountDetailsService;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(AccountOverviewApiController.class)
public class AccountOverviewApiControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	AccountDetailsService accountDetailsService;

	AccountOverviewApiController accountOverviewApiController;

	@BeforeEach
	public void setService() {
		MockitoAnnotations.openMocks(this);
		accountOverviewApiController = new AccountOverviewApiController(accountDetailsService);
	}
	@Test
	@DisplayName("Customer Account deatils")
	public void accountDeatilsTest() throws Exception {

		AccountInfo accDetails = new AccountInfo();
		accDetails.setAccountNumber("NL83ABCD6657861510");
		accDetails.setAccountType("savings");
		accDetails.setBalance(0);
		accDetails.setCurrency("€");
		accDetails.setAccountCreatedTmstp(LocalDateTime.now());
		when(accountDetailsService.getAccountDetails(any())).thenReturn(accDetails);
		mockMvc.perform(get("/overview").contentType("application/json").param("customerId",
				"01a91d21-a8e6-4841-868f-4ab2eecadc01")).andExpect(status().isOk())
				.andExpect(jsonPath("$.accountNumber", is("NL83ABCD6657861510")));
	}
	
	@Test
	@DisplayName("AccountDetailsAPI ratelimitor")
	public void loginRatelimiterTest() throws Exception {
		 ResponseEntity<String> res=accountOverviewApiController.accountRateLimitingFallback("djdsklfjds35435fef5",null);
		 assertEquals(res.getBody(), "Too Many Requests - Retry after some time");
	}
}
