package cwall.club.userapi.Controller;

import cwall.club.common.Annotation.SalaryPermission;
import cwall.club.common.DTO.EmployeeInfoDTO;
import cwall.club.common.DTO.OrderInfoDTO;
import cwall.club.common.Item.ApiResult;
import cwall.club.common.Item.OrderInfo;
import cwall.club.common.Item.UserContext;
import cwall.club.common.VO.OrderInfoVO;
import cwall.club.core.Service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order")
@Api(tags = "订单接口")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/update")
    @ResponseBody
    @SalaryPermission(SalaryPermission.FunctionPermission.STAFF)
    @ApiOperation(value = "更新订单信息", notes = "更新订单信息", httpMethod = "POST", response = OrderInfoVO.class)
    public ApiResult updateOrder(@RequestBody OrderInfoDTO orderInfoDTO,String cid){
        return ApiResult.renderSuccess(orderService.update(orderInfoDTO));
    }

    @PostMapping("/create")
    @ResponseBody
    @SalaryPermission(SalaryPermission.FunctionPermission.STAFF)
    @ApiOperation(value = "增加订单信息", notes = "增加订单信息", httpMethod = "POST", response = OrderInfoVO.class)
    public ApiResult createOrder(@RequestBody OrderInfoDTO orderInfoDTO, String cid){
        orderInfoDTO.setCid(Long.valueOf(cid));
        return ApiResult.renderSuccess(orderService.create(orderInfoDTO));
    }

    @PostMapping("/get")
    @ResponseBody
    @SalaryPermission(SalaryPermission.FunctionPermission.STAFF)
    @ApiOperation(value = "获取订单信息", notes = "获取订单信息", httpMethod = "POST", response = OrderInfoVO.class)
    public ApiResult getOrder(Long goodsId, String cid){
        return ApiResult.renderSuccess(orderService.getOne(goodsId));
    }

    @PostMapping("/getEmployee")
    @ResponseBody
    @SalaryPermission(SalaryPermission.FunctionPermission.STAFF)
    @ApiOperation(value = "获取雇员订单信息", notes = "获取雇员订单信息", httpMethod = "POST", response = OrderInfoVO.class)
    public ApiResult getEmployeeOrder(String cid){
        return ApiResult.renderSuccess(orderService.getEmployeeOrder(Long.valueOf(cid)));
    }

    @PostMapping("/getAll")
    @ResponseBody
    @SalaryPermission(SalaryPermission.FunctionPermission.STAFF)
    @ApiOperation(value = "获取全部信息", notes = "获取全部信息", httpMethod = "POST", response = OrderInfoVO.class)
    public ApiResult getAllOrder(String cid){
        return ApiResult.renderSuccess(orderService.getAllOrder(Long.valueOf(cid)));
    }
}
