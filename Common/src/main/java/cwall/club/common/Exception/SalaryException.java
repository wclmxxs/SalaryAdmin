package cwall.club.common.Exception;

import cwall.club.common.Enum.ExceptionCode;
import lombok.Data;

@Data
public class SalaryException extends RuntimeException{
    int code;
    String message;
    public SalaryException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public SalaryException(String message){
        super(message);
        this.code = 500;
        this.message = message;
    }

    public SalaryException(ExceptionCode code){
        super(code.getDescription());
        this.code = code.getCode();
        this.message = code.getDescription();
    }
}
