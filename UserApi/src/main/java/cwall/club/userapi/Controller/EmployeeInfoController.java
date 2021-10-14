package cwall.club.userapi.Controller;

import cwall.club.common.Annotation.SalaryPermission;
import cwall.club.common.DTO.EmployeeInfoDTO;
import cwall.club.common.Item.ApiResult;
import cwall.club.common.Item.UserContext;
import cwall.club.common.VO.CompanyVO;
import cwall.club.common.VO.EmployeeInfoVO;
import cwall.club.core.Service.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/employee")
@Api(tags = "雇员接口")
public class EmployeeInfoController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/getCompany")
    @ResponseBody
    @SalaryPermission(SalaryPermission.FunctionPermission.USER)
    @ApiOperation(value = "获取用户所在所有公司", notes = "获取用户所在所有公司", httpMethod = "POST", response = CompanyVO.class)
    public ApiResult getCompanyInfo(){
        return ApiResult.renderSuccess(employeeService.getCompanyInfo());
    }

    @PostMapping("/update")
    @ResponseBody
    @SalaryPermission(SalaryPermission.FunctionPermission.STAFF)
    @ApiOperation(value = "更新雇员信息", notes = "更新雇员信息", httpMethod = "POST", response = EmployeeInfoVO.class)
    public ApiResult updateEmployeeInfo(@RequestBody Map<String,Object> maps, String cid){
        return ApiResult.renderSuccess(employeeService.updateEmployeeInfo(maps, UserContext.getUser().getId(), Long.valueOf(UserContext.getUser().getCid())));
    }

    @PostMapping("/get")
    @ResponseBody
    @SalaryPermission(SalaryPermission.FunctionPermission.STAFF)
    @ApiOperation(value = "获取雇员信息", notes = "获取雇员信息", httpMethod = "POST", response = EmployeeInfoVO.class)
    public ApiResult getEmployeeInfo(String cid){
        return ApiResult.renderSuccess(employeeService.getEmployeeInfo(Long.valueOf(cid), UserContext.getUser().getId()));
    }
    @PostMapping("/updateOther")
    @ResponseBody
    @SalaryPermission(SalaryPermission.FunctionPermission.ADMIN)
    @ApiOperation(value = "管理更新雇员信息", notes = "管理更新雇员信息", httpMethod = "POST", response = EmployeeInfoVO.class)
    public ApiResult updateOtherEmployeeInfo(@RequestBody Map<String,Object> maps,String cid,String uid){
        return ApiResult.renderSuccess(employeeService.updateEmployeeInfo(maps, Long.valueOf(uid), Long.valueOf(UserContext.getUser().getCid())));
    }

    @PostMapping("/delete")
    @ResponseBody
    @SalaryPermission(SalaryPermission.FunctionPermission.ADMIN)
    @ApiOperation(value = "删除雇员信息", notes = "删除雇员信息", httpMethod = "POST", response = Boolean.class)
    public ApiResult deleteEmployeeInfo(String cid, String uid){
        return ApiResult.renderSuccess(employeeService.deleteEmployeeInfo(Long.valueOf(cid),Long.valueOf(uid)));
    }

    @PostMapping("/create")
    @ResponseBody
    @SalaryPermission(SalaryPermission.FunctionPermission.ADMIN)
    @ApiOperation(value = "增加雇员信息", notes = "增加雇员信息", httpMethod = "POST", response = EmployeeInfoVO.class)
    public ApiResult createEmployeeInfo(@RequestBody EmployeeInfoDTO employeeInfoDTO, String cid){
        employeeInfoDTO.setCid(Long.valueOf(cid));
        return ApiResult.renderSuccess(employeeService.createEmployeeInfo(employeeInfoDTO));
    }

    @PostMapping("/getOther")
    @ResponseBody
    @SalaryPermission(SalaryPermission.FunctionPermission.ADMIN)
    @ApiOperation(value = "管理获取雇员信息", notes = "管理获取雇员信息", httpMethod = "POST", response = EmployeeInfoVO.class)
    public ApiResult getOtherEmployeeInfo(String cid,String uid){
        return ApiResult.renderSuccess(employeeService.getEmployeeInfo(Long.valueOf(cid), Long.valueOf(uid)));
    }

    @PostMapping("/getAll")
    @ResponseBody
    @SalaryPermission(SalaryPermission.FunctionPermission.ADMIN)
    @ApiOperation(value = "管理获取雇员信息", notes = "管理获取雇员信息", httpMethod = "POST", response = EmployeeInfoVO.class)
    public ApiResult getAllEmployeeInfo(String cid){
        return ApiResult.renderSuccess(employeeService.getEmployeeInfo(Long.valueOf(cid)));
    }
}
