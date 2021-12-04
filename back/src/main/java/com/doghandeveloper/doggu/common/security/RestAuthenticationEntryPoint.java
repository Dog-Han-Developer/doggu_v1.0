package com.doghandeveloper.doggu.common.security;

import com.doghandeveloper.doggu.common.exception.ErrorCode;
import com.doghandeveloper.doggu.common.exception.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.info("Responding with unauthorized error. Message - {}", authException.getMessage());
        // 유효한 자격증명을 제공하지 않고 접근하려 할때 401
//        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getLocalizedMessage());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=utf-8");
        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.AUTHORIZATION_ERROR);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(errorResponse);
        PrintWriter out = response.getWriter();
        out.print(json);
    }
}
