package cwall.club.userapi.Controller;

import cwall.club.common.Annotation.SalaryPermission;
import cwall.club.common.DTO.ProjectDTO;
import cwall.club.common.Item.ApiResult;
import cwall.club.common.VO.OrderInfoVO;
import cwall.club.common.VO.ProjectVO;
import cwall.club.core.Service.ProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/project")
@Api(tags = "项目接口")
public class ProjectController {


    @Autowired
    private ProjectService projectService;

    @PostMapping("/getAll")
    @ResponseBody
    @SalaryPermission(SalaryPermission.FunctionPermission.STAFF)
    @ApiOperation(value = "", notes = "", httpMethod = "POST", response = ProjectVO.class)
    public ApiResult getAll(String cid){
        return ApiResult.renderSuccess(projectService.getAll(Long.valueOf(cid)));
    }

    @PostMapping("/get")
    @ResponseBody
    @SalaryPermission(SalaryPermission.FunctionPermission.STAFF)
    @ApiOperation(value = "", notes = "", httpMethod = "POST", response = ProjectVO.class)
    public ApiResult get(String cid,String pid){
        return ApiResult.renderSuccess(projectService.get(Long.valueOf(pid)));
    }

    @PostMapping("/create")
    @ResponseBody
    @SalaryPermission(SalaryPermission.FunctionPermission.STAFF)
    @ApiOperation(value = "", notes = "", httpMethod = "POST", response = ProjectVO.class)
    public ApiResult create(ProjectDTO projectDTO,String cid){
        return ApiResult.renderSuccess(projectService.create(projectDTO));
    }
}
