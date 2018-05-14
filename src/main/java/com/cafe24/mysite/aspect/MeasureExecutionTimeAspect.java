package com.cafe24.mysite.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
@Aspect
public class MeasureExecutionTimeAspect {

	@Around("execution(* *..repository.*.*(..)) || execution(* *..service.*.*(..))")
	public Object roundAdvice(ProceedingJoinPoint pjp) throws Throwable {

		StopWatch sw = new StopWatch();
		sw.start();

		Object result = pjp.proceed();

		sw.stop();
		Long time = sw.getTotalTimeMillis();

		String className = pjp.getTarget().getClass().getName();
		String methodName = pjp.getSignature().getName();
		String taskName = className + "." + methodName;

		System.out.println(taskName + ":" + time+"mills");

		return result;
	}

}
