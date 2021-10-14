package cwall.club.common.Util;

import cwall.club.common.Enum.ExceptionCode;
import cwall.club.common.Exception.SalaryException;

public class AssertUtil {

    public static void isEmpty(String str){
        if (str == null){
            throw new SalaryException(ExceptionCode.NEED_PARAMETER);
        }
    }

}
