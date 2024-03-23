package com.example.aop.order.aop;

import org.aspectj.lang.annotation.Pointcut;

public class PointCuts {

    @Pointcut("execution(* com.example.aop.order..*(..))")
    public void allOrder(){} //pointcut 시그니처

    //클래스 이름 패턴이 *service
    @Pointcut("execution(* com.example.aop.order.*Service.*(..))")
    public void allService(){}

    @Pointcut("allOrder() && allService()")
    public void orderAndService(){}
}
