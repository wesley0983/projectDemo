package com.example.demo.security;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

public class CustomProcessingFilter extends AbstractAuthenticationProcessingFilter {

    private ObjectMapper mapper = new ObjectMapper();

    protected CustomProcessingFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    protected CustomProcessingFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        if (httpServletRequest.getMethod().equals("POST") && httpServletRequest.getContentType().startsWith("application/json")) {
            JsonNode root = mapper.readTree(httpServletRequest.getInputStream());
            return this.getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(root.get("username").asText(), root.get("password").asText()));
        } else {
            throw new BadCredentialsException("method not support");
        }
    }

    private String getXsfToken(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String result = null;
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("XSRF-TOKEN")) {
                    result = cookie.getValue();
                    break;
                }
            }
        }
        if (result == null) {
            Collection<String> headers = httpServletResponse.getHeaders("Set-Cookie");
            for (String header : headers) {
                if (header.contains("XSRF-TOKEN")) {
                    result = header.substring(header.indexOf("=") + 1, header.indexOf(";"));
                    break;
                }
            }
        }
        return result;
    }
}
