package com.cgg.framework.aspect;

import com.cgg.framework.validate.Validate;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


@Aspect
@Component
@Slf4j
public class ParamValidateAspect {

  @Pointcut("execution(* com.cgg..*.controller..*(..))")
  public void controllerPointCut() {}

  @Around("controllerPointCut()")
  public Object around(ProceedingJoinPoint point) throws Throwable {
    Object[] args = point.getArgs();
    for (Object arg : args) {
      if (arg instanceof Validate) {
        ((Validate) arg).validate();
      }
    }
    return point.proceed();
  }
}
