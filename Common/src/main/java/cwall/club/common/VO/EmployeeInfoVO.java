package cwall.club.common.VO;

import cwall.club.common.Item.EmployeeInfo;
import cwall.club.common.Util.ClassUtil;
import lombok.Data;

@Data
public class EmployeeInfoVO {
    Long id;
    Long uid;
    char type;
    String location;
    String socialCode;
    double tax;
    double otherTax;
    double salary;
    double percent; //佣金率
    int lenLimit;
    Long cid;
    char payType;
    String name;
    public static EmployeeInfoVO copyFromEmployeeInfo(EmployeeInfo employeeInfo) {
        EmployeeInfoVO employeeInfoVO = new EmployeeInfoVO();
        employeeInfoVO.setId(employeeInfo.getId());
        ClassUtil.copyOneFromOne(employeeInfoVO,employeeInfo,employeeInfoVO.getClass(),employeeInfo.getClass());
        return employeeInfoVO;
    }
}
