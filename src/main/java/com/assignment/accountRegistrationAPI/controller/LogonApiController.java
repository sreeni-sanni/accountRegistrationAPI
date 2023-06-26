package com.assignment.accountRegistrationAPI.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.accountRegistrationAPI.api.LogonApi;
import com.assignment.accountRegistrationAPI.model.LoginRequest;
import com.assignment.accountRegistrationAPI.model.LoginResponse;
import com.assignment.accountRegistrationAPI.service.LogonService;

import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class LogonApiController implements LogonApi {

	private final LogonService logonService;

	public LogonApiController(LogonService logonService) {
		this.logonService = logonService;
	}

	@Override
	@RateLimiter(name = "accountRateLimiterAPI", fallbackMethod = "logonRateLimitingFallback")
	public ResponseEntity<LoginResponse> logon(@Valid LoginRequest LoginInfoRequest) {
		return new ResponseEntity<>(logonService.logonCustomer(LoginInfoRequest), HttpStatus.OK);
	}

	public ResponseEntity<String> logonRateLimitingFallback(LoginRequest LoginInfoRequest ,RequestNotPermitted exception) {
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Retry-After", "1s");
		return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).headers(responseHeaders)
				.body("Too Many Requests - Retry after some time");
	}

}
