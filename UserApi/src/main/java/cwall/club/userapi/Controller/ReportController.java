package cwall.club.userapi.Controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import cwall.club.common.Annotation.SalaryPermission;
import cwall.club.common.DTO.OrderInfoDTO;
import cwall.club.common.DTO.ReportDTO;
import cwall.club.common.Item.ApiResult;
import cwall.club.common.Item.UserContext;
import cwall.club.common.VO.OrderInfoVO;
import cwall.club.core.Service.ReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/report")
@Api(tags = "报告接口")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @PostMapping("/draw")
    @ResponseBody
    @SalaryPermission(SalaryPermission.FunctionPermission.STAFF)
    @ApiOperation(value = "", notes = "", httpMethod = "POST", response = String.class)
    public ApiResult draw(@RequestParam String cid, @RequestParam int code, @RequestBody ReportDTO reportDTO){// 0 工作总时间 1 在某项目工作总时间 2 休假情况  3 获取年初至今薪资
        return ApiResult.renderSuccess(reportService.drawImage(cid, code,reportDTO, UserContext.getUser().getId()));
    }

    @PostMapping("/drawOther")
    @ResponseBody
    @SalaryPermission(SalaryPermission.FunctionPermission.ADMIN)
    @ApiOperation(value = "", notes = "", httpMethod = "POST", response = String.class)
    public ApiResult drawOther(@RequestParam String cid, @RequestParam int code, @RequestBody ReportDTO reportDTO){// 0 工作总时间 1 年初至今薪资
        return ApiResult.renderSuccess(reportService.drawImage(cid, code, reportDTO, Long.valueOf(reportDTO.getUid())));
    }
}
