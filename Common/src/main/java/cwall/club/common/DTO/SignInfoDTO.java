package cwall.club.common.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class SignInfoDTO {
    Long employeeId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;
    int len;
    Long cid;
    Long id;
    Long pid;
}
