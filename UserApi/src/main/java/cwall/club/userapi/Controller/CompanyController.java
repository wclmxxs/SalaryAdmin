package cwall.club.userapi.Controller;

import cwall.club.common.Annotation.SalaryPermission;
import cwall.club.common.DTO.CompanyDTO;
import cwall.club.common.Item.ApiResult;
import cwall.club.common.VO.CompanyVO;
import cwall.club.common.VO.UserInfoVO;
import cwall.club.core.Service.CompanyService;
import cwall.club.core.Service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/company")
@Api(tags = "公司接口")
public class CompanyController {

    @Autowired
    private CompanyService Service;

    @PostMapping("/create")
    @ResponseBody
    @SalaryPermission(SalaryPermission.FunctionPermission.USER)
    @ApiOperation(value = "创建公司", notes = "创建公司", httpMethod = "POST", response = CompanyVO.class)
    public ApiResult create(@RequestBody CompanyDTO companyDTO){
        return ApiResult.renderSuccess(Service.createCompany(companyDTO));
    }
}
