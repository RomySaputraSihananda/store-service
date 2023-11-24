package com.romys.DTOs;

import lombok.Data;

@Data
public class PasswordDTO {
    String oldPassword;
    String newPassword;
}
