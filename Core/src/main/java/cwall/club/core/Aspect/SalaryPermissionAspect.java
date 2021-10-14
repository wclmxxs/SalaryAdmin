package cwall.club.core.Aspect;

import cwall.club.common.Annotation.SalaryPermission;
import cwall.club.common.Enum.ExceptionCode;
import cwall.club.common.Exception.SalaryException;
import cwall.club.common.Item.EmployeeInfo;
import cwall.club.common.Item.UserContext;
import cwall.club.common.Util.AssertUtil;
import cwall.club.core.Repository.EmployeeRepository;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;

@Aspect
@Service
public class SalaryPermissionAspect {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Around("@annotation(cwall.club.common.Annotation.SalaryPermission)")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable{
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        SalaryPermission permission = method.getAnnotation(SalaryPermission.class);
        SalaryPermission.FunctionPermission type = permission.value();
        UserContext user = UserContext.getUser();
        EmployeeInfo employeeInfo = null;
        switch (type){
            case INNER:
                break;
            case OPERATION:
                break;
            case ADMIN:
                employeeInfo = employeeRepository.findByCidAndUid(Long.valueOf(user.getCid()), user.getId());
                if (employeeInfo == null){
                    throw new SalaryException(500,"该用户不在选择的公司内");
                }
                if (employeeInfo.getType() != '2'){
                    throw new SalaryException(500,"只有管理可以进行该操作");
                }
            case STAFF:
                AssertUtil.isEmpty(user.getCid());
                if (employeeInfo == null){
                    employeeInfo = employeeRepository.findByCidAndUid(Long.valueOf(user.getCid()), user.getId());
                }
                if (employeeInfo == null){
                    throw new SalaryException(500,"该用户不在选择的公司内");
                }
            case USER:
                if (!user.isLogin()){
                    throw new SalaryException(ExceptionCode.NOT_LOGIN);
                }
                break;
        }
        Object[] args = joinPoint.getArgs();
        Object result = joinPoint.proceed(args);
        return result;
    }
}
