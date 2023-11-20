package com.romys.DTOs;

import lombok.Data;

@Data
public class UserDetailDTO {
    private String firstName;
    private String lastName;
    private String maidenName;
    private int age;
    private String gender;
    private String email;
    private String phone;
    private String birthDate;
    private String image;
    private String bloodGroup;
    private int height;
    private double weight;
    private String eyeColor;
    private HairDTO hair;
    private String domain;
    private AddressDTO address;
    private String macAddress;
    private String university;
    private BankDTO bank;
    private CompanyDTO company;
    private String ein;
    private String ssn;
}
