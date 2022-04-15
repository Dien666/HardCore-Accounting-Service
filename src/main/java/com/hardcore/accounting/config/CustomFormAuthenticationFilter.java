package com.hardcore.accounting.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hardcore.accounting.exception.ErrorResponse;
import com.hardcore.accounting.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.hardcore.accounting.exception.BizErrorCode.NO_AUTHORIZED;

@Slf4j
public class CustomFormAuthenticationFilter extends FormAuthenticationFilter {

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (isLoginRequest(request, response)) {
            if (isLoginSubmission(request, response)) {
                return executeLogin(request, response);
            } else {
                //allow them to see the login page ;)
                return true;
            }
        } else {
            //saveRequest(request);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            val errorResponse = ErrorResponse.builder()
                    .code(NO_AUTHORIZED)
                    .errorType(ServiceException.ErrorType.Client)
                    .statusCode(HttpStatus.UNAUTHORIZED.value())
                    .message("No access for related url")
                    .build();

            try (val writer = response.getWriter()){
                writer.write(new ObjectMapper().writeValueAsString(errorResponse));
            } catch (IOException ex) {
                log.error("The IO exception is thrown!");
                return false;
            }
            return false;
        }
    }
}
