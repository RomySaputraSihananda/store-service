package com.romys.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class BankDTO {
    @Schema(defaultValue = "")
    private String cardExpire;

    @Schema(defaultValue = "")
    private String cardNumber;

    @Schema(defaultValue = "")
    private String cardType;

    @Schema(defaultValue = "")
    private String currency;

    @Schema(defaultValue = "")
    private String iban;
}
