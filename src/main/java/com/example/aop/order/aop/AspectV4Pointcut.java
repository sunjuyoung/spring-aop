package com.example.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Slf4j
@Aspect
public class AspectV4Pointcut {


//포인트컷을 외부로 뺴서 따로 관리한다
    //전체 경로를 입력해주어야 한다.

    @Around("com.example.aop.order.aop.PointCuts.allOrder()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable{
        log.info("[log] {}", joinPoint.getSignature()); //시그니처
        return joinPoint.proceed();
    }

    @Around("com.example.aop.order.aop.PointCuts.orderAndService()")
    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable{

        try {
            log.info("[트랜잭션 시작] {}",joinPoint.getSignature());
            Object result = joinPoint.proceed();
            log.info("[트랜잭션 커밋] {}",joinPoint.getSignature());
            return result;
        }catch (Exception e){
            log.info("[롤백] {}", joinPoint.getSignature());
            throw  e;
        }finally {
            log.info("[리소스 릴리즈] {}", joinPoint.getSignature());
        }
    }
}
