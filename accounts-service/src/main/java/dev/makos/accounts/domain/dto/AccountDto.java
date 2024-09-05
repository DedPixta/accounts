package dev.makos.accounts.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(name = "Account", description = "Account details to hold account information")
public class AccountDto {

    @NotEmpty(message = "Account number cannot be null or empty")
    @Pattern(regexp = "^\\d{10}$", message = "Account number should be 10 digits")
    @Schema(description = "Account number of the customer", example = "1234567890")
    private Long accountNumber;

    @NotEmpty(message = "Account type cannot be null or empty")
    @Schema(description = "Account type of the customer", example = "Savings")
    private String accountType;

    @NotEmpty(message = "Branch address cannot be null or empty")
    @Schema(description = "Branch name of the customer", example = "ABC Bank")
    private String branchAddress;
}
