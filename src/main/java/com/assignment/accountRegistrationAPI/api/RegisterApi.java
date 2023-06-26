package com.assignment.accountRegistrationAPI.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.assignment.accountRegistrationAPI.model.Customer;
import com.assignment.accountRegistrationAPI.model.LoginRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

public interface RegisterApi {

	@Operation(summary = "Register new account", description = "Customer can create new account", tags = { "Register" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginRequest.class))),
			@ApiResponse(responseCode = "400", description = "Invalid data supplied") })
	@RequestMapping(value = "/register", produces = { "application/json" }, consumes = { "application/json",
			"multipart/form-data" }, method = RequestMethod.POST)
	ResponseEntity<LoginRequest> register(
			@Parameter(in = ParameterIn.DEFAULT, description = "Customer can create new account", required = true, schema = @Schema()) @Valid @RequestPart Customer customer,
			@RequestPart("file") MultipartFile file);

}
