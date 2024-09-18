package dev.makos.cards.controller;

import dev.makos.cards.config.properties.CardContactInfo;
import dev.makos.cards.constants.CardConstants;
import dev.makos.cards.domain.dto.CardDto;
import dev.makos.cards.domain.dto.ErrorResponseDto;
import dev.makos.cards.domain.dto.ResponseDto;
import dev.makos.cards.service.CardService;
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

@Tag(name = "Card REST API", description = "APIs for managing cards")
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/cards", produces = {MediaType.APPLICATION_JSON_VALUE})
@RestController
public class CardController {

    private final CardService cardService;
    private final Environment environment;
    private final CardContactInfo cardContactInfo;

    @Value("${build.version}")
    private String buildVersion;

    @Operation(summary = "Card REST API", description = "APIs for managing cards")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "HTTP Status CREATED"),
            @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PostMapping
    public ResponseEntity<ResponseDto> createCard(
            @Pattern(regexp = "^\\d{10}$", message = "Mobile number should be 10 digits")
            @Valid @RequestParam String mobileNumber) {
        cardService.create(mobileNumber);
        String statusCode = String.valueOf(HttpStatus.CREATED.value());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(statusCode, CardConstants.MESSAGE_201));
    }

    @Operation(summary = "Fetch Card Details REST API", description = "REST API to fetch card details based on a mobile number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
            @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping
    public ResponseEntity<CardDto> fetchCardDetails(
            @Pattern(regexp = "^\\d{10}$", message = "Mobile number should be 10 digits")
            @RequestParam String mobileNumber) {
        CardDto cardDto = cardService.fetch(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(cardDto);
    }

    @Operation(summary = "Update Card Details REST API", description = "REST API to update card details based on a card number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
            @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PutMapping
    public ResponseEntity<ResponseDto> updateCardDetails(@Valid @RequestBody CardDto cardDto) {
        cardService.update(cardDto);
        String statusCode = String.valueOf(HttpStatus.OK.value());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(statusCode, CardConstants.MESSAGE_200));
    }

    @Operation(summary = "Delete Card Details REST API", description = "REST API to delete Card details based on a mobile number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
            @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @DeleteMapping
    public ResponseEntity<ResponseDto> deleteCardDetails(
            @Pattern(regexp = "^\\d{10}$", message = "Mobile number should be 10 digits")
            @RequestParam String mobileNumber) {
        cardService.delete(mobileNumber);
        String statusCode = String.valueOf(HttpStatus.OK.value());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(statusCode, CardConstants.MESSAGE_200));
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
    public ResponseEntity<CardContactInfo> getContacts() {
        return ResponseEntity.status(HttpStatus.OK).body(cardContactInfo);
    }
}
