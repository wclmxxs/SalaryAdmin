package cwall.club.userapi.Controller;

import cwall.club.common.Annotation.SalaryPermission;
import cwall.club.common.DTO.SignInfoDTO;
import cwall.club.common.Item.ApiResult;
import cwall.club.common.VO.CompanyVO;
import cwall.club.common.VO.SignInfoVO;
import cwall.club.core.Service.SignInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sign")
@Api(tags = "考勤接口")
public class SignInfoController {

    @Autowired
    private SignInfoService signInfoService;

    @PostMapping("/sign")
    @ResponseBody
    @SalaryPermission(SalaryPermission.FunctionPermission.STAFF)
    @ApiOperation(value = "签到", notes = "签到", httpMethod = "POST", response = SignInfoVO.class)
    public ApiResult signDate(@RequestBody List<SignInfoDTO> signInfoDTOS, String cid){
        return ApiResult.renderSuccess(signInfoService.signDate(signInfoDTOS, Long.valueOf(cid)));
    }

    @PostMapping("/cancel")
    @ResponseBody
    @SalaryPermission(SalaryPermission.FunctionPermission.STAFF)
    @ApiOperation(value = "取消签到", notes = "取消签到", httpMethod = "POST", response = SignInfoVO.class)
    public ApiResult cancelSign(@RequestBody List<SignInfoDTO> signInfoDTOS, String cid){
        return ApiResult.renderSuccess(signInfoService.cancelSign(signInfoDTOS, Long.valueOf(cid)));
    }
}
