package com.assignment.accountRegistrationAPI.controller;

import org.openapitools.client.model.LoginInfo;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.accountRegistrationAPI.model.RegistrationResponse;
import com.assignment.accountRegistrationAPI.model.LoginResponse;
import com.assignment.accountRegistrationAPI.service.LogonService;

import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class LogonApiController {

	private final LogonService logonService;

	public LogonApiController(LogonService logonService) {
		this.logonService = logonService;
	}

	@PostMapping(value = "/logon", produces = { "application/json" }, consumes = { "application/json" })
	@RateLimiter(name = "accountRateLimiterAPI", fallbackMethod = "logonRateLimitingFallback")
	public ResponseEntity<LoginResponse> logon(@Valid @RequestBody LoginInfo LoginInfoRequest) {
		return new ResponseEntity<>(logonService.logonCustomer(LoginInfoRequest), HttpStatus.OK);
	}

	public ResponseEntity<String> logonRateLimitingFallback(LoginInfo registrationResponse,
			RequestNotPermitted exception) {
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Retry-After", "1s");
		return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).headers(responseHeaders)
				.body("Too Many Requests - Retry after some time");
	}

}
