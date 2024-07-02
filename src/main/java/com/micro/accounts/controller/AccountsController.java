package com.micro.accounts.controller;

import com.micro.accounts.dto.CustomerDto;
import com.micro.accounts.dto.ErrorResponseDto;
import com.micro.accounts.dto.ResponseDto;
import com.micro.accounts.service.AccountsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.micro.accounts.constants.AccountsConstants.*;

@Tag(
        name = "CRUD REST API for Accounts in Bank services.",
        description = "APIs to CREATE, UPDATE, GET and DELETE operations."
)
@RestController
@RequestMapping(path = "/api/v1/accounts", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Validated
public class AccountsController {

    private final AccountsService accountsService;

    @Operation(
            summary = "Create Account REST API",
            description = "REST API to create a new Customer and Account inside Bank application"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP status CREATED"
    )
    @PostMapping
    public ResponseEntity<ResponseDto> createAccount(@RequestBody @Valid CustomerDto customerDto) {
        accountsService.createAccount(customerDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(STATUS_201, MESSAGE_201));
    }

    @Operation(
            summary = "GET Account REST API",
            description = "REST API to fetch a Customer and Account details for Bank application"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP status OK"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "HTTP status NOT_FOUND",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping
    public ResponseEntity<CustomerDto> getAccountDetails(@RequestParam
                                                         @Pattern(regexp = "[\\d]{12}", message = "mobileNumber should be 12 digits")
                                                         String mobileNumber) {
        CustomerDto customerDto = accountsService.fetchAccount(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerDto);
    }

    @Operation(
            summary = "UPDATE Account REST API",
            description = "REST API to update Customer and Account inside Bank application"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "HTTP status FAILED",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @PutMapping
    public ResponseEntity<ResponseDto> updateCustomerDetails(@RequestBody @Valid CustomerDto customerDto) {
        boolean isUpdated = accountsService.updateCustomer(customerDto);
        HttpStatus status = isUpdated ? HttpStatus.OK : HttpStatus.EXPECTATION_FAILED;
        String message = isUpdated ? MESSAGE_200 : MESSAGE_417_UPDATE;
        return ResponseEntity
                .status(status)
                .body(new ResponseDto(status.toString(), message));
    }

    @Operation(
            summary = "DELETE Account REST API",
            description = "REST API to delete Customer and Account inside Bank application"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP status OK"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "HTTP status NOT_FOUND",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @DeleteMapping
    public ResponseEntity<ResponseDto> deleteCustomer(@RequestBody @Valid CustomerDto customerDto) {
        accountsService.deleteCustomer(customerDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(HttpStatus.OK.toString(), MESSAGE_200));
    }
}