package com.micro.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Schema(
        name = "ErrorResponse",
        description = "Schema to hold error response data"
)
@Data
@AllArgsConstructor
public class ErrorResponseDto {

    @Schema(
            description = "Invoked url"
    )
    private String apiPath;

    @Schema(
            description = "Error code"
    )
    private HttpStatus errorCode;

    @Schema(
            description = "Error message"
    )
    private String errorMessage;

    @Schema(
            description = "Time the error occurred"
    )
    private LocalDateTime errorTime;
}
