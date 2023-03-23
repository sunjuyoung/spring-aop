package com.example.aop.pointcut;

import com.example.aop.member.MemberService;
import com.example.aop.member.annotation.ClassAop;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;


@Import(ParameterTest.ParameterAspect.class)
@Slf4j
@SpringBootTest
public class ParameterTest {

    @Autowired
    MemberService memberService;

    @Test
    void success() {
        log.info("memberService Proxy={}", memberService.getClass());
        memberService.hello("helloA");
    }

    @Aspect
    @Slf4j
    static class ParameterAspect{

        @Pointcut("execution(* com.example.aop.member..*.*(..))")
        private void allMember(){}

        @Around("allMember()")
        public Object logArgs1(ProceedingJoinPoint joinPoint) throws Throwable {
            Object args = joinPoint.getArgs()[0];

            log.info("[logArgs1]{}, args={}",joinPoint.getSignature(), args);
            return joinPoint.proceed();

        }


        @Around("allMember() && args(arg,..)")
        public Object logArgs2(ProceedingJoinPoint joinPoint, Object arg) throws Throwable {
            log.info("[logArgs1]{}, args={}",joinPoint.getSignature(), arg);
            return joinPoint.proceed();
        }

        @Before("allMember() && args(arg,..)")
        public void logArgs3(String arg){
            log.info("[logArgs3] arg={}",arg);
        }

        //
        @Before("allMember() && this(obj)")//MemberService
        public void thisArgs(JoinPoint joinPoint, MemberService obj){
            log.info("[this]{}, obj={}",joinPoint.getSignature(), obj.getClass());
        }

        //
        @Before("allMember() && target(obj)")//MemberService
        public void targetArgs(JoinPoint joinPoint, MemberService obj){
            log.info("[target]{}, obj={}",joinPoint.getSignature(), obj.getClass());
        }

        @Before("allMember() && target(annotation)")//MemberService
        public void targetArgs(JoinPoint joinPoint, ClassAop annotation){
            log.info("[target]{}, obj={}",joinPoint.getSignature(), annotation);
        }
    }
}
