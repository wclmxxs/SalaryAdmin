package cwall.club.userapi.Controller;

import cwall.club.common.Annotation.SalaryPermission;
import cwall.club.common.DTO.UserInfoDTO;
import cwall.club.common.Item.ApiResult;
import cwall.club.core.Service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@Api(tags = "Auth接口")
public class AuthController {

    @Resource
    private UserInfoService userInfoService;

    @PostMapping("/register")
    @ApiOperation(value = "注册", notes = "注册", httpMethod = "POST", response = Boolean.class)
    public ApiResult register(@RequestBody UserInfoDTO userInfoDTO){
        return ApiResult.renderSuccess(userInfoService.registerUser(userInfoDTO));
    }

    @PostMapping("/login")
    @ApiOperation(value = "登录", notes = "登录", httpMethod = "POST", response = Boolean.class)
    public ApiResult login(@RequestBody UserInfoDTO userInfoDTO, HttpServletRequest response){
        return ApiResult.renderSuccess(userInfoService.getUser(userInfoDTO, response));
    }

    @PostMapping("/change")
    @ResponseBody
    @SalaryPermission(SalaryPermission.FunctionPermission.USER)
    @ApiOperation(value = "修改密码", notes = "修改密码", httpMethod = "POST", response = Boolean.class)
    public ApiResult change(String code,String pwd){
        return ApiResult.renderSuccess(userInfoService.changePassword(code,pwd));
    }
}