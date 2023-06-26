package com.assignment.accountRegistrationAPI.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.client.model.Address;
import org.openapitools.client.model.Customer;
import org.openapitools.client.model.RegisterResponse;
import org.springframework.mock.web.MockMultipartFile;

import com.assignment.accountRegistrationAPI.entity.CustomerInfo;
import com.assignment.accountRegistrationAPI.exception.AccountRegistrationAPIException;
import com.assignment.accountRegistrationAPI.repository.RegisterRepository;
import com.assignment.accountRegistrationAPI.utils.Utils;

@ExtendWith(MockitoExtension.class)
public class RegisterServiceTest {

	RegisterService registerService;

	@Mock
	RegisterRepository registerRepository;

	@Mock
	Utils utils;

	@BeforeEach
	public void setService() {
		MockitoAnnotations.openMocks(this);
		registerService = new RegisterService(registerRepository, utils);
	}

	@Test
	@DisplayName("when new customer registration")
	public void customerRigisterTest() {
		MockMultipartFile file = new MockMultipartFile("file", "image/jpeg", "multipart/form-data", "pic".getBytes());
		Customer customer = getCoustomerRequest();
		customer.getAddress().setCity("Netherlands");
		CustomerInfo custEntity=new CustomerInfo();
		custEntity.setUserName("S.Sanniboena");
		when(registerRepository.existsByUserName(customer.getUserName())).thenReturn(false);
		when(utils.checkAgeEligibility(customer.getDateOfBirth())).thenReturn(true);
		when(utils.verifyCountry(customer.getAddress().getCountry())).thenReturn(true);
		when(registerRepository.save(any(CustomerInfo.class))).thenReturn(custEntity);
		RegisterResponse res = registerService.cutomerRegistration(customer, file);
		assertEquals(res.getUserName(), customer.getUserName());
	}

	@Test
	@DisplayName("when exsting customer try to register")
	public void extingCustomerRigisterFailedTest() {
		MockMultipartFile file = new MockMultipartFile("file", "image/jpeg", "multipart/form-data", "pic".getBytes());
		Customer cust = getCoustomerRequest();
		cust.getAddress().setCountry("Netherlands");
		when(registerRepository.existsByUserName(any())).thenReturn(true);
		AccountRegistrationAPIException exception = assertThrows(AccountRegistrationAPIException.class,
				() -> registerService.cutomerRegistration(cust, file));
		assertEquals("Username is already exists,Please provide different username", exception.getMessage());
	}

	@Test
	@DisplayName("when customer age age is below 18")
	public void extingCustomerRigisterFailedTest1() {
		MockMultipartFile file = new MockMultipartFile("file", "image/jpeg", "multipart/form-data", "pic".getBytes());
		Customer cust = getCoustomerRequest();
		when(registerRepository.existsByUserName(any())).thenReturn(false);
		when(utils.checkAgeEligibility(any())).thenReturn(false);
		AccountRegistrationAPIException exception = assertThrows(AccountRegistrationAPIException.class,
				() -> registerService.cutomerRegistration(cust, file));
		assertEquals("Age must more than 18 years old", exception.getMessage());
	}

	@Test
	@DisplayName("when customer register from outside of listed country")
	public void customerOutsideOfCountryTest() {
		MockMultipartFile file = new MockMultipartFile("file", "image/jpeg", "multipart/form-data", "pic".getBytes());
		Customer cust = getCoustomerRequest();
		cust.getAddress().setCountry("India");
		when(registerRepository.existsByUserName(any())).thenReturn(false);
		when(utils.checkAgeEligibility(any())).thenReturn(true);
		when(utils.verifyCountry(any())).thenReturn(false);
		AccountRegistrationAPIException exception = assertThrows(AccountRegistrationAPIException.class,
				() -> registerService.cutomerRegistration(cust, file));
		assertEquals("Customer is not belongs to allowed countries", exception.getMessage());
	}
	
	@Test
	@DisplayName("when customer upload identity file")
	public void customeridentityFileTest() {
		Customer cust = getCoustomerRequest();
		cust.getAddress().setCountry("India");
		when(registerRepository.existsByUserName(any())).thenReturn(false);
		when(utils.checkAgeEligibility(any())).thenReturn(true);
		when(utils.verifyCountry(any())).thenReturn(true);
		AccountRegistrationAPIException exception = assertThrows(AccountRegistrationAPIException.class,
				() -> registerService.cutomerRegistration(cust, null));
		assertEquals("Please provide valid identity document", exception.getMessage());
	}

	private Customer getCoustomerRequest() {
		Customer customer = new Customer();
		customer.setFirstName("sreenivasulu");
		customer.setLastName("sanniboena");
		customer.setUserName("S.Sanniboena");
		customer.setDateOfBirth(LocalDate.of(2000, 06, 23));

		Address addrs = new Address();
		addrs.setCity("Amstradam");
		addrs.setState("North Holland");
		addrs.setStreet("437 Lytton");
		addrs.setZip("94301");
		customer.setAddress(addrs);
		return customer;
	}
}
