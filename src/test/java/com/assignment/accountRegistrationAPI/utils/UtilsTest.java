package com.assignment.accountRegistrationAPI.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.assignment.accountRegistrationAPI.exception.AccountRegistrationAPIException;
import com.assignment.accountRegistrationAPI.utils.Utils;

@ExtendWith(MockitoExtension.class)
public class UtilsTest {
	
	Utils Utils;
	
	@BeforeEach
	public void setService() {
		MockitoAnnotations.openMocks(this);
		Utils = new Utils();
	}

	@Test
	@DisplayName("Random password generation")
	public void passwordGenerationTest() throws Exception {
		String key=Utils.generatePassword();
		assertEquals(key.length(),10);
	}
	
	@Test
	@DisplayName("Iban number generation")
	public void generatorIbanTest() throws Exception {
		String iban=Utils.generatorIbanNumber("Netherlands");
		assertEquals(iban.substring(0, 2),"NL");
	}
	
	@Test
	@DisplayName("Iban number generation Exception")
	public void generatorIbanExceptionTest() throws Exception {
		AccountRegistrationAPIException exception = assertThrows(AccountRegistrationAPIException.class,
				() -> Utils.generatorIbanNumber("India"));
		assertEquals("Requested country is not allowed", exception.getMessage());
	}
	
	@Test
	@DisplayName("check age above 18 years old true")
	public void checkAgeEligibilityValidTest() throws Exception {
		Boolean isvalid=Utils.checkAgeEligibility(LocalDate.of(1992, 06, 12));
		assertEquals(isvalid,true);
	}
	
	@Test
	@DisplayName("check age above 18 years old false")
	public void checkAgeNoEligibilityTest() throws Exception {
		Boolean isvalid=Utils.checkAgeEligibility(LocalDate.of(2006, 06, 12));
		assertEquals(isvalid,false);
	}

}
