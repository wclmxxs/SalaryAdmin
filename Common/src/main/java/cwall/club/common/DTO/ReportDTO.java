package cwall.club.common.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

@Data
public class ReportDTO {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date startTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date endTime;
    String pid;
    String uid;
}
