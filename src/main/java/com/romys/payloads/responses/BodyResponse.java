package com.romys.payloads.responses;

import java.util.ArrayList;

public record BodyResponse<Data>(
        String status,
        int code,
        String message,
        ArrayList<Data> data) {
}
