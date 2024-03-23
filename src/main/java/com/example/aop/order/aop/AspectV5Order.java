package com.example.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;

@Slf4j
public class AspectV5Order {


    //Order는 클래스단위로 구분한다.
    @Aspect
    @Order(2)
    public static class LogAspect{
        @Around("com.example.aop.order.aop.PointCuts.allOrder()")
        public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable{
            log.info("[log] {}", joinPoint.getSignature()); //시그니처
            return joinPoint.proceed();
        }
    }

    @Aspect
    @Order(1)
    public static class TxAspect{

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





}
