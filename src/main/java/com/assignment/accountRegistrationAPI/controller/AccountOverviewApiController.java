package com.assignment.accountRegistrationAPI.controller;

import org.openapitools.client.model.AccountInfo;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.accountRegistrationAPI.entity.AccountDetails;
import com.assignment.accountRegistrationAPI.service.AccountDetailsService;

import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class AccountOverviewApiController {

	private final AccountDetailsService accountDetailsService;

	public AccountOverviewApiController(AccountDetailsService accountDetailsService) {
		this.accountDetailsService = accountDetailsService;
	}

	@GetMapping(value = "/overview", produces = { "application/json" }, consumes = { "application/json" })
	@RateLimiter(name = "accountRateLimiterAPI", fallbackMethod = "accountRateLimitingFallback")
	public ResponseEntity<AccountInfo> overview(@Valid @RequestParam("customerId") String customerId) {
		return new ResponseEntity<>(accountDetailsService.getAccountDetails(customerId), HttpStatus.OK);
	}

	public ResponseEntity<String> accountRateLimitingFallback(String customerId, RequestNotPermitted exception) {
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Retry-After", "1s");
		return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).headers(responseHeaders)
				.body("Too Many Requests - Retry after some time");
	}
}
