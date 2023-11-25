package com.romys.models;

import org.springframework.http.HttpHeaders;

import com.romys.DTOs.AddressDTO;
import com.romys.DTOs.BankDTO;
import com.romys.DTOs.CompanyDTO;
import com.romys.DTOs.HairDTO;
import com.romys.DTOs.UserDTO;
import com.romys.DTOs.UserDetailDTO;
import com.romys.enums.Role;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserModel {
    private Role role;
    private String username;
    private String password;
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
    private String ip;
    private AddressDTO address;
    private String macAddress;
    private String university;
    private BankDTO bank;
    private CompanyDTO company;
    private String ein;
    private String ssn;
    private String userAgent;

    public UserModel(UserDTO user) {
        this.role = Role.USER;
        this.username = user.getUsername();
        this.password = user.getPassword();
    }

    public UserModel(UserDetailDTO userDetailDTO, HttpServletRequest request) {
        this.firstName = userDetailDTO.getFirstName();
        this.lastName = userDetailDTO.getLastName();
        this.maidenName = userDetailDTO.getMaidenName();
        this.age = userDetailDTO.getAge();
        this.gender = userDetailDTO.getGender();
        this.email = userDetailDTO.getEmail();
        this.phone = userDetailDTO.getPhone();
        this.birthDate = userDetailDTO.getBirthDate();
        this.image = userDetailDTO.getImage();
        this.bloodGroup = userDetailDTO.getBloodGroup();
        this.height = userDetailDTO.getHeight();
        this.weight = userDetailDTO.getWeight();
        this.eyeColor = userDetailDTO.getEyeColor();
        this.hair = userDetailDTO.getHair();
        this.domain = userDetailDTO.getDomain();
        this.address = userDetailDTO.getAddress();
        this.macAddress = userDetailDTO.getMacAddress();
        this.university = userDetailDTO.getUniversity();
        this.bank = userDetailDTO.getBank();
        this.company = userDetailDTO.getCompany();
        this.ein = userDetailDTO.getEin();
        this.ssn = userDetailDTO.getSsn();
        this.ip = this.getClientIP(request);
        this.userAgent = request.getHeader(HttpHeaders.USER_AGENT);
    }

    private String getClientIP(HttpServletRequest request) {
        String xForwardedForHeader = request.getHeader("X-Forwarded-For");

        if (xForwardedForHeader == null) {
            return request.getRemoteAddr();
        }

        return xForwardedForHeader.split(",")[0].trim();
    }
}