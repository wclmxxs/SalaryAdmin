package cwall.club.common.Annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SalaryPermission {
    enum FunctionPermission{
        BASE("游客操作"),
        USER("用户操作"),
        STAFF("员工操作"),
        ADMIN("管理操作"),
        OPERATION("运维操作"),
        INNER("内部调用")
        ;
        String description;
        FunctionPermission(String description){
            this.description = description;
        }
    }
    FunctionPermission value() default FunctionPermission.BASE;
}
