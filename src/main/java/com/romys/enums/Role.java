package com.romys.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Role {

    ROOT("ROLE_ROOT"),
    USER("ROLE_USER");

    private String value;
}
