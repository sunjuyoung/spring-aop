package com.example.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

@Slf4j
@Aspect
public class AspectV6Advice {

//    어드바이스 종류
//@Around : 메서드 호출 전후에 수행, 가장 강력한 어드바이스, 조인 포인트 실행 여부 선택, 반환 값 변환, 예외
//변환 등이 가능
//@Before : 조인 포인트 실행 이전에 실행
//@AfterReturning : 조인 포인트가 정상 완료후 실행
//@AfterThrowing : 메서드가 예외를 던지는 경우 실행
//@After : 조인 포인트가 정상 또는 예외에 관계없이 실행(finally


//    JoinPoint 인터페이스의 주요 기능
//getArgs() : 메서드 인수를 반환합니다.
//getThis() : 프록시 객체를 반환합니다.
//getTarget() : 대상 객체를 반환합니다.
//getSignature() : 조언되는 메서드에 대한 설명을 반환합니다.
//toString() : 조언되는 방법에 대한 유용한 설명을 인쇄합니다.
//ProceedingJoinPoint 인터페이스의 주요 기능
//proceed() : 다음 어드바이스나 타켓을 호출한다.

    @Around("com.example.aop.order.aop.PointCuts.allOrder()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable{
        log.info("[log] {}", joinPoint.getSignature()); //시그니처
        return joinPoint.proceed();
    }

//    @Around("com.example.aop.order.aop.PointCuts.orderAndService()")
//    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable{
//
//        try {
//
//            //@Before
//            log.info("[트랜잭션 시작] {}",joinPoint.getSignature());
//            Object result = joinPoint.proceed();
//
//            //@@AfterReturning
//            log.info("[트랜잭션 커밋] {}",joinPoint.getSignature());
//            return result;
//        }catch (Exception e){
//            //@AfterThrowing
//            log.info("[롤백] {}", joinPoint.getSignature());
//            throw  e;
//        }finally {
//            //@After
//            log.info("[리소스 릴리즈] {}", joinPoint.getSignature());
//        }
//    }

    @Before("com.example.aop.order.aop.PointCuts.orderAndService()")
    public void doBefore(JoinPoint joinPoint){
        log.info("[before] {}" ,joinPoint.getSignature());
    }
    @AfterReturning(value = "com.example.aop.order.aop.PointCuts.orderAndService()",returning = "result")
    public void doReturn(JoinPoint joinPoint, Object result){
        log.info("[return] {} return={}", joinPoint.getSignature(), result);
    }

    @AfterThrowing(value = "com.example.aop.order.aop.PointCuts.orderAndService()",throwing = "ex")
    public void doThrow(JoinPoint joinPoint, Exception ex){
        log.info("[throw] {} ex={}", joinPoint.getSignature(), ex);
    }

    @After(value = "com.example.aop.order.aop.PointCuts.orderAndService()")
    public void doAfter(JoinPoint joinPoint){
        log.info("[finally] {}", joinPoint.getSignature());
    }
}
