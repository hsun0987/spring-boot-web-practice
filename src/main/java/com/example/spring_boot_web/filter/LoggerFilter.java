package com.example.spring_boot_web.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

@Slf4j
// @Component
public class LoggerFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        // 진입 전
        log.info(">>>>>>>> 집입 ");

        var req = new ContentCachingRequestWrapper((HttpServletRequest)servletRequest);
        var res = new ContentCachingResponseWrapper((HttpServletResponse)servletResponse);

        filterChain.doFilter(req, res);

        var reqJson = new String(req.getContentAsByteArray());
        log.info("req : {}", reqJson);
        var resJson = new String(res.getContentAsByteArray());
        log.info("res : {}", resJson);

        log.info("<<<<<<<<< 리턴 ");

        // 진입 후
        // 클라이언트 response body에 데이터 출력
        res.copyBodyToResponse();
    }
}
