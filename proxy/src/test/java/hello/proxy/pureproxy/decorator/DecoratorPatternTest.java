package hello.proxy.pureproxy.decorator;

import hello.proxy.pureproxy.decorator.code.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class DecoratorPatternTest {

    @Test
    public void noDeco(){
        Component component = new RealComponent();
        DecoratorPatternClient decoratorPatternClient = new DecoratorPatternClient(component);

        decoratorPatternClient.execute();
    }

    @Test
    public void deco1(){
        Component realComponent = new RealComponent();
        Component component = new MessageDecorator(realComponent);
        DecoratorPatternClient decoratorPatternClient = new DecoratorPatternClient(component);

        decoratorPatternClient.execute();
    }

    @Test
    public void deco2(){
        Component realComponent = new RealComponent();
        Component messageDecorator = new MessageDecorator(realComponent);
        TimeDecorator timeDecorator = new TimeDecorator(messageDecorator);
        DecoratorPatternClient decoratorPatternClient = new DecoratorPatternClient(timeDecorator);

        decoratorPatternClient.execute();
    }
}
