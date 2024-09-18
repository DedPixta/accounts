package dev.makos.accounts.controller;

import dev.makos.accounts.constants.AccountConstant;
import dev.makos.accounts.config.properties.AccountContactInfo;
import dev.makos.accounts.domain.dto.CustomerDto;
import dev.makos.accounts.domain.dto.ErrorResponseDto;
import dev.makos.accounts.domain.dto.ResponseDto;
import dev.makos.accounts.service.AccountService;
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

@Tag(name = "Account REST API", description = "APIs for managing accounts")
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/accounts", produces = {MediaType.APPLICATION_JSON_VALUE})
@RestController
public class AccountController {

    private final AccountService accountService;
    private final Environment environment;
    private final AccountContactInfo accountContactInfo;

    @Value("${build.version}")
    private String buildVersion;



    @Operation(summary = "Create Account", description = "Create new Customer & Account")
    @ApiResponse(responseCode = "201", description = "HTTP status Created")
    @PostMapping
    public ResponseEntity<ResponseDto> create(@Valid @RequestBody CustomerDto customerDto) {
        accountService.createAccount(customerDto);
        String statusCode = String.valueOf(HttpStatus.CREATED.value());
        ResponseDto responseDto = new ResponseDto(statusCode, AccountConstant.MESSAGE_201);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @Operation(summary = "Fetch Account", description = "Fetch Customer & Account details by mobileNumber")
    @ApiResponse(responseCode = "200", description = "HTTP status OK")
    @GetMapping
    public ResponseEntity<CustomerDto> fetch(
            @Pattern(regexp = "^\\d{10}$", message = "Mobile number should be 10 digits")
            @RequestParam String mobileNumber) {
        CustomerDto customerDto = accountService.fetchAccount(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(customerDto);
    }

    @Operation(summary = "Update Account", description = "Update Customer & Account details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "HTTP status OK"),
            @ApiResponse(responseCode = "500", description = "HTTP status Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PutMapping
    public ResponseEntity<ResponseDto> update(@Valid @RequestBody CustomerDto customerDto) {
        accountService.updateAccount(customerDto);
        String statusCode = String.valueOf(HttpStatus.OK.value());
        ResponseDto responseDto = new ResponseDto(statusCode, AccountConstant.MESSAGE_200);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @Operation(summary = "Delete Account", description = "Delete Customer & Account by mobileNumber")
    @ApiResponse(responseCode = "200", description = "HTTP status OK")
    @DeleteMapping
    public ResponseEntity<ResponseDto> delete(
            @Pattern(regexp = "^\\d{10}$", message = "Mobile number should be 10 digits")
            @RequestParam String mobileNumber) {
        accountService.deleteByMobileNumber(mobileNumber);
        String statusCode = String.valueOf(HttpStatus.OK.value());
        ResponseDto responseDto = new ResponseDto(statusCode, AccountConstant.MESSAGE_200);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }



    @Operation(summary = "Get Build Version", description = "Get the build version of the application")
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
    public ResponseEntity<AccountContactInfo> getContacts() {
        return ResponseEntity.status(HttpStatus.OK).body(accountContactInfo);
    }
}
