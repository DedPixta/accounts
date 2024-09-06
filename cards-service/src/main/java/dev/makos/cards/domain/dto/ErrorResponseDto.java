package dev.makos.cards.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(name = "ErrorResponse", description = "Error response details to hold error information")
public class ErrorResponseDto {

    @Schema(description = "API path that caused the error", example = "uri=/api/v1/accounts")
    private String apiPath;

    @Schema(description = "HTTP method that caused the error", example = "POST")
    private String httpMethod;

    @Schema(description = "HTTP error code", example = "400")
    private HttpStatus errorCode;

    @Schema(description = "HTTP error message", example = "Something went wrong")
    private String errorMessage;

    @Schema(description = "Error time", example = "2024-09-05T14:24:16.198726")
    private LocalDateTime errorTime;
}
