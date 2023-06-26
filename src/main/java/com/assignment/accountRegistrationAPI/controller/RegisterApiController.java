package com.assignment.accountRegistrationAPI.controller;

import org.openapitools.client.model.Customer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.assignment.accountRegistrationAPI.model.CustomerInfo;
import com.assignment.accountRegistrationAPI.model.RegistrationResponse;
import com.assignment.accountRegistrationAPI.service.RegisterService;

import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class RegisterApiController {

	private final RegisterService registerService;

	public RegisterApiController(RegisterService registerService) {
		this.registerService = registerService;
	}

	@PostMapping(value = "/register", produces = { "application/json" }, consumes = { "application/json","multipart/form-data" })
	@RateLimiter(name = "accountRateLimiterAPI", fallbackMethod = "rigisterRateLimitingFallback")
	public ResponseEntity<RegistrationResponse> register(@Valid @RequestPart Customer customer, MultipartFile file) {
		return new ResponseEntity<>(registerService.cutomerRegistration(customer, file), HttpStatus.OK);
	}

	public ResponseEntity<String> rigisterRateLimitingFallback(Customer customer, MultipartFile file,
			RequestNotPermitted exception) {
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Retry-After", "1s");
		return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).headers(responseHeaders)
				.body("Too Many Requests - Retry after some time");
	}

}
