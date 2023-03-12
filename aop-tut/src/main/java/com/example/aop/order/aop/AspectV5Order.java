package com.example.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;

@Slf4j
public class AspectV5Order {

    //order는 aspect기분으로 사용하기때문에 스프링에서는 클래스단위로

    @Order(2)
    @Aspect
    public static class LogAspect{
        @Around("com.example.aop.order.aop.Pointcuts.allOrders()")
        public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable{
            log.info("[log] {}",joinPoint.getSignature());
            return joinPoint.proceed();
        }
    }

    @Order(1)
    @Aspect
    public static class TxAspect{
        //. aop 패키지와 하위 패키지 이면서 클래스 이름 패턴이 *Service
        @Around("com.example.aop.order.aop.Pointcuts.orderAndService()")
        public Object doTransation(ProceedingJoinPoint joinPoint) throws Throwable{

            try{
                //before
                log.info("[트랜잭션 시작] {}", joinPoint.getSignature());
                Object result = joinPoint.proceed();
                //afterreturning
                log.info("[트랜잭션 시작] {}", joinPoint.getSignature());
                return result;
            }catch (Exception e){
                //afterthrowing
                log.info("[트랜잭션 롤백] {}", joinPoint.getSignature());
                throw e;
            }finally {
                //after
                log.info("[리소스 릴리즈]  {}", joinPoint.getSignature());
            }
        }
    }




}
