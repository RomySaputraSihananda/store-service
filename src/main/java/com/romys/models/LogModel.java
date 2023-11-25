package com.romys.models;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.http.HttpHeaders;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LogModel {
    private String username;
    private String ip;
    private String userAgent;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime time;

    public LogModel(String username, HttpServletRequest request) {
        this.username = username;
        this.ip = this.getClientIP(request);
        this.userAgent = request.getHeader(HttpHeaders.USER_AGENT);
        this.time = this.time != null ? this.time : LocalDateTime.now(ZoneId.of("Asia/Jakarta"));
    }

    private String getClientIP(HttpServletRequest request) {
        String xForwardedForHeader = request.getHeader("X-Forwarded-For");

        if (xForwardedForHeader == null)
            return request.getRemoteAddr();

        return xForwardedForHeader.split(",")[0].trim();
    }
}
