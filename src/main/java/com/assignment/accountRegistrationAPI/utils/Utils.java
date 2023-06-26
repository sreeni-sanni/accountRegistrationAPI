package com.assignment.accountRegistrationAPI.utils;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.iban4j.CountryCode;
import org.iban4j.Iban;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.assignment.accountRegistrationAPI.exception.AccountRegistrationAPIException;

@Component
public class Utils {

	@Value("${countries}")
	private List<String> countryList;

	public Boolean verifyCountry(String countryName) {
		return countryList.stream().anyMatch(s -> s.equalsIgnoreCase(countryName));
	}

	public Boolean checkAgeEligibility(LocalDate birthDate) {
		return Period.between(birthDate, LocalDate.now()).getYears() > 18;
	}

	/**
	 * Method will generator Iban number based on country
	 * @param countryName
	 * @return
	 */
	public String generatorIbanNumber(String countryName) {
		Iban iban = null;
		switch (countryName) {
		case "Netherlands", "Belgium", "Germany" ->
			iban = new Iban.Builder().countryCode(CountryCode.NL).bankCode("ABCD").buildRandom();
		/*
		 * case "Belgium" -> iban = new
		 * Iban.Builder().countryCode(CountryCode.BE).bankCode("123").buildRandom();
		 * case "Germany" -> iban = new
		 * Iban.Builder().countryCode(CountryCode.DE).bankCode("12345678").buildRandom()
		 * ;
		 */
		default -> throw new AccountRegistrationAPIException("Requested country is not allowed");
		}
		return iban.toString();
	}

	/**
	 * @return
	 */
	public String generatePassword() {
		String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String num = "0123456789";
		String specialChar = "!@#%";
		String combination = upper + upper.toLowerCase() + num + specialChar;
		int len = 10;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < len; i++) {
			sb.append(combination.charAt(ThreadLocalRandom.current().nextInt(combination.length())));
		}
		return sb.toString();
	}

}
