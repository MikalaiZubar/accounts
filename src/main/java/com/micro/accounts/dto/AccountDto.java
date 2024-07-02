package com.micro.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Schema(
        name = "Account",
        description = "Schema to hold Account data"
)
@Data
public class AccountDto {

    @Schema(
            description = "Account number",
            example = "987654321"
    )
    @NotNull
    @Size(min = 9, max = 9)
    private Long accountNumber;

    @Schema(
            description = "Account type",
            example = "CREDIT"
    )
    @NotBlank(message = "accountType cannot be empty")
    private String accountType;

    @Schema(
            description = "Bank branch address",
            example = "City, Street, 12"
    )
    @NotBlank(message = "branchAddress cannot be empty")
    private String branchAddress;
}
