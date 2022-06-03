package com.zcy.blog.config.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * 全局日志处理
 */
@Aspect //表示是切面
@Component
public class GlobalLogHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 可以统一定义切点
     * 第一个 * 表示要拦截的目标方法返回值任意（也可以明确指定返回值类型
     * 第二个 * 表示包中的任意类（也可以明确指定类
     * 第三个 * 表示类中的任意方法
     * 最后面的两个点表示方法参数任意，个数任意，类型任意
     */
    @Pointcut("execution(* com.zcy.blog.controller.*.*(..))")
    public void log(){}

    /**
     * @param joinPoint 包含了目标方法的关键信息
     * @Before 注解表示这是一个前置通知，即在目标方法执行之前执行，注解中，需要填入切点
     */
    @Before("log()")
    public void doBefore(JoinPoint joinPoint){
//        logger.info("拦截到请求："+joinPoint.getArgs().toString());
    }


    /**
     * 环绕通知
     *
     * 环绕通知是集大成者，可以用环绕通知实现上面的四个通知，这个方法的核心有点类似于在这里通过反射执行方法
     * @param pjp
     * @return 注意这里的返回值类型最好是 Object ，和拦截到的方法相匹配
     */
//    @Around("log()")
//    public Object around(ProceedingJoinPoint pjp) {
//        Object proceed = null;
//        try {
//            //这个相当于 method.invoke 方法，我们可以在这个方法的前后分别添加日志，就相当于是前置/后置通知
//            proceed = pjp.proceed();
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }
//        return proceed;
//    }
}
