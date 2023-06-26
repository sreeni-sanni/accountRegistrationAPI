
package com.assignment.accountRegistrationAPI.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.client.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import com.assignment.accountRegistrationAPI.controller.RegisterApiController;
import com.assignment.accountRegistrationAPI.model.CustomerInfo;
import com.assignment.accountRegistrationAPI.model.RegistrationResponse;
import com.assignment.accountRegistrationAPI.service.RegisterService;

@ExtendWith(MockitoExtension.class)

@WebMvcTest(RegisterApiController.class)
public class RegisterApiControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	RegisterService registerService;

	RegisterApiController registerApiController;

	@BeforeEach
	public void setService() {
		MockitoAnnotations.openMocks(this);
		registerApiController = new RegisterApiController(registerService);
	}

	@Test

	@DisplayName("Customer Registeration")
	public void customerRegisterTest() throws Exception {

		MockMultipartFile file = new MockMultipartFile("file", "image/jpeg", "multipart/form-data", "pic".getBytes());
		MockMultipartFile jsonFile = new MockMultipartFile("customer", "", "application/json",
				getCustomerRequest().getBytes());
		RegistrationResponse res = new RegistrationResponse("S.Sanniboena", "gBE3XgRWjZ");
		when(registerService.cutomerRegistration(any(), any())).thenReturn(res);
		mockMvc.perform(multipart("/register").file(file).file(jsonFile)).andExpect(status().isOk())
				.andExpect(jsonPath("$.userName", is("S.Sanniboena")));
	}

	@Test

	@DisplayName("RegisterAPI ratelimitor")
	public void ratelimiterTest() throws Exception {
		MockMultipartFile file = new MockMultipartFile("file", "image/jpeg", "multipart/form-data", "pic".getBytes());
		ResponseEntity<String> res = registerApiController.rigisterRateLimitingFallback(new Customer(), file,
				null);
		assertEquals(res.getBody(), "Too Many Requests - Retry after some time");
	}

	private String getCustomerRequest() {
		String customerReq = """
				{
				"firstName":"Sreenivasulu",
				"lastName": "Sanniboena",
				"userName": "S.Sanniboena",
				"dateOfBirth": "1992-06-12",
				"address": { 
					"street": "437 Lytton", 
					"city":"Palo Alto",
					"state": "CA",
					"country": "Netherlands",
					"zip": "455525" 
						} 
			}
			""";
		return customerReq;
}}
