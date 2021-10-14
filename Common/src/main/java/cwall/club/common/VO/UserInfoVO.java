package cwall.club.common.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import cwall.club.common.Item.UserInfo;
import cwall.club.common.Util.ClassUtil;
import lombok.Data;

import java.util.Date;

@Data
public class UserInfoVO {
    String uid;
    String phone;
    String name;
    String sign;
    boolean sex; //false为女  true为男
    String trueName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date bornTime;
    private String location;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date registerTime;
    Long id;
    public static UserInfoVO copyFromUserInfo(UserInfo userInfo){
        UserInfoVO userInfoVO = new UserInfoVO();
        ClassUtil.copyOneFromOne(userInfoVO,userInfo,userInfoVO.getClass(),userInfo.getClass());
        userInfoVO.setId(userInfo.getId());
        return userInfoVO;
    }
}
