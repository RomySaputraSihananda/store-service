package com.romys.payloads.responses;

import java.util.List;

public record BodyResponses<Data>(
                String status,
                int code,
                String message,
                List<Data> data) {
}
