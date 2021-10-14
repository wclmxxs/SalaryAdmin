package cwall.club.common.DTO;

import lombok.Data;

@Data
public class EmployeeInfoDTO {
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

    public static EmployeeInfoDTO build(Long cid,Long uid,char type,double tax,double otherTax,double salary,double percent,char payType,String location) {
        EmployeeInfoDTO employeeInfoDTO = new EmployeeInfoDTO();
        employeeInfoDTO.setCid(cid);
        employeeInfoDTO.setUid(uid);
        employeeInfoDTO.setLocation(location);
        employeeInfoDTO.setType(type);
        employeeInfoDTO.setTax(tax);
        employeeInfoDTO.setOtherTax(otherTax);
        employeeInfoDTO.setSocialCode("0000000000");
        employeeInfoDTO.setSalary(salary);
        employeeInfoDTO.setPercent(percent);
        employeeInfoDTO.setLenLimit(24);
        employeeInfoDTO.setPayType(payType);
        return employeeInfoDTO;
    }

}
