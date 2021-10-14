package cwall.club.common.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import cwall.club.common.Item.OrderInfo;
import cwall.club.common.Util.ClassUtil;
import lombok.Data;

import java.util.Date;

@Data
public class OrderInfoVO {
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

    public static OrderInfoVO copyFrom(OrderInfo orderInfo) {
        OrderInfoVO orderInfoVO = new OrderInfoVO();
        ClassUtil.copyOneFromOne(orderInfoVO, orderInfo, orderInfoVO.getClass(), orderInfo.getClass());
        orderInfoVO.setId(orderInfo.getId());
        return  orderInfoVO;
    }
}
