package com.example.aop.order.aop;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {

    @Pointcut("execution(* com.example.aop..*(..))")
    public void allOrder(){} //pointcut signature

    //클래스 이름 패턴이 *service
    @Pointcut("execution(* *..*Service.*(..))")
    public void allService(){}

    @Pointcut("allOrder()")
    public void allOrders(){}


    @Pointcut("allOrders() && allService()")
    public void orderAndService(){}






}
