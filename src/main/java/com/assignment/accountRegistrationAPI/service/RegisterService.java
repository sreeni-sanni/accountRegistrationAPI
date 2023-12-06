package com.assignment.accountRegistrationAPI.service;

import java.io.IOException;
import java.time.LocalDateTime;

import org.openapitools.client.model.Customer;
import org.openapitools.client.model.RegisterResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.assignment.accountRegistrationAPI.entity.AccountDetails;
import com.assignment.accountRegistrationAPI.entity.Address;
import com.assignment.accountRegistrationAPI.entity.CustomerIdentificationFile;
import com.assignment.accountRegistrationAPI.entity.CustomerInfo;
import com.assignment.accountRegistrationAPI.exception.AccountRegistrationAPIException;
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

	/**
	 * This method will Register Customer bases customer request It will do
	 * validations against user request -> customer useeName should be unique ->
	 * customer age must be more than 18 years -> customer should be part of allowed
	 * countries -> customer should provide valid identification document
	 * 
	 * @param customerReq
	 * @param file
	 * @return
	 * @throws AccountRegistrationAPIException
	 */
	public RegisterResponse cutomerRegistration(Customer customerReq, MultipartFile file)
			throws AccountRegistrationAPIException {

		if (registerRepository.existsByUserName(customerReq.getUserName()))
			throw new AccountRegistrationAPIException("Username already exists,Please provide different username");

		if (!utils.checkAgeEligibility(customerReq.getDateOfBirth()))
			throw new AccountRegistrationAPIException("Age must be more than 18 years old");

		if (!utils.verifyCountry(customerReq.getAddress().getCountry()))
			throw new AccountRegistrationAPIException("Customer does not belongs to allowed countries");

		if (ObjectUtils.isEmpty(file) || file.isEmpty())
			throw new AccountRegistrationAPIException("Please provide valid identity document");

		CustomerInfo registeredCust = registerRepository.save(createCustomer(customerReq, file));
		RegisterResponse rResponse = new RegisterResponse();
		rResponse.setUserName(registeredCust.getUserName());
		rResponse.setPassword(registeredCust.getPassword());
		return rResponse;
	}

	private CustomerInfo createCustomer(Customer customerReq, MultipartFile file) {
		CustomerInfo entiry = new CustomerInfo();
		entiry.setFirstName(customerReq.getFirstName());
		entiry.setLastName(customerReq.getLastName());
		entiry.setUserName(customerReq.getUserName());
		entiry.setDateOfBirth(customerReq.getDateOfBirth());
		entiry.setPassword(utils.generatePassword());
		getAddress(entiry,customerReq);
		createAccount(entiry);
		retrieveIdentificationDoc(entiry, file);
		return entiry;

	}
	
	private void getAddress(CustomerInfo entiry,Customer customerReq) {
		Address addrs=new Address();
		addrs.setCity(customerReq.getAddress().getCity());
		addrs.setCountry(customerReq.getAddress().getCountry());
		addrs.setState(customerReq.getAddress().getState());
		addrs.setStreet(customerReq.getAddress().getStreet());
		addrs.setZip(customerReq.getAddress().getZip());
		entiry.setAddress(addrs);
	}

	
	private void retrieveIdentificationDoc(CustomerInfo customerReq, MultipartFile file) {
		try {
			CustomerIdentificationFile custIdentificationFile = new CustomerIdentificationFile();
			custIdentificationFile.setName(file.getOriginalFilename());
			custIdentificationFile.setType(file.getContentType());
			custIdentificationFile.setData(file.getBytes());
			customerReq.setCustomerIdentificationFile(custIdentificationFile);
		} catch (IOException e) {
			throw new AccountRegistrationAPIException(e.getMessage());
		}
	}

	
	private void createAccount(CustomerInfo customerReq) {

		AccountDetails accountInfo = new AccountDetails();
		accountInfo.setAccountNumber(utils.generatorIbanNumber(customerReq.getAddress().getCountry()));
		accountInfo.setAccountType("Savings");
		accountInfo.setBalance(0);
		accountInfo.setCurrency("€");
		accountInfo.setAccountCreatedTmstp(LocalDateTime.now());
		customerReq.setAccountInfo(accountInfo);
	}

}
