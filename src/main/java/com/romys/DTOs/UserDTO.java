package com.romys.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserDTO {
    @Schema(defaultValue = "romys")
    private String username;
    @Schema(defaultValue = "12345678")
    private String password;
}
