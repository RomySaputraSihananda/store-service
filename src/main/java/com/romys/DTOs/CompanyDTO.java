package com.romys.DTOs;

import lombok.Data;

@Data
public class CompanyDTO {
    private AddressDTO address;
    private String department;
    private String name;
    private String title;
}
