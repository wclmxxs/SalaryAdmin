package cwall.club.common.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import cwall.club.common.Item.SignInfo;
import cwall.club.common.Util.ClassUtil;
import lombok.Data;

import java.util.Date;

@Data
public class SignInfoVO {
    Long employeeId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;
    int len;
    Long cid;
    Long id;
    Long pid;

    public static SignInfoVO copyFrom(SignInfo signInfo) {
        SignInfoVO signInfoVO = new SignInfoVO();
        ClassUtil.copyOneFromOne(signInfoVO, signInfo, signInfoVO.getClass(), signInfo.getClass());
        signInfoVO.setId(signInfo.getId());
        return signInfoVO;
    }
}
