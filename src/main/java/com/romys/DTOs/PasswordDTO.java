package com.romys.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class PasswordDTO {
    @Schema(defaultValue = "12345678")
    String oldPassword;
    @Schema(defaultValue = "123456789")
    String newPassword;
}
