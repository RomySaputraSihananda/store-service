package com.romys.models;

import lombok.Data;

@Data
public class UserModel {
    private int id;
    private String firstName;
    private String lastName;
    private String maidenName;
    private int age;
    private String gender;
    private String email;
    private String phone;
    private String username;
    private String password;
    private String birthDate;
    private String image;
    private String bloodGroup;
    private int height;
    private double weight;
    private String eyeColor;
    private Hair hair;
    private String domain;
    private String ip;
    private Address address;
    private String macAddress;
    private String university;
    private Bank bank;
    private Company company;
    private String ein;
    private String ssn;
    private String userAgent;

}

@Data
class Address {
    private String address;
    private String city;
    private Coordinates coordinates;
    private String postalCode;
    private String state;
}

@Data
class Bank {
    private String cardExpire;
    private String cardNumber;
    private String cardType;
    private String currency;
    private String iban;
}

@Data
class Company {
    private Address address;
    private String department;
    private String name;
    private String title;
}

@Data
class Coordinates {
    private double lat;
    private double lon;
}

@Data
class Hair {
    private String color;
    private String type;
}
