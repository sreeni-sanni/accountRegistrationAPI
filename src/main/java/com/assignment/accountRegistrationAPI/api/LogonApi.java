package com.assignment.accountRegistrationAPI.api;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.assignment.accountRegistrationAPI.model.LoginRequest;
import com.assignment.accountRegistrationAPI.model.LoginResponse;

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
public interface LogonApi {

    @Operation(summary = "Logon", description = "Customer logon into online system", tags={ "Logon" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
        
        @ApiResponse(responseCode = "400", description = "Invalid username/password supplied") })
    @RequestMapping(value = "/logon",
        produces = { "application/json" }, 
        consumes = { "application/json" }, 
        method = RequestMethod.POST)
	ResponseEntity<LoginResponse> logon(
			@Parameter(in = ParameterIn.DEFAULT, description = "Customer logon details", required = true, schema = @Schema())  @Valid  @RequestBody LoginRequest LoginInfoRequest);

}

