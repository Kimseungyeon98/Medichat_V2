package ksy.medichat.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @Before("execution(* ksy.medichat..*Controller.*(..))")
    public void beforeControllerMethod(JoinPoint joinPoint) {
        System.out.println("Controller: " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
    }

    /*@Before("execution(* ksy.medichat..*Service.*(..))")
    public void beforeServiceMethod(JoinPoint joinPoint) {
        System.out.println("Service: " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
    }

    @Before("execution(* ksy.medichat..*Repository.*(..))")
    public void beforeRepositoryMethod(JoinPoint joinPoint) {
        System.out.println("Repository: " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
    }*/
}