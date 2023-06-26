package com.assignment.accountRegistrationAPI.api;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.assignment.accountRegistrationAPI.model.AccountDetails;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@jakarta.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-06-21T18:55:23.939541042Z[GMT]")
@Validated
public interface AccountOverviewApi {

	@Operation(summary = "Account info overview", description = "Account information", tags = { "Overview" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AccountDetails.class))),
			@ApiResponse(responseCode = "400", description = "Invalid customer id supplied") })
	@RequestMapping(value = "/overview", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.GET)
	ResponseEntity<AccountDetails> overviewPost(
			@Parameter(in = ParameterIn.DEFAULT, description = "Customer can create new account", required = true, schema = @Schema()) @Valid @RequestParam("customerId") String customerId);

}
