package com.example.aop.pointcut;

import com.example.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import java.lang.reflect.Method;

@Slf4j
public class ExecutionTest {

    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    Method helloMethod;

    @BeforeEach
    public void init() throws NoSuchMethodException {
        helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
    }

    @Test
    void printMethod(){
        log.info("helloMethod = {}", helloMethod);
    }

    @Test
    void exactMatch(){
        //java.lang.String com.example.aop.member.MemberServiceImpl.hello

        pointcut.setExpression("execution(public String com.example.aop.member.MemberServiceImpl.hello(String))");
        Assertions.assertThat(pointcut.matches(helloMethod,MemberServiceImpl.class)).isTrue();
    }

    @Test
    void allMatch(){
        //반환 타입 메서드 파라미터 모두 생략
        pointcut.setExpression("execution(* *(..))");
        Assertions.assertThat(pointcut.matches(helloMethod,MemberServiceImpl.class)).isTrue();
    }

    @Test
    void nameMatch(){
        //메서드 이름 매치 , * 사용 가능
        pointcut.setExpression("execution(* hello(..))");
        pointcut.setExpression("execution(* *ll*(..))");

    }

    @Test
    void packageMatch(){
        //패키지 이름 매치 , * 사용 가능
        pointcut.setExpression("execution(* com.example.aop.member.MemberServiceImpl.hello(..))");
        pointcut.setExpression("execution(* com.example.aop.member.*.hello(..))");

        //서브 패키지 모두 매칭시 ..
        pointcut.setExpression("execution(* com.example.aop.member..*.hello(..))");
        Assertions.assertThat(pointcut.matches(helloMethod,MemberServiceImpl.class)).isTrue();
    }

}
