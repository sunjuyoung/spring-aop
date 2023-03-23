package com.example.aop.exam;

import com.example.aop.exam.annotation.Retry;
import com.example.aop.exam.annotation.Trace;
import org.springframework.stereotype.Repository;

@Repository
public class ExamRepository {

    private static int seq = 0;

    //5번에 한번 실패한다

    @Trace
    @Retry(4)        //횟수제한 필수, 재지정 가능
    public String save(String itemId){
        seq++;
        if(seq % 5 ==0){
            throw new IllegalStateException("예외 발생");
        }
        return "ok";
    }
}
