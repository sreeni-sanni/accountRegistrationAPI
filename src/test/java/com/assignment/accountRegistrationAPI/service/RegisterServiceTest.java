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
import org.springframework.mock.web.MockMultipartFile;

import com.assignment.accountRegistrationAPI.exception.APIException;
import com.assignment.accountRegistrationAPI.model.AccountDetails;
import com.assignment.accountRegistrationAPI.model.Address;
import com.assignment.accountRegistrationAPI.model.Customer;
import com.assignment.accountRegistrationAPI.model.LoginRequest;
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
		MockMultipartFile file = new MockMultipartFile("data", "filename.txt", "text/plain", "some xml".getBytes());
		Customer customer = getCoustomerRequest();
		customer.getAddress().setCountry("Netherlands");
		when(registerRepository.existsByUserName(customer.getUserName())).thenReturn(false);
		when(utils.checkAgeEligibility(customer.getDateOfBirth())).thenReturn(true);
		when(utils.verifyCountry(customer.getAddress().getCountry())).thenReturn(true);
		when(registerRepository.save(any())).thenReturn(customer);
		LoginRequest res = registerService.cutomerRegistration(customer, file);
		assertEquals(res.userName(), customer.getUserName());
		/*
		 * ArgumentCaptor<Customer> captor = ArgumentCaptor.forClass(Customer.class);
		 * verify(registerRepository.save(captor.capture()));
		 * assertEquals(captor.getValue().getAddress().getCountry(), is("Netherlands"));
		 */
		 
	}

	@Test
	@DisplayName("when exsting customer try to register")
	public void extingCustomerRigisterFailedTest() {
		MockMultipartFile file = new MockMultipartFile("data", "filename.txt", "text/plain", "some xml".getBytes());
		Customer cust = getCoustomerRequest();
		cust.getAddress().setCountry("Netherlands");
		when(registerRepository.existsByUserName(any())).thenReturn(true);
		APIException exception = assertThrows(APIException.class,
				() -> registerService.cutomerRegistration(cust, file));
		assertEquals("Username is already exists,Please provide different username", exception.getMessage());
	}

	@Test
	@DisplayName("when customer age age is below 18")
	public void extingCustomerRigisterFailedTest1() {
		MockMultipartFile file = new MockMultipartFile("data", "filename.txt", "text/plain", "some xml".getBytes());
		Customer cust = getCoustomerRequest();
		when(registerRepository.existsByUserName(any())).thenReturn(false);
		when(utils.checkAgeEligibility(any())).thenReturn(false);
		APIException exception = assertThrows(APIException.class,
				() -> registerService.cutomerRegistration(cust, file));
		assertEquals("Age must more than 18 years old", exception.getMessage());
	}

	@Test
	@DisplayName("when customer register from outside of listed country")
	public void customerOutsideOfCountryTest() {
		MockMultipartFile file = new MockMultipartFile("data", "filename.txt", "text/plain", "some xml".getBytes());
		Customer cust = getCoustomerRequest();
		cust.getAddress().setCountry("India");
		when(registerRepository.existsByUserName(any())).thenReturn(false);
		when(utils.checkAgeEligibility(any())).thenReturn(true);
		when(utils.verifyCountry(any())).thenReturn(false);
		APIException exception = assertThrows(APIException.class,
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
		APIException exception = assertThrows(APIException.class,
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

		AccountDetails accDetails = new AccountDetails();
		accDetails.setAccountNumber("NL83ABCD6657861510");
		accDetails.setAccountType("savings");
		accDetails.setBalance(0);
		accDetails.setCurrency("€");
		accDetails.setAccountCreatedTmstp(LocalDate.now());
		customer.setAccountInfo(accDetails);
		customer.setAddress(addrs);
		return customer;
	}
}
