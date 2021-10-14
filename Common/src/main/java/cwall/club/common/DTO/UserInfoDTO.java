package cwall.club.common.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.HashMap;
import java.util.List;


@Data
public class UserInfoDTO {
    String uid;
    String pwd;
    String rePwd;
    String phone;
    int code;
}
