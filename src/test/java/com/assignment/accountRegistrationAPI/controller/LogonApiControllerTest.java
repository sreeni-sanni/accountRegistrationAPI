package com.assignment.accountRegistrationAPI.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.client.model.LoginInfo;
import org.openapitools.client.model.LoginResponse;
import org.openapitools.client.model.RegisterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.assignment.accountRegistrationAPI.controller.LogonApiController;
import com.assignment.accountRegistrationAPI.service.LogonService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(LogonApiController.class)
public class LogonApiControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	LogonService logonService;

	LogonApiController logonApiController;

	@BeforeEach
	public void setService() {
		MockitoAnnotations.openMocks(this);
		logonApiController = new LogonApiController(logonService);
	}

	@Test
	@DisplayName("Customer logon")
	public void logonTest() throws Exception {
		RegisterResponse req = new RegisterResponse();
		req.setUserName("userName");
		req.setPassword("gBE3XgRWjZ");
		LoginResponse response = new LoginResponse();
		response.setCustomerId("01a91d21-a8e6-4841-868f-4ab2eecadc01");
		response.setMessage("Successfully logged on");
		when(logonService.logonCustomer(any())).thenReturn(response);
		mockMvc.perform(
				post("/logon").contentType("application/json").content(new ObjectMapper().writeValueAsString(req)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.customerId", is("01a91d21-a8e6-4841-868f-4ab2eecadc01")));
	}

	@Test
	@DisplayName("logonAPI ratelimitor")
	public void loginRatelimiterTest() throws Exception {
		ResponseEntity<String> res = logonApiController
				.logonRateLimitingFallback(new LoginInfo(), null);
		assertEquals(res.getBody(), "Too Many Requests - Retry after some time");
	}
}
