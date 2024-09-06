package dev.makos.cards.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
@Schema(name = "Response", description = "Response details to hold successful response information")
public class ResponseDto {

    @Schema(description = "HTTP status code", example = "200")
    private String statusCode;

    @Schema(description = "HTTP status message", example = "Request processed successfully")
    private String statusMessage;
}
