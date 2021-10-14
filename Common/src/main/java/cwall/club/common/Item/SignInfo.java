package cwall.club.common.Item;

import com.fasterxml.jackson.annotation.JsonFormat;
import cwall.club.common.DTO.SignInfoDTO;
import cwall.club.common.Util.ClassUtil;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "sign_info")
public class SignInfo extends BaseItem{
    Long employeeId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time = new Date(System.currentTimeMillis());
    int len;
    Long cid;
    Long pid;

    public static SignInfo copyFromDTO(SignInfoDTO signInfoDTO) {
        SignInfo signInfo = new SignInfo();
        ClassUtil.copyOneFromOne(signInfo, signInfoDTO, signInfo.getClass(), signInfoDTO.getClass());
        return signInfo;
    }
}

