package wenjing.xdtic.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * 记录 Controller 中方法执行的切面
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
@Aspect
@Component
public class ControllerLogger {

    @Pointcut("execution(* wenjing.xdtic.controller.*.*(..))")
    public void advice() {

    }

    @Before("advice()")
    public void doBefore(JoinPoint jp) {
        String methodName = getMethodName(jp);
        System.out.println("enter method: " + methodName);
    }

    public void doAround() {
    }

    @After("advice()")
    public void doAfter(JoinPoint jp) {
        String methodName = getMethodName(jp);
        System.out.println("exit  method: " + methodName);
    }

    public void doReturn() {

    }

    public void doThrowing() {

    }

    private String getMethodName(JoinPoint jp) {
        //    Object proxy = jp.getThis(); // 代理类
        Object target = jp.getTarget();
        String className = target.getClass().getSimpleName();
        MethodSignature signature = (MethodSignature) jp.getSignature();
        String methodName = signature.getName();

        return className + ":" + methodName;
    }
}
