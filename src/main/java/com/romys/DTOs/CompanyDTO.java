package com.romys.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CompanyDTO {
    private AddressDTO address;

    @Schema(defaultValue = "")
    private String department;

    @Schema(defaultValue = "")
    private String name;

    @Schema(defaultValue = "")
    private String title;
}
