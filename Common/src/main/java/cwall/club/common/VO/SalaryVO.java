package cwall.club.common.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class SalaryVO {
    Long cid;
    Long employeeId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date time;
    double salary;
    Long id;
}
