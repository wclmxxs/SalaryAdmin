package cwall.club.common.Item;

import com.fasterxml.jackson.annotation.JsonFormat;
import cwall.club.common.DTO.OrderInfoDTO;
import cwall.club.common.Util.ClassUtil;
import lombok.Data;
import org.aspectj.weaver.ast.Or;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "order_info")
public class OrderInfo extends BaseItem{
    Long cid;
    Long employeeId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date startTime = new Date(System.currentTimeMillis());
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date endTime = new Date(System.currentTimeMillis());
    double price;
    String guestName;
    String guestLocation;
    String guestPhone;
    Long goodId;

    public static OrderInfo copyFromDTO(OrderInfoDTO orderInfoDTO) {
        OrderInfo orderInfo = new OrderInfo();
        ClassUtil.copyOneFromOne(orderInfo, orderInfoDTO, orderInfo.getClass(), orderInfoDTO.getClass());
        return orderInfo;
    }
}

