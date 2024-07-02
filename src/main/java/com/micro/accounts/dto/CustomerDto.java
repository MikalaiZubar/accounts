package com.micro.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(
        name = "Customer",
        description = "Schema to hold Customer and Account data"
)
public class CustomerDto {

    @Schema(
            description = "Name of the Customer",
            example = "John Doe"
    )
    @NotBlank(message = "name cannot be empty")
    private String name;

    @Schema(
            description = "email of the Customer",
            example = "some@emai.com"
    )
    @NotBlank(message = "email cannot be empty")
    @Email
    private String email;

    @Schema(
            description = "Mobile number of the Customer",
            example = "370987654321"
    )
    @NotBlank(message = "mobileNumber cannot be empty")
    @Pattern(regexp = "[\\d]{12}", message = "mobileNumber should contain 12 digits")
    private String mobileNumber;

    @Schema(
            description = "Account detailes for the Customer"
    )
    private AccountDto account;

}
