package cwall.club.common.Item;

import com.fasterxml.jackson.annotation.JsonFormat;
import cwall.club.common.Util.IDUtil;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "salary")
public class Salary extends BaseItem{
    Long cid;
    Long employeeId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date time;
    double salary;
    char type;
    String code;

    public static Salary build(Long cid,Long employeeId,double s,char type,String code) {
        Salary salary = new Salary();
        salary.setId(IDUtil.generateID());
        salary.setSalary(s);
        salary.setCid(cid);
        salary.setEmployeeId(employeeId);
        salary.setTime(new Date(System.currentTimeMillis()));
        salary.setCode(code);
        salary.setType(type);
        return salary;
    }
}
