package com.romys.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CoordinatesDTO {
    @Schema(defaultValue = "")
    private double lat;

    @Schema(defaultValue = "")
    private double lon;
}
