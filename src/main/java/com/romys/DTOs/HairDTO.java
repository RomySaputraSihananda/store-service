package com.romys.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class HairDTO {
    @Schema(defaultValue = "")
    private String color;

    @Schema(defaultValue = "")
    private String type;
}
