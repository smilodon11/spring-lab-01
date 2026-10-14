package kz.iitu.springlab.aspect;

import kz.iitu.springlab.audit.Audited;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
@Order(1)
public class AuditAspect {

    private static final Logger log = LoggerFactory.getLogger(AuditAspect.class);

    @Around("@annotation(audited)")
    public Object audit(ProceedingJoinPoint pjp, Audited audited) throws Throwable {
        log.info("[AUDIT] start {}", audited.action());
        if (audited.logArguments()) {
            log.info("[AUDIT] args={}", java.util.Arrays.toString(pjp.getArgs()));
        }
        try {
            Object result = pjp.proceed();
            log.info("[AUDIT] {} success at {}", audited.action(), LocalDateTime.now());
            return result;
        } catch (Throwable ex) {
            log.warn("[AUDIT] {} failure at {}: {}", audited.action(), LocalDateTime.now(), ex.getMessage());
            throw ex;                           // исключение нельзя "проглатывать"
        }
    }
}