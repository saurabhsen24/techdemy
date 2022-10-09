package com.techdemy.security;

import com.techdemy.dto.ErrorMessage;
import com.techdemy.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException ex) throws IOException, ServletException {
        log.error(ex.getLocalizedMessage(), ex);
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        ErrorMessage errorMessage = ErrorMessage.builder()
                .statusCode(HttpServletResponse.SC_FORBIDDEN)
                .message("Forbidden")
                .description(ex.getMessage())
                .timestamp(new Date())
                .build();

        String errorJson = Utils.toJson(errorMessage);
        response.getWriter().write(errorJson);
    }
}
