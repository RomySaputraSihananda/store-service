package com.romys.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AddressDTO {

    @Schema(defaultValue = "")
    private String address;

    @Schema(defaultValue = "")
    private String city;

    private CoordinatesDTO coordinates;

    @Schema(defaultValue = "")
    private String postalCode;

    @Schema(defaultValue = "")
    private String state;
}
