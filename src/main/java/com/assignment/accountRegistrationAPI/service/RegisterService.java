package com.assignment.accountRegistrationAPI.service;

import java.io.IOException;
import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.assignment.accountRegistrationAPI.exception.APIException;
import com.assignment.accountRegistrationAPI.model.AccountDetails;
import com.assignment.accountRegistrationAPI.model.Customer;
import com.assignment.accountRegistrationAPI.model.CustomerIdentificationFile;
import com.assignment.accountRegistrationAPI.model.LoginRequest;
import com.assignment.accountRegistrationAPI.repository.RegisterRepository;
import com.assignment.accountRegistrationAPI.utils.Utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RegisterService {

	private final RegisterRepository registerRepository;
	private final Utils utils;

	public RegisterService(RegisterRepository registerRepository, Utils utils) {
		this.registerRepository = registerRepository;
		this.utils = utils;
	}

	public LoginRequest cutomerRegistration(Customer customerReq, MultipartFile file) throws APIException {

		if (registerRepository.existsByUserName(customerReq.getUserName()))
			throw new APIException("Username is already exists,Please provide different username");
		
		if (!utils.checkAgeEligibility(customerReq.getDateOfBirth()))
			throw new APIException("Age must more than 18 years old");

		if (!utils.verifyCountry(customerReq.getAddress().getCountry()))
			throw new APIException("Customer is not belongs to allowed countries");

		if (ObjectUtils.isEmpty(file) || file.isEmpty())
			throw new APIException("Please provide valid identity document");

		retrieveIdentificationDoc(customerReq, file);
		createAccount(customerReq);
		customerReq.setPassword(utils.generatePassword());
		Customer registeredCust=registerRepository.save(customerReq);
		return new LoginRequest(registeredCust.getUserName(), registeredCust.getPassword());
	}

	private void retrieveIdentificationDoc(Customer customerReq, MultipartFile file) {
		try {
			CustomerIdentificationFile custIdentificationFile = new CustomerIdentificationFile();
			custIdentificationFile.setName(file.getOriginalFilename());
			custIdentificationFile.setType(file.getContentType());
			custIdentificationFile.setData(file.getBytes());
			customerReq.setCustomerIdentificationFile(custIdentificationFile);
		} catch (IOException e) {
			throw new APIException(e.getMessage());
		}
	}

	private void createAccount(Customer customerReq) {

		AccountDetails accountInfo = new AccountDetails();
		accountInfo.setAccountNumber(utils.generatorIbanNumber(customerReq.getAddress().getCountry()));
		accountInfo.setAccountType("Savings");
		accountInfo.setBalance(0);
		accountInfo.setCurrency("€");
		accountInfo.setAccountCreatedTmstp(LocalDate.now());
		customerReq.setAccountInfo(accountInfo);
	}

}
