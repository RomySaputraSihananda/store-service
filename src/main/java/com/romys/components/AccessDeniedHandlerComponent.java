package com.romys.components;

import java.io.IOException;
import java.util.List;

import com.romys.payloads.responses.BodyResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AccessDeniedHandlerComponent implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException {
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(HttpStatus.FORBIDDEN.value());
		final ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(
				response.getOutputStream(),
				new BodyResponse<>(
						HttpStatus.FORBIDDEN.getReasonPhrase(), HttpStatus.FORBIDDEN.value(),
						accessDeniedException.getMessage(),
						List.of(
								request.getServletPath(),
								accessDeniedException.getClass().getName())));
	}
}
