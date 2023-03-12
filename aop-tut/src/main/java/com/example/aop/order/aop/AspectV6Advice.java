package com.example.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

@Slf4j
@Aspect
public class AspectV6Advice {



    //. aop 패키지와 하위 패키지 이면서 클래스 이름 패턴이 *Service
//    @Around("com.example.aop.order.aop.Pointcuts.orderAndService()")
//    public Object doTransation(ProceedingJoinPoint joinPoint) throws Throwable{
//
//        try{
//            //before
//            log.info("[트랜잭션 시작] {}", joinPoint.getSignature());
//            Object result = joinPoint.proceed();
//            //afterreturning
//            log.info("[트랜잭션 시작] {}", joinPoint.getSignature());
//            return result;
//        }catch (Exception e){
//            //afterthrowing
//            log.info("[트랜잭션 롤백] {}", joinPoint.getSignature());
//            throw e;
//        }finally {
//            //after
//            log.info("[리소스 릴리즈]  {}", joinPoint.getSignature());
//        }
//    }




    @Before("com.example.aop.order.aop.Pointcuts.orderAndService()")
    public void doBefore(JoinPoint joinPoint){
        log.info("[before] {}", joinPoint.getSignature());
    }


    @AfterReturning(value = "com.example.aop.order.aop.Pointcuts.orderAndService()", returning = "result")
    public void doReturn(JoinPoint joinPoint, Object result){
        log.info("[result] {} result={}", joinPoint.getSignature(),result);
    }

    @AfterThrowing(value = "com.example.aop.order.aop.Pointcuts.orderAndService()",throwing = "ex")
    public void doThrowing(JoinPoint joinPoint, Exception ex){
        log.info("[ex] {} message={}",ex);
    }

    @After(value = "com.example.aop.order.aop.Pointcuts.orderAndService()")
    public void doAfter(JoinPoint joinPoint){
        log.info("[after]  {}", joinPoint.getSignature());
    }


}
