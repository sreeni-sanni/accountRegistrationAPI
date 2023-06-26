package com.assignment.accountRegistrationAPI.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.assignment.accountRegistrationAPI.api.RegisterApi;
import com.assignment.accountRegistrationAPI.model.Customer;
import com.assignment.accountRegistrationAPI.model.LoginRequest;
import com.assignment.accountRegistrationAPI.service.RegisterService;

import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class RegisterApiController implements RegisterApi {

	private final RegisterService registerService;

	public RegisterApiController(RegisterService registerService) {
		this.registerService = registerService;
	}

	@Override
	@RateLimiter(name = "accountRateLimiterAPI", fallbackMethod = "rigisterRateLimitingFallback")
	public ResponseEntity<LoginRequest> register(@Valid Customer customer, MultipartFile file) {
		return new ResponseEntity<>(registerService.cutomerRegistration(customer, file), HttpStatus.OK);
	}

	public ResponseEntity<String> rigisterRateLimitingFallback(Customer customer,MultipartFile file, RequestNotPermitted exception) {
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Retry-After", "1s");
		return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).headers(responseHeaders)
				.body("Too Many Requests - Retry after some time");
	}

}
