package com.example.spring_boot_web.filter.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
public class OpenApiInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("pre handle");
        // true면 controller 전달, false면 전달 안함

        var handlerMethod = (HandlerMethod)handler;

        // 메소드에 @OpenApi 가 붙어있는지 확인
        var methodLevel = handlerMethod.getMethodAnnotation(OpenApi.class);
        if (methodLevel != null){
            log.info("method level");
            return true;
        }

        // 클래스에 @OpenApi 가 붙어있는지 확인
        var classLevel = handlerMethod.getBeanType().getAnnotation(OpenApi.class);
        if (classLevel != null){
            log.info("class level");
            return true;
        }

        log.info("open api 아닙니다 : {}", request.getRequestURI());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("post handle");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("after completion");
    }
}
