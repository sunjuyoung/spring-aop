package com.example.aop.pointcut;

import com.example.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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



    @Test
    void superTypeMatch() throws NoSuchMethodException {
        //부모타입
        pointcut.setExpression("execution(* com.example.aop.member.MemberService.*(..))");

        Method internal = MemberServiceImpl.class.getMethod("internal", String.class);

        //자식 구현체 허용
        Assertions.assertThat(pointcut.matches(helloMethod,MemberServiceImpl.class)).isTrue();
        //부모타입에 있는 메서드만 허용
        Assertions.assertThat(pointcut.matches(internal,MemberServiceImpl.class)).isFalse();
    }

    @DisplayName("파라미터 허용")
    @Test
    void argsMatch(){
        pointcut.setExpression("execution(* *(String))");
        //pointcut.setExpression("execution(* *(String, ..))"); String 타입으로 시작, 모든 타입 모든파라미터
        //pointcut.setExpression("execution(* *(String, *))"); String 타입으로 시작, 2개 파라미터
        Assertions.assertThat(pointcut.matches(helloMethod,MemberServiceImpl.class)).isTrue();
    }


//   within 표현식에 부모 타입을 지정하면 안된다는 점이다. 정확하게
//타입이 맞아야 한다. 이 부분에서 execution 과 차이가 난다
    @Test
    void withinExact() {
        pointcut.setExpression("within(hello.aop.member.MemberServiceImpl)");
        Assertions.assertThat(pointcut.matches(helloMethod,
                MemberServiceImpl.class)).isTrue();
    }
    @Test
    void withinStar() {
        pointcut.setExpression("within(hello.aop.member.*Service*)");
        Assertions.assertThat(pointcut.matches(helloMethod,
                MemberServiceImpl.class)).isTrue();
    }
    @Test
    void withinSubPackage() {
        pointcut.setExpression("within(hello.aop..*)");
        Assertions.assertThat(pointcut.matches(helloMethod,
                MemberServiceImpl.class)).isTrue();
    }

}
