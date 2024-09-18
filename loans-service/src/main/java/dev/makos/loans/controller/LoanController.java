package dev.makos.loans.controller;

import dev.makos.loans.config.properties.LoanContactInfo;
import dev.makos.loans.constants.LoanConstants;
import dev.makos.loans.domain.dto.ErrorResponseDto;
import dev.makos.loans.domain.dto.LoanDto;
import dev.makos.loans.domain.dto.ResponseDto;
import dev.makos.loans.service.LoanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Loan REST API", description = "APIs for managing loan")
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/loans", produces = {MediaType.APPLICATION_JSON_VALUE})
@RestController
public class LoanController {

    private final LoanService loanService;
    private final Environment environment;
    private final LoanContactInfo loanContactInfo;

    @Value("${build.version}")
    private String buildVersion;

    @Operation(summary = "Create Loan REST API", description = "REST API to create new loan")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "HTTP Status CREATED"),
            @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PostMapping
    public ResponseEntity<ResponseDto> createLoan(
            @Pattern(regexp = "^\\d{10}$", message = "Mobile number should be 10 digits")
            @RequestParam String mobileNumber) {
        loanService.create(mobileNumber);
        String statusCode = String.valueOf(HttpStatus.CREATED.value());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(statusCode, LoanConstants.MESSAGE_201));
    }

    @Operation(summary = "Fetch Loan Details REST API", description = "REST API to fetch loan details based on a mobile number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
            @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping
    public ResponseEntity<LoanDto> fetchLoanDetails(
            @Pattern(regexp = "^\\d{10}$", message = "Mobile number should be 10 digits")
            @RequestParam String mobileNumber) {
        LoanDto loanDto = loanService.fetch(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(loanDto);
    }

    @Operation(summary = "Update Loan Details REST API", description = "REST API to update loan details based on a loan number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
            @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PutMapping
    public ResponseEntity<ResponseDto> updateLoanDetails(@Valid @RequestBody LoanDto loanDto) {
        loanService.update(loanDto);
        String statusCode = String.valueOf(HttpStatus.OK.value());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(statusCode, LoanConstants.MESSAGE_200));
    }

    @Operation(summary = "Delete Loan Details REST API", description = "REST API to delete Loan details based on a mobile number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
            @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @DeleteMapping
    public ResponseEntity<ResponseDto> deleteLoanDetails(
            @Pattern(regexp = "^\\d{10}$", message = "Mobile number should be 10 digits")
            @RequestParam String mobileNumber) {
        loanService.delete(mobileNumber);
        String statusCode = String.valueOf(HttpStatus.OK.value());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(statusCode, LoanConstants.MESSAGE_200));
    }    @Operation(summary = "Get Build Version", description = "Get the build version of the application")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "HTTP status OK"),
            @ApiResponse(responseCode = "500", description = "HTTP status Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })

    @GetMapping("/build-info")
    public ResponseEntity<String> getBuildVersion() {
        return ResponseEntity.status(HttpStatus.OK).body(buildVersion);
    }

    @Operation(summary = "Get Java Version", description = "Get the Java version of the application")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "HTTP status OK"),
            @ApiResponse(responseCode = "500", description = "HTTP status Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/java-version")
    public ResponseEntity<String> getJavaVersion() {
        return ResponseEntity.status(HttpStatus.OK).body(environment.getProperty("java.specification.version"));
    }

    @Operation(summary = "Get Contacts", description = "Get the contacts of the application")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "HTTP status OK"),
            @ApiResponse(responseCode = "500", description = "HTTP status Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/contacts")
    public ResponseEntity<LoanContactInfo> getContacts() {
        return ResponseEntity.status(HttpStatus.OK).body(loanContactInfo);
    }
}
