package cwall.club.core.Service;

import cwall.club.common.DTO.OrderInfoDTO;
import cwall.club.common.Exception.SalaryException;
import cwall.club.common.Item.OrderInfo;
import cwall.club.common.Item.UserContext;
import cwall.club.common.Util.IDUtil;
import cwall.club.common.VO.EmployeeInfoVO;
import cwall.club.common.VO.OrderInfoVO;
import cwall.club.core.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private GoodsService goodsService;

    public OrderInfoVO create(OrderInfoDTO orderInfoDTO){
        if (orderInfoDTO.getGoodId() == null || goodsService.findGoods(orderInfoDTO.getGoodId(),orderInfoDTO.getCid())==null){
            throw new SalaryException(500, "商品不存在");
        }
        if (orderInfoDTO.getPrice() <= 0){
            throw new SalaryException(500, "商品金额错误");
        }
        OrderInfo orderInfo = OrderInfo.copyFromDTO(orderInfoDTO);
        orderInfo.setId(IDUtil.generateID());
        orderRepository.save(orderInfo);
        return OrderInfoVO.copyFrom(orderInfo);
    }

    public OrderInfoVO update(OrderInfoDTO orderInfoDTO){
        if (orderInfoDTO.getGoodId() == null || goodsService.findGoods(orderInfoDTO.getGoodId(),orderInfoDTO.getCid())==null){
            throw new SalaryException(500, "商品不存在");
        }
        if (orderInfoDTO.getPrice() <= 0){
            throw new SalaryException(500, "商品金额错误");
        }
        Optional<OrderInfo> orderInfo = orderRepository.findById(orderInfoDTO.getId());
        if (!orderInfo.isPresent()){
            throw new SalaryException(500, "订单不存在");
        }
        OrderInfo order = OrderInfo.copyFromDTO(orderInfoDTO);
        order.setId(orderInfoDTO.getId());
        order.set_id(orderInfo.get().get_id());
        orderRepository.save(order);
        return OrderInfoVO.copyFrom(order);
    }

    public OrderInfoVO getOne(Long id){
        Optional<OrderInfo> orderInfo = orderRepository.findById(id);
        if (!orderInfo.isPresent()){
            throw new SalaryException(500, "订单不存在");
        }
        return OrderInfoVO.copyFrom(orderInfo.get());
    }

    public List<OrderInfoVO> getEmployeeOrder(Long cid){
        UserContext user = UserContext.getUser();
        EmployeeInfoVO employeeInfo = employeeService.getEmployeeInfo(cid, user.getId());
        return orderRepository.findByEmployeeId(employeeInfo.getId()).stream().map(orderInfo -> OrderInfoVO.copyFrom(orderInfo)).collect(Collectors.toList());
    }

    public List<OrderInfoVO> getEmployeeOrder(Long cid,Long uid){
        EmployeeInfoVO employeeInfo = employeeService.getEmployeeInfo(cid, uid);
        return orderRepository.findByEmployeeId(employeeInfo.getId()).stream().map(orderInfo -> OrderInfoVO.copyFrom(orderInfo)).collect(Collectors.toList());
    }

    public List<OrderInfoVO> getAllOrder(Long cid){
        return orderRepository.findByCid(cid).stream().map(orderInfo -> OrderInfoVO.copyFrom(orderInfo)).collect(Collectors.toList());
    }

}
