package com.romys.DTOs;

import lombok.Data;

@Data
public class UserDetailDTO {
    public String firstName;
    public String lastName;
    public String maidenName;
    public int age;
    public String gender;
    public String email;
    public String phone;
    public String birthDate;
    public String image;
    public String bloodGroup;
    public int height;
    public double weight;
    public String eyeColor;
    public HairDTO hair;
    public String domain;
    public AddressDTO address;
    public String macAddress;
    public String university;
    public BankDTO bank;
    public CompanyDTO company;
    public String ein;
    public String ssn;
}