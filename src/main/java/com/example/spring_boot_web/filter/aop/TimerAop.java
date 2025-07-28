package com.example.spring_boot_web.filter.aop;

import com.example.spring_boot_web.model.UserRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.Arrays;

@Aspect
@Component
public class TimerAop {

    @Pointcut(value = "within(com.example.spring_boot_web.controller.UserApiController)")
    public void timerPointCut(){}

    // 메소드 실행 전
    @Before(value = "timerPointCut()")
    public void before(JoinPoint joinPoint){
        System.out.println("before");
    }

    // 메소드 실행 후
    @After(value = "timerPointCut()")
    public void after(JoinPoint joinPoint){
        System.out.println("after");
    }

    // 실행이 성공했을 때 결과값
    @AfterReturning(value = "timerPointCut()", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result){
        System.out.println("after returning");
    }

    // 예외가 발생했을 때
    @AfterThrowing(value = "timePointCut()", throwing = "tx")
    public void afterThrowing(JoinPoint joinPoint, Throwable tx){
        System.out.println("after throwing");
    }

    // 메소드 실행 앞뒤
    @Around(value = "timerPointCut()")
    public void around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("메소드 실행 이전");

        Arrays.stream(joinPoint.getArgs()).forEach(
                it -> {
                    if (it instanceof UserRequest){
                        var tempUser = (UserRequest) it;
                        var phoneNumber = tempUser.getPhoneNumber().replace("-", "");
                        tempUser.setPhoneNumber(phoneNumber);
                    }
                }
        );

        var newObjs = Arrays.asList(
                new UserRequest()
        );

        var stopWatch = new StopWatch();
        stopWatch.start();
        joinPoint.proceed(newObjs.toArray());

        stopWatch.stop();

        System.out.println("총 소요된 시간 = " + stopWatch.getTotalTimeMillis());
        System.out.println("메소드 실행 이후");
    }
}
