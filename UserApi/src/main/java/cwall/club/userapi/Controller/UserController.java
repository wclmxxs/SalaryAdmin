package cwall.club.userapi.Controller;

import cwall.club.common.Annotation.SalaryPermission;
import cwall.club.common.Item.ApiResult;
import cwall.club.common.VO.UserInfoVO;
import cwall.club.core.Service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/user")
@Api(tags = "用户接口")
public class UserController {

    @Autowired
    private UserInfoService userInfoService;

    @PostMapping("/getInfo")
    @ResponseBody
    @SalaryPermission(SalaryPermission.FunctionPermission.USER)
    @ApiOperation(value = "获取用户信息", notes = "获取用户信息", httpMethod = "POST", response = UserInfoVO.class)
    public ApiResult getInfo(){
        return ApiResult.renderSuccess(userInfoService.getUserInfo());
    }

    @PostMapping("/setInfo")
    @ResponseBody
    @SalaryPermission(SalaryPermission.FunctionPermission.USER)
    @ApiOperation(value = "", notes = "", httpMethod = "POST", response = UserInfoVO.class)
    public ApiResult setInfo(@RequestBody HashMap<String,Object> maps){
        return ApiResult.renderSuccess(userInfoService.setUserInfo(maps));
    }
}
