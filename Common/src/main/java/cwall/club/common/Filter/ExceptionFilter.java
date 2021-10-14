package cwall.club.common.Filter;

import cwall.club.common.Exception.SalaryException;
import cwall.club.common.Item.ApiResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionFilter {
    @ExceptionHandler(SalaryException.class)
    @ResponseBody
    public ApiResult doFilter(SalaryException e){
        return ApiResult.rendFail(e.getCode(), e.getMessage());
    }
}
