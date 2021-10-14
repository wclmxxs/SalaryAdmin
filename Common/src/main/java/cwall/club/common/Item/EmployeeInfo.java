package cwall.club.common.Item;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "employee_info")
public class EmployeeInfo extends BaseItem{
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
}
