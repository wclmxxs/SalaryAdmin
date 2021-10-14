package cwall.club.common.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class OrderInfoDTO {
    Long cid;
    Long employeeId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date startTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date endTime;
    double price;
    String guestName;
    String guestLocation;
    String guestPhone;
    Long goodId;
    Long id;
}
