package dev.makos.accounts.controller;

import dev.makos.accounts.constants.AccountConstant;
import dev.makos.accounts.domain.dto.CustomerDto;
import dev.makos.accounts.domain.dto.ResponseDto;
import dev.makos.accounts.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/accounts", produces = {MediaType.APPLICATION_JSON_VALUE})
@RestController
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<ResponseDto> create(@RequestBody CustomerDto customerDto) {
        accountService.createAccount(customerDto);
        String statusCode = String.valueOf(HttpStatus.CREATED.value());
        ResponseDto responseDto = new ResponseDto(statusCode, AccountConstant.MESSAGE_201);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping
    public ResponseEntity<CustomerDto> fetch(@RequestParam String mobileNumber) {
        CustomerDto customerDto = accountService.fetchAccount(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(customerDto);
    }

    @PutMapping
    public ResponseEntity<ResponseDto> update(@RequestBody CustomerDto customerDto) {
        accountService.updateAccount(customerDto);
        String statusCode = String.valueOf(HttpStatus.OK.value());
        ResponseDto responseDto = new ResponseDto(statusCode, AccountConstant.MESSAGE_200);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @DeleteMapping
    public ResponseEntity<ResponseDto> delete(@RequestParam String mobileNumber) {
        accountService.deleteByMobileNumber(mobileNumber);
        String statusCode = String.valueOf(HttpStatus.OK.value());
        ResponseDto responseDto = new ResponseDto(statusCode, AccountConstant.MESSAGE_200);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
}
