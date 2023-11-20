package com.romys.payloads.responses;

public record BodyResponse<Data>(
        String status,
        int code,
        String message,
        Data data) {
}
