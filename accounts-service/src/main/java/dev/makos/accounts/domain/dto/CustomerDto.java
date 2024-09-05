package dev.makos.accounts.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(name = "Customer", description = "Customer details to hold customer and account information")
public class CustomerDto {

    @NotEmpty(message = "Name cannot be null or empty")
    @Size(min = 5, max = 30, message = "Name must be between 5 and 30 characters")
    @Schema(description = "Name of the customer", example = "John Doe")
    private String name;

    @NotEmpty(message = "Email cannot be null or empty")
    @Email(message = "Email should be valid")
    @Schema(description = "Email of the customer", example = "test@gmail.com")
    private String email;

    @Pattern(regexp = "^\\d{10}$", message = "Mobile number should be 10 digits")
    @Schema(description = "Mobile number of the customer", example = "9876543210")
    private String mobileNumber;

    @Schema(description = "Account details of the customer")
    private AccountDto accountDto;
}
