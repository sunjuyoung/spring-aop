package hello.proxy.pureproxy.decorator.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageDecorator implements Component{

    private Component component;

    public MessageDecorator(Component component) {
        this.component = component;
    }

    @Override
    public String operation() {
        log.info("messageDeco 실행");

        String operation = component.operation();
        String decoResult = "****"+operation;
        log.info("messageDeco 실행 완료 결과 : {}",decoResult);
        return decoResult;
    }
}
