package com.doghandeveloper.doggu.common.security;

import com.doghandeveloper.doggu.common.exception.ErrorCode;
import com.doghandeveloper.doggu.common.exception.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public class RestAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException authException) throws IOException, ServletException {
        log.info("Responding with forbidden error. Message - {}", authException.getMessage());
        // 필요한 권한이 없이 접근하려 할때 403
//        response.sendError(HttpServletResponse.SC_FORBIDDEN);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json;charset=utf-8");
        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.AUTHENTICATION_ERROR);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(errorResponse);
        PrintWriter out = response.getWriter();
        out.print(json);
    }
}
