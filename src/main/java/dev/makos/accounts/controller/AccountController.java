package dev.makos.accounts.controller;

import dev.makos.accounts.constants.AccountConstant;
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
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Account Controller", description = "APIs for managing accounts")
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/accounts", produces = {MediaType.APPLICATION_JSON_VALUE})
@RestController
public class AccountController {

    private final AccountService accountService;

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
    public ResponseEntity<CustomerDto> fetch(@RequestParam String mobileNumber) {
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
    public ResponseEntity<ResponseDto> delete(@RequestParam String mobileNumber) {
        accountService.deleteByMobileNumber(mobileNumber);
        String statusCode = String.valueOf(HttpStatus.OK.value());
        ResponseDto responseDto = new ResponseDto(statusCode, AccountConstant.MESSAGE_200);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
}
