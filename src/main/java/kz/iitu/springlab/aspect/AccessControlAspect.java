package kz.iitu.springlab.aspect;

import jakarta.servlet.http.HttpServletRequest;
import kz.iitu.springlab.audit.RequiresRole; // <-- ДОБАВЛЕН ИМПОРТ
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@Order(0) // Запускается самым первым до всех остальных аспектов
public class AccessControlAspect {

    @Before("@annotation(requiresRole)")
    public void checkRole(RequiresRole requiresRole) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return;
        }

        HttpServletRequest request = attributes.getRequest();
        String role = request.getHeader("X-Role");

        if (role == null || !role.equals(requiresRole.value())) {
            throw new SecurityException("Access denied: required role " + requiresRole.value());
        }
    }
}