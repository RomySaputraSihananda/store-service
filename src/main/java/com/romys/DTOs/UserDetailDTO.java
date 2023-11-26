package com.romys.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserDetailDTO {
    @Schema(defaultValue = "romy")
    public String firstName;

    @Schema(defaultValue = "sihananda")
    public String lastName;

    @Schema(defaultValue = "saputra")
    public String maidenName;

    @Schema(defaultValue = "18")
    public int age;

    @Schema(defaultValue = "male")
    public String gender;

    @Schema(defaultValue = "romysaputrasihanandaa@gmail.com")
    public String email;

    @Schema(defaultValue = "088765144536")
    public String phone;

    @Schema(defaultValue = "2005-12-22")
    public String birthDate;

    @Schema(defaultValue = "https://raw.githubusercontent.com/RomySaputraSihananda/RomySaputraSihananda/main/images/me.jpg")
    public String image;

    @Schema(defaultValue = "AB")
    public String bloodGroup;

    @Schema(defaultValue = "165")
    public int height;

    @Schema(defaultValue = "55")
    public double weight;

    @Schema(defaultValue = "black")
    public String eyeColor;

    public HairDTO hair;

    @Schema(defaultValue = "https://romysaputrasihananda.github.io")
    public String domain;

    public AddressDTO address;

    @Schema(defaultValue = "13:69:BA:56:A3:74")
    public String macAddress;

    @Schema(defaultValue = "Indonesia University")
    public String university;

    public BankDTO bank;

    public CompanyDTO company;

    @Schema(defaultValue = "20-9487066")
    public String ein;

    @Schema(defaultValue = "661-64-2976")
    public String ssn;
}