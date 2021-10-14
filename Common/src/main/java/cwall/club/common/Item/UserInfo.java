package cwall.club.common.Item;

import com.fasterxml.jackson.annotation.JsonFormat;
import cwall.club.common.DTO.UserInfoDTO;
import cwall.club.common.Util.ClassUtil;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "user_info")
public class UserInfo extends BaseItem{
    String uid;
    String pwd;
    String phone;
    String name = "CWaller";
    String sign = "I am a CWaller";
    boolean sex; //false为女  true为男
    String trueName = "CWaller";
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date bornTime = new Date(System.currentTimeMillis());
    private String location = "福建省 - 漳州市 - 长泰区";
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date registerTime = new Date(System.currentTimeMillis());

    public static UserInfo copyFromDTO(UserInfoDTO userInfoDTO) {
        UserInfo userInfo = new UserInfo();
        ClassUtil.copyOneFromOne(userInfo,userInfoDTO,userInfo.getClass(),userInfoDTO.getClass());
        return userInfo;
    }
}
